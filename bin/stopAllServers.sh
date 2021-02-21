#!/bin/bash

cd $HOME/Softwares/spark
sbin/stop-master.sh

cd $HOME/Softwares/zookeeper
bin/zkServer.sh stop

cd $HOME/Softwares/kafka
bin/kafka-server-stop.sh

