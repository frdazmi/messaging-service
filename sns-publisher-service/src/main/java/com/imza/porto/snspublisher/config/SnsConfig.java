package com.imza.porto.snspublisher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class SnsConfig {


  @Value("${aws.accessKeyId}")
  private String accessKeyId;

  @Value("${aws.secretKey}")
  private String secretKey;

  @Value("${aws.localstack.endpoint}")
  private String endpoint;

  @Bean
  public SnsClient snsClient() {
    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
    return SnsClient.builder()
            .endpointOverride(URI.create(endpoint))
            .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build();
  }
}
