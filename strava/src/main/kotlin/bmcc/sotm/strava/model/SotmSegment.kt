package bmcc.sotm.strava.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SotmSegment (@JsonProperty("entries")val efforts : Set<SegmentEffort>)