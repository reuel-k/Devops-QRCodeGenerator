package com.devops.qr_code_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class QrCodeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(QrCodeWebApplication.class, args);
    }
}
