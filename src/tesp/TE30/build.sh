#!/bin/bash
rootdir=`pwd`

cd $rootdir/TE30_generated
sh build.sh

cd $rootdir/TE30_deployment
mvn clean install

