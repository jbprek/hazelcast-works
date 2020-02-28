package com.foo.lock.global;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IlockGlobalDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IlockGlobalDemoApplication.class, args);
    }

}
