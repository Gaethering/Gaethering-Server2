package com.gaethering.modulemember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(
    scanBasePackages = {"com.gaethering.moduledomain", "com.gaethering.modulemember"}
)
@EntityScan("com.gaethering.moduledomain.domain")
public class ModuleMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleMemberApplication.class, args);
    }

}
