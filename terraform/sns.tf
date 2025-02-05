resource "aws_sns_topic" "event_topic" {
  name = "event_topic-${random_id.random_suffix.hex}"
}
