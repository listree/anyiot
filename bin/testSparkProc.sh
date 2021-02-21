#!/bin/bash


cd $HOME/Projects/integrated-iotsystem/

spark-submit \
  --packages org.apache.spark:spark-streaming-kafka_2.11:1.6.0 \
  --class "dashboard.KafkaToSpark" \
  --master local[4] \
  target/integrated-iotsystem-1.0.0.jar localhost:2181 groupname topicA 10