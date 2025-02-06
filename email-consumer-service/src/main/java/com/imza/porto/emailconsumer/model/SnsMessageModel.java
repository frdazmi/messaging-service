package com.imza.porto.emailconsumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class SnsMessageModel {
  @JsonProperty("Type")
  private String type;
  @JsonProperty("MessageId")
  private String messageId;
  @JsonProperty("TopicArn")
  private String topicArn;
  @JsonProperty("Message")
  private String message;
  @JsonProperty("Timestamp")
  private String timestamp;
  @JsonProperty("UnsubscribeURL")
  private String unsubscribeURL;
  @JsonProperty("SignatureVersion")
  private String signatureVersion;
  @JsonProperty("Signature")
  private String signature;
  @JsonProperty("SigningCertURL")
  private String signingCertURL;
}
