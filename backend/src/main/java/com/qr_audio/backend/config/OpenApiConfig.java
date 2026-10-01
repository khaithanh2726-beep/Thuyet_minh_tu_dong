package com.qr_audio.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Hệ thống Thuyết minh Đa ngôn ngữ QR")
                        .version("1.0.0")
                        .description("Tài liệu RESTful API cho Đồ án môn Công nghệ phần mềm"));
    }
}