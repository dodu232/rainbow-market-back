package org.example.rm_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RainbowMarketBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainbowMarketBackApplication.class, args);
    }

}
