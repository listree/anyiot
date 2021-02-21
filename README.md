# Integrated System for IoT applications

## General use cases:
### Collect telemetry data from multiple sources
e.g. every 5 seconds from 1K to 1M devices.
### Send alert when metric reach threshold
### Realtime Dashboard on calculate metrics
 
## Dependent on open source projects only
e.g. Kafka, Spark, Postgres, Cassandra, etc...

## Instructions:
### Install zookeepr & kafka & Spark:
under directory $HOME/Softwares 

### Start zookeepr & kafka & Spark:
bin/startAllServers.sh

### Stop zookeepr & kafka & Spark:
bin/stopAllServers.sh

### Download & Compile & run:
Under $HOME/Projects

git clone https://github.com/listree/integrated-iotsystem.git

cd integrated-iotsystem

bin/demoSimpleKafka.sh

bin/demoSparkSubmit.sh