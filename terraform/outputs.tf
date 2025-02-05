output "email_queue_name" {
  value = aws_sqs_queue.email_queue.name
}

output "sms_queue_name" {
  value = aws_sqs_queue.sms_queue.name
}

output "email_queue_arn" {
  value = aws_sqs_queue.email_queue.arn
}

output "sms_queue_arn" {
  value = aws_sqs_queue.sms_queue.arn
}

output "sns_topic_arn" {
  value = aws_sns_topic.event_topic.arn
}
