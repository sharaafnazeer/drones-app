/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.sharaafnazeer.dronesapp.repos")
@EnableScheduling
public class DronesAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(DronesAppApplication.class, args);
    }

}
