package bmcc.sotm.strava.model

import com.fasterxml.jackson.annotation.JsonProperty

data class StravaMember (
        @JsonProperty("firstname") val forename: String,
        @JsonProperty("lastname") val surname: String
)
