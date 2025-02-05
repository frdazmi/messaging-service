resource "aws_sqs_queue" "email_queue" {
  name                       = "email-queue-${random_id.random_suffix.hex}"
  message_retention_seconds  = 86400
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue_redrive_policy" "email_queue_redrive_policy" {
  queue_url = aws_sqs_queue.email_queue.url
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.email_queue_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue" "sms_queue" {
  name                       = "sms-queue-${random_id.random_suffix.hex}"
  message_retention_seconds  = 86400
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue_redrive_policy" "sms_queue_redrive_policy" {
  queue_url = aws_sqs_queue.sms_queue.url
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.sms_queue_dlq.arn
    maxReceiveCount     = 3
  })
}

resource "aws_sqs_queue" "email_queue_dlq" {
  name                      = "${aws_sqs_queue.email_queue.name}-dlq"
  message_retention_seconds = 86400
}

resource "aws_sqs_queue" "sms_queue_dlq" {
  name                      = "${aws_sqs_queue.sms_queue.name}-dlq"
  message_retention_seconds = 86400
}

resource "aws_sqs_queue_policy" "email_queue_policy" {
  queue_url = aws_sqs_queue.email_queue.url

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = "*"
        Action    = "sqs:SendMessage"
        Resource  = aws_sqs_queue.email_queue.arn
        Condition = {
          ArnEquals = {
            "aws:SourceArn" = aws_sns_topic.event_topic.arn
          }
        }
      }
    ]
  })
}

resource "aws_sqs_queue_policy" "sms_queue_policy" {
  queue_url = aws_sqs_queue.sms_queue.url

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = "*"
        Action    = "sqs:SendMessage"
        Resource  = aws_sqs_queue.sms_queue.arn
        Condition = {
          ArnEquals = {
            "aws:SourceArn" = aws_sns_topic.event_topic.arn
          }
        }
      }
    ]
  })
}

resource "aws_sns_topic_subscription" "email_queue_subscription" {
  topic_arn = aws_sns_topic.event_topic.arn
  protocol  = "sqs"
  endpoint  = aws_sqs_queue.email_queue.arn
  depends_on = [aws_sqs_queue_policy.email_queue_policy]
}

resource "aws_sns_topic_subscription" "sms_queue_subscription" {
  topic_arn = aws_sns_topic.event_topic.arn
  protocol  = "sqs"
  endpoint  = aws_sqs_queue.sms_queue.arn
  depends_on = [aws_sqs_queue_policy.sms_queue_policy] 
}
