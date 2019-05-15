#!/bin/bash
rootdir=`pwd`

cd $rootdir/TE30_generated
mvn clean install

cd $rootdir/TE30_deployment
mvn clean install

