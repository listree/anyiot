#!/bin/bash

cd $HOME/Softwares/spark
sbin/start-master.sh

cd $HOME/Softwares/zookeeper
bin/zkServer.sh start

cd $HOME/Softwares/kafka
bin/kafka-server-start.sh config/server.properties

