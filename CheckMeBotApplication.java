package tag.sources.checkmebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class CheckMeBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(CheckMeBotApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(3);
		scheduler.setThreadNamePrefix("MyScheduler-");
		scheduler.initialize();
		return scheduler;
	}
}