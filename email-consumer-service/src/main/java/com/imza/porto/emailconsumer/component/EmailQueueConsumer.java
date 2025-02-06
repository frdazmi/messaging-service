package com.imza.porto.emailconsumer.component;

import com.imza.porto.sdk.sqs.consumer.AbstractSqsConsumer;
import com.imza.porto.emailconsumer.model.SnsMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.concurrent.Executor;

@Component
public class EmailQueueConsumer extends AbstractSqsConsumer<SnsMessageModel> {

  private static final Logger logger = LoggerFactory.getLogger(EmailQueueConsumer.class);
  @Value("${email-queue-sqs.url}")
  private String queueUrl;
  @Value("${email-queue-sqs.consumer.maxNumberOfMessages}")
  private Integer maxNumberOfMessages;
  @Value("${email-queue-sqs.consumer.waitTimeSeconds}")
  private Integer waitTimeSeconds;

  public EmailQueueConsumer(SqsClient sqsClient,
                            @Qualifier("sqsWorkerPool") Executor workerPool) {
    super(sqsClient, workerPool);
  }

  @Override
  protected Config getConfig() {
    return AbstractSqsConsumer.Config.builder()
        .queueUrl(queueUrl)
        .maxNumberOfMessages(maxNumberOfMessages)
        .waitTimeSeconds(waitTimeSeconds)
        .build();
  }

  @Scheduled(fixedRateString = "${email-queue-sqs.consumer.pollingInterval}")
  public void startPolling() {
    pollQueueMessages();
  }

  @Override
  protected void handleMessage(SnsMessageModel message) throws Exception {
    logger.info("Received message: {}", message);
    // Send email logic
  }
}
