package bmcc.sotm.strava.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StravaSegment (@JsonProperty("entries")val efforts : Set<StravaAttempt>)