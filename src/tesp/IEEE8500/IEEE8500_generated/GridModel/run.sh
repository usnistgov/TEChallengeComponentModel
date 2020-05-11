#!/bin/bash
configFile=conf/IEEE8500.json
if [ $# -eq 1 ] && [ -f $1 ]; then
    configFile=$1
fi

echo using the file $configFile
java -Djava.net.preferIPv4Stack=true -Dlog4j.configurationFile=conf/log4j2.xml -jar gridlabd-federate-1.0.0-SNAPSHOT.jar $configFile

