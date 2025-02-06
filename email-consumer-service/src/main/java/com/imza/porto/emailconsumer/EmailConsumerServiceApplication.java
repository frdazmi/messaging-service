package com.imza.porto.emailconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailConsumerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmailConsumerServiceApplication.class, args);
  }
}
