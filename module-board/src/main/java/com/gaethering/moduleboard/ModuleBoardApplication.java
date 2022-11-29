package com.gaethering.moduleboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(
    scanBasePackages = {"com.gaethering.moduledomain", "com.gaethering.moduleboard"}
)
@EntityScan("com.gaethering.moduledomain.domain")
public class ModuleBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBoardApplication.class, args);
    }

}
