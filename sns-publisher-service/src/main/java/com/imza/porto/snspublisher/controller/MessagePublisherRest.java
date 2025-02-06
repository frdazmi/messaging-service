package com.imza.porto.snspublisher.controller;

import com.imza.porto.sdk.sns.SnsProducer;
import com.imza.porto.snspublisher.dtos.MessageRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import software.amazon.awssdk.services.sns.SnsClient;

import java.util.Collections;

@Controller
@RequestMapping("/api/message")
public class MessagePublisherRest {

  private static final Logger logger = LoggerFactory.getLogger(MessagePublisherRest.class);

  @Autowired
  private SnsClient snsClient;

  @Value("${aws.sns.topicArn}")
  private String snsTopicArn;

  @PostMapping("/publish")
  public ResponseEntity<String> publishMessage(@RequestBody MessageRequestDto requestDto) {
    SnsProducer snsProducer = new SnsProducer(snsClient);
    logger.info("Publishing message {} to SNS topic : {}", requestDto.getMessage(), snsTopicArn);
    String messageId = snsProducer.publishMessage(snsTopicArn, requestDto.getMessage(), Collections.emptyMap());
    return ResponseEntity.ok("Message published successfully with ID : " + messageId);
  }
}
