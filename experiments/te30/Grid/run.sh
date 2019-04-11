#!/bin/bash
java -Djava.net.preferIPv4Stack=true -Dlog4j.configurationFile=conf/log4j2.xml -jar gridlabd-federate-1.0.0-SNAPSHOT.jar conf/Grid.json
