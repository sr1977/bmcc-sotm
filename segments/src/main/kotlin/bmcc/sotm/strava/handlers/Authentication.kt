package bmcc.sotm.strava.handlers

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

data class AuthResponse(var token_type: String, var access_token: String)

interface AuthenticationHandler {
    fun accessToken () : Mono<String>
}

@Component
@RefreshScope
class StravaAuthenticationHandlerHandler (
        val client : WebClient,
        val meterRegistry: MeterRegistry): AuthenticationHandler {

    var accessToken: String? = null
    lateinit @Value("\${strava.clientSecret}") var clientSecret: String
    lateinit @Value("\${strava.clientId}") var clientId: String
    lateinit @Value("\${strava.refreshToken}") var refreshToken: String
    lateinit @Value("\${strava.grantType}") var grantType: String

    override fun accessToken(): Mono<String> {
        val authSettings = LinkedMultiValueMap<String, String>().apply {
            add("client_secret", clientSecret)
            add("client_id", clientId)
            add("refresh_token", refreshToken)
            add("grant_type", grantType)
        }

        println("Secret is $clientSecret")

        // TODO can this be a singleton
        return if (accessToken != null ) Mono.just(accessToken!!) else client.post()
                            .uri("/oauth/token")
                            .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                            .body(BodyInserters.fromFormData(authSettings))
                            .retrieve()
                            .bodyToMono(AuthResponse::class.java)
                            .log("Auth token request")
                            .map {
                                meterRegistry.counter("strava.token.request").increment()
                                "${it.token_type} ${it.access_token}"
                            }
                            .doOnNext {
                                this.accessToken = it
                                Flux.interval(Duration.ofSeconds(30)).subscribe {
                                accessToken = null
                            }
                }
    }

}
