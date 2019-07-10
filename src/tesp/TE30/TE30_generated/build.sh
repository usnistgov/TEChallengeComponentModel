#!/bin/bash
mvn clean install -f Grid30/pom.xml
mvn clean install -f TE30-java-federates/Auction/pom.xml
mvn clean install -f TE30-java-federates/ExternalLoad/pom.xml
mvn clean install -f TE30-java-federates/PyPower/pom.xml
mvn clean install -f TE30-java-federates/SimulationTime/pom.xml

