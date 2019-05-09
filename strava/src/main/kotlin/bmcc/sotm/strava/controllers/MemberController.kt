package bmcc.sotm.strava.controllers

import bmcc.sotm.strava.handlers.AuthenticationHandler
import bmcc.sotm.strava.model.StravaMember
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/members")
class MemberController (val client: WebClient, val authenticationHandler: AuthenticationHandler, val meterRegistry: MeterRegistry) {

    lateinit @Value("\${strava.clubId}") var clubId: String

    @GetMapping
    fun fetch(): Flux<StravaMember> {
            return authenticationHandler.accessToken()
                    .flux()
                    .flatMap{
                        meterRegistry.counter("strava.members.request")
                        client.get()
                                .uri("/api/v3/clubs/$clubId/members")
                                .header(HttpHeaders.AUTHORIZATION, it)
                                .retrieve()
                                .bodyToFlux(StravaMember::class.java)
                                .log("BMCC Members")
                                .cache()
                    }


        }
}