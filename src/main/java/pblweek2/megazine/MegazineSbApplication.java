package pblweek2.megazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MegazineSbApplication {
    public static void main(String[] args) {
        SpringApplication.run(MegazineSbApplication.class, args);
    }
}
