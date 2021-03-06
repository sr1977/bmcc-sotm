package bmcc.sotm.strava.controllers

import bmcc.sotm.strava.handlers.AuthenticationHandler
import bmcc.sotm.strava.model.StravaAttempt
import bmcc.sotm.strava.model.StravaSegment
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/segments")
class SegmentController (val client: WebClient, val authenticationHandler: AuthenticationHandler, val meterRegistry: MeterRegistry) {

    lateinit @Value("\${strava.clubId}") var clubId: String

    @GetMapping("/efforts")
    fun fetch(segmentId: Int): Flux<StravaAttempt> {
            return authenticationHandler.accessToken()
                    .flux()
                    .flatMap{
                        meterRegistry.counter("strava.segment.request", "segmentId", segmentId.toString()).increment()
                        client.get()
                                .uri("/api/v3/segments/$segmentId/leaderboard?club_id=$clubId")
                                .header(HttpHeaders.AUTHORIZATION, it)
                                .retrieve()
                                .bodyToFlux(StravaSegment::class.java)
                                .log("SOTM segment")
                                .flatMap { Flux.fromIterable(it.efforts)  }
                                .sort()
                                .log("Sorted segment effort")
                                .cache()
                    }


        }
}