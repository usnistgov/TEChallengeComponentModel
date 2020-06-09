#!/bin/bash

if [ $# -ne 1 ]; then
    echo "usage: $0 path/to/_generated"
    exit 1
fi

if [ ! -d $1 ]; then
    echo "illegal argument: expected _generated directory"
    exit 1;
fi

root_directory=`pwd`
logs_directory=$root_directory/logs
code_directory=`realpath ${1%/}`

fedmgr_host=127.0.0.1
fedmgr_port=8083

timestamp=`date +"%F_%T"`

function getNumberJoined {
    if (( $# != 1 ))
    then
        echo bad syntax: getNumberJoined federateType
        exit 1
    fi
    federateList=$(curl -s -X GET http://$fedmgr_host:$fedmgr_port/federates -H "Content-Type: application/json")
    # JSON Query:
    #   .[] = process all values in the input object
    #   select(...) = exclude entries for resigned federates (resignTime defined) and federates that are not the desired TYPE
    #   enclosing [ ] = combine the result from the above queries into a single JSON array
    #   length = count the number of elements in the combined array
    echo $federateList | jq --arg TYPE "$1" '[.[] | select(.resignTime == null and .federateType == $TYPE)] | length'
}

function waitUntilJoined {
    if (( $# != 2 ))
    then
        echo bad syntax: waitUntilJoined federateType expectedNumber
        exit 1
    fi
    federateType=$1
    expectedNumber=$2

    if (( $expectedNumber < 1 ))
    then
        echo "illegal argument: expectedNumber of federates cannot be $expectedNumber"
        exit 1
    fi

    printf "Waiting for $expectedNumber instances of $federateType to join.."
    while (( $(getNumberJoined $federateType) != $expectedNumber))
    do
        printf "."
        sleep 5
    done
    printf "\n"
}

nc -z $fedmgr_host $fedmgr_port
if [ $? -eq 0 ]; then
    echo Cannot start the federation manager on port $fedmgr_port
    exit 1
fi

if [ ! -d $logs_directory ]; then
    echo Creating the $logs_directory directory
    mkdir $logs_directory
fi

# run the federation manager
cd $root_directory
xterm -fg white -bg black -l -lf $logs_directory/federation-manager-${timestamp}.log -T "Federation Manager" -geometry 140x40+0+30 -e "export CPSWT_ROOT=`pwd` && mvn exec:java -P FederationManager" &

printf "Waiting for the federation manager to come online.."
until $(curl -o /dev/null -s -f -X GET http://$fedmgr_host:$fedmgr_port/fedmgr); do
    printf "."
    sleep 5
done
printf "\n"

curl -o /dev/null -s -X POST http://$fedmgr_host:$fedmgr_port/fedmgr --data '{"action": "START"}' -H "Content-Type: application/json"

# run the other federates
cd $code_directory/FileReader
xterm -fg white -bg black -l -lf $logs_directory/FileReader-${timestamp}.log -T "FileReader" -geometry 140x40+30+60 -e "sh run.sh conf/FileReaderParity.json" &
waitUntilJoined FileReader 1

cd $root_directory
xterm -fg white -bg black -l -lf $logs_directory/ParityClient-${timestamp}.log -T "ParityClient" -geometry 140x40+60+90 -e "mvn exec:java -P ExecJava,ParityClient" &
waitUntilJoined ParityClient 1

cd $root_directory
xterm -fg white -bg black -l -lf $logs_directory/ParityReporter-${timestamp}.log -T "ParityReporter" -geometry 140x40+90+120 -e "mvn exec:java -P ExecJava,ParityReporter" &
waitUntilJoined ParityReporter 1

cd $root_directory
xterm -fg white -bg black -l -lf $logs_directory/ParitySystem-${timestamp}.log -T "ParitySystem" -geometry 140x40+120+150 -e "mvn exec:java -P ExecJava,ParitySystem" &
waitUntilJoined ParitySystem 1

cd $code_directory/SimulationTime
xterm -fg white -bg black -l -lf $logs_directory/SimulationTime-${timestamp}.log -T "SimulationTime" -geometry 140x40+150+180 -e "sh run.sh conf/SimulationTimeParity.json" &
waitUntilJoined SimulationTime 1

# terminate the simulation
read -n 1 -r -s -p "Press any key to terminate the federation execution..."
printf "\n"

curl -o /dev/null -s -X POST http://$fedmgr_host:$fedmgr_port/fedmgr --data '{"action": "TERMINATE"}' -H "Content-Type: application/json"

printf "Waiting for the federation manager to terminate.."
while $(curl -o /dev/null -s -f -X GET http://$fedmgr_host:$fedmgr_port/fedmgr); do
    printf "."
    sleep 5
done
printf "\n"

