package com.samnart.space_global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class AwsS3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .endpointOverride(URI.create("http://localhost:9000")) // MinIO URL
            .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create("minioadmin", "minioadmin")))
            .region(Region.US_EAST_1) // Match what you use in properties
            .build();
    }
}
