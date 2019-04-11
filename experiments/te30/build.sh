#!/bin/bash
root_directory=`pwd`

echo Copying GridLAB-D JAR file from local maven repo
cd $root_directory/Grid
sh copy-jar.sh

echo Copying Metronome JAR file from local maven repo
cd $root_directory/Metronome
sh copy-jar.sh

echo Running mvn install on TE30_deployment
cd $root_directory/TE30_deployment
mvn install
