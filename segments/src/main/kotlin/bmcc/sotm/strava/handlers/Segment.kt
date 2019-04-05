package bmcc.sotm.strava.handlers

import bmcc.sotm.strava.model.SegmentEffort
import bmcc.sotm.strava.model.SotmSegment
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux


interface SegmentHandler {
    fun fetch (segmentId: Int) : Flux<SegmentEffort>
}

@Component
class StravaSegmentHandler(val client: WebClient,
                           val authenticationHandler: AuthenticationHandler,
                           val meterRegistry: MeterRegistry) : SegmentHandler {

    override fun fetch(segmentId: Int): Flux<SegmentEffort> {
        return authenticationHandler.accessToken()
                .flux()
                .flatMap{
                    meterRegistry.counter("strava.segment.leaderboard", "segmentId", segmentId.toString()).increment()
                    client.get()
                            .uri("/api/v3/segments/$segmentId/leaderboard?club_id=451376")
                            .header(HttpHeaders.AUTHORIZATION, it)
                            .retrieve()
                            .bodyToFlux(SotmSegment::class.java)
                            .log("SOTM segment")
                            .flatMap { Flux.fromIterable(it.efforts)  }
                            .sort()
                            .log("Sorted segment effort")
                            .cache()
                }

    }

}
