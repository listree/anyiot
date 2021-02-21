#!/bin/bash

# mvn package

spark-submit \
    --deploy-mode cluster \
    --master yarn \
    --class dashboard.KafakToSpar \
    target/integrated-iotsystem-1.0.0.jar 80