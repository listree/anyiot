#!/bin/bash

# javac -cp "$HOME/Softwares/kafka/libs/*" *.java
# mvn clean compile

java -cp "$HOME/Softwares/kafka/libs/*:target/classes" agents.AlertProducer TopicA &

java -cp "$HOME/Softwares/kafka/libs/*:target/classes" alerts.AlertConsumer TopicA &
