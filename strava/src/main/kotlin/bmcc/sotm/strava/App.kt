package bmcc.sotm.strava

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient


@SpringBootApplication
class App {

	@Bean
	fun client() = WebClient.builder()
				.baseUrl("https://www.strava.com")
				.defaultHeader(HttpHeaders.ACCEPT, "application/json")
				.build()

}

fun main(args: Array<String>) {
	runApplication<App>(*args)
}
