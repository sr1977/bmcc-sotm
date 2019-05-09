package bmcc.sotm.strava.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

// TODO can we introduce a companion object here for the comparable?
data class StravaAttempt (
        @JsonProperty("athlete_name") val name: String,
        @JsonProperty("elapsed_time") val time: Int,
        @JsonProperty("start_date_local") val date: LocalDate,
        val rank: Int
) :Comparable<StravaAttempt> {
    override fun compareTo(other: StravaAttempt): Int {
        return this.rank - other.rank
    }

}
