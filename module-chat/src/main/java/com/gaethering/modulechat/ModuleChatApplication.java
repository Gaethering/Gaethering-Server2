package com.gaethering.modulechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(
    scanBasePackages = {"com.gaethering.moduledomain", "com.gaethering.modulechat"}
)
@EntityScan("com.gaethering.moduledomain.domain")
public class ModuleChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleChatApplication.class, args);
    }

}
