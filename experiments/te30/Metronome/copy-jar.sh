#!/bin/bash
groupId=org.webgme.guest
artifactId=Metronome
version=0.1.0-SNAPSHOT

rm -f ${artifactId}-${version}.jar
mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:copy -Dartifact=${groupId}:${artifactId}:${version}:jar -DoutputDirectory=.
