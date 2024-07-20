package com.lsk.packagefetch.configuration;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {
    private String url;
    private String accessKey;
    private String accessSecret;

    @Bean
    public MinioClient minioClient () {
        return MinioClient.builder()
                .credentials(accessKey, accessSecret)
                .endpoint(url)
                .build();
    }
}
