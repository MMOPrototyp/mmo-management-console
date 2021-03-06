package com.jukusoft.mmo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.jukusoft.mmo"}, exclude = {SessionAutoConfiguration.class})
@EnableCaching
@EnableScheduling
@EntityScan("com.jukusoft.mmo")
@EnableJpaRepositories("com.jukusoft.mmo")
public class ServerMain {

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }

}
