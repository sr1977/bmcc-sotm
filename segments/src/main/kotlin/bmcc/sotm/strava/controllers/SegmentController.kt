package bmcc.sotm.strava.controllers

import bmcc.sotm.strava.handlers.SegmentHandler
import bmcc.sotm.strava.model.SegmentEffort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class SegmentController (val segmentHandler: SegmentHandler) {

    @GetMapping("/sotm")
    fun sotm() : Flux<SegmentEffort> {
        return segmentHandler.fetch(12387557)

    }
}