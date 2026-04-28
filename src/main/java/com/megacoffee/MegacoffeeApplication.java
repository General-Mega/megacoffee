package com.megacoffee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.megacoffee")
@SpringBootApplication
public class MegacoffeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MegacoffeeApplication.class, args);
    }
}
