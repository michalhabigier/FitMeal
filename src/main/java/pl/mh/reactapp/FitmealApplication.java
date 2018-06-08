package pl.mh.reactapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FitmealApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitmealApplication.class, args);
    }
}
