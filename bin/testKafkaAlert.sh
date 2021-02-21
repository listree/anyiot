#!/bin/bash

# javac -cp "$HOME/Softwares/kafka/libs/*" *.java
# mvn clean compile

cd $HOME/Projects/integrated-iotsystem/
java -cp "$HOME/Softwares/kafka/libs/*:target/classes" agents.AlertProducer TopicA &

cd $HOME/Projects/integrated-iotsystem/
java -cp "$HOME/Softwares/kafka/libs/*:target/classes" alerts.AlertConsumer TopicA &
