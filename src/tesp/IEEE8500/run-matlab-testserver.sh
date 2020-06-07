#!/bin/bash
HOME_DIR=IEEE8500_generated/MatlabClient

java -Dlog4j.configurationFile=$HOME_DIR/conf/log4j2.xml -cp $HOME_DIR/target/MatlabClient-0.1.0-SNAPSHOT.jar gov.nist.hla.te.matlabclient.TestServer

