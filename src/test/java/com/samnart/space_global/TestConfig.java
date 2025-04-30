package com.samnart.space_global;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public S3Client s3ClientTest() {
        return mock(S3Client.class);
    }
}
