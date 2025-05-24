package org.psk.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PskLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(PskLabApplication.class, args);
    }
}
