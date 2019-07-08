# script to run experiment
# This script will run an experiment where the main federates are all local
# it assumes:
# 	script.xml has federates expected
# 	FedManagerProject.fed has the fom file
# 	model and outputs are the gridlabD files
# 	tmy3.csv is the weather data
# There are clear limitations of this script in copying additional configuration files
# To use:
#	Ensure you have fresh source
#	run buildall.sh to build all sources
#	run run.sh to run experiment launching all federates in a terminal window

root_directory=`pwd`
logs_directory=$root_directory/logs
export RTI_RID_FILE=$root_directory/RTI.rid
export LOG4J=$root_directory/log4j2.xml


timestamp=`date +"%F_%T"`
federationId=TEChallenge

echo "Start of experiment at $timestamp"
echo env | grep RTI

nc -z 127.0.0.1 8083
if [ $? -eq 0 ]; then
    echo Cannot start the federation manager on port 8083
    exit 1
fi

if [ ! -d $logs_directory ]; then
    echo Creating the $logs_directory directory
    mkdir $logs_directory
fi

#use this selection to run in separate xterminal sessions(windows)
runinterminal="yes"
#runinterminal="no"

#use this selection to produce log files of all terminal content
logtofile="-l "
#logtofile=""

##################################
#paths to executables
##################################
#project federates
pathtofederate_fedmanager=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/TEChallenge_deployment
pathtofederate_Grid=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/Grid_generated/Grid-java-federates/Grid-impl-java/Grid/target
pathtofederate_Loads=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/Loads_generated/Loads-java-federates/Loads-impl-java/Loads/target
pathtofederate_LocalController=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/LocalController_generated/LocalController-java-federates/LocalController-impl-java/LocalController/target
pathtofederate_SupervisoryController=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/SupervisoryController_generated/SupervisoryController-java-federates/SupervisoryController-impl-java/SupervisoryController/target
pathtofederate_TransactiveAgent=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/TransactiveAgent_generated/TransactiveAgent-java-federates/TransactiveAgent-impl-java/TransactiveAgent/target


pathtofederate_Market=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/Market_generated/Market-java-federates/Market-impl-java/Market/target
pathtofederate_Storage=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/Storage_generated/Storage-java-federates/Storage-impl-java/Storage/target
pathtofederate_Generators=/home/vagrant/Projects/TEChallengeComponentModel/src/uceftesp/Generators_generated/Generators-java-federates/Generators-impl-java/Generators/target



# library federates
pathtofederate_GridlabD=/home/vagrant/ucefcodebase/ucef-gridlabd/GridLAB-D/target/
pathtofederate_Metronome=/home/vagrant/ucefcodebase/ucef-library/Federates/metronome/source/MetronomeFederate-java-federates/MetronomeFederate-impl-java/Metronome/target/
pathtofederate_Weather=/home/vagrant/ucefcodebase/ucef-library/Federates/tmy3weather/source/WeatherFederate-java-federates/WeatherFederate-impl-java/Weather/target
pathtofederate_Database=/home/vagrant/ucefcodebase/ucef-database/target

##################################
# copy configuration files
##################################
cp fedmgrconfig.json $pathtofederate_fedmanager/conf
cp experimentConfig.json $pathtofederate_fedmanager/conf
cp TEChallenge.xml $pathtofederate_fedmanager/fom
cp TEChallenge.fed $pathtofederate_fedmanager/fom
cp $LOG4J $pathtofederate_fedmanager/conf

cp Database.json $pathtofederate_Database
cp TEChallenge.xml $pathtofederate_Database
cp tmy3.csv $pathtofederate_Weather

cp LoadsConfig.json $pathtofederate_Loads/conf
cp LocalControllerConfig.json $pathtofederate_LocalController/conf
cp SupervisoryControllerConfig.json $pathtofederate_SupervisoryController/conf
cp TransactiveAgentConfig.json $pathtofederate_TransactiveAgent/conf

cp GeneratorsConfig.json $pathtofederate_Generators/conf
cp StorageConfig.json $pathtofederate_Storage/conf
cp MarketConfig.json $pathtofederate_Market/conf


cp MetronomeConfig.json $pathtofederate_Metronome
cp WeatherConfig.json $pathtofederate_Weather
cp GridLAB-D.json $pathtofederate_GridlabD



##################################
# run the federation manager
##################################
cd $pathtofederate_fedmanager
xterm -fg white -bg black -l -lf $logs_directory/federation-manager-${timestamp}.log -T "Federation Manager" -geometry 140x40+0+0 -e "export CPSWT_ROOT=`pwd` && mvn exec:java -P FederationManagerExecJava" &

printf "Waiting for the federation manager to come online.."
until $(curl -o /dev/null -s -f -X GET http://127.0.0.1:8083/fedmgr); do
    printf "."
    sleep 5
done
printf "\n"


##################################
# run the library federates
##################################
#cd $pathtofederate_GridlabD
#xterm -fg red       -bg black -l -lf $logs_directory/gridlabd-${timestamp}.log              -T "Gridlabd"              -geometry 140x40+200+20 -e "java  -Dlog4j.configurationFile=$LOG4J -jar gridlabd-federate-0.1.0-SNAPSHOT.jar  GridlabD.json" &
cd $pathtofederate_Metronome
xterm -fg red       -bg black -l -lf $logs_directory/metronome-${timestamp}.log             -T "Metronome"             -geometry 140x40+200+40 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Metronome-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=MetronomeConfig.json" &
cd $pathtofederate_Weather
xterm -fg orange    -bg black -l -lf $logs_directory/weather-${timestamp}.log               -T "Weather"               -geometry 140x40+200+60 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Weather-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=WeatherConfig.json" &
cd $pathtofederate_Database
xterm -fg lightblue -bg black -l -lf $logs_directory/database-${timestamp}.log              -T "Database"              -geometry 140x40+200+80 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Database-0.0.1-SNAPSHOT.jar  $pathtofederate_Database/Database.json" &


##################################
# run the other federates
##################################
cd $pathtofederate_Grid
xterm -fg green     -bg black -l -lf $logs_directory/Grid-${timestamp}.log                  -T "Grid"                  -geometry 140x40+180+60 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Grid-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/GridConfig.json" &
cd $pathtofederate_Loads
xterm -fg yellow    -bg black -l -lf $logs_directory/Loads-${timestamp}.log                 -T "Loads"                 -geometry 140x40+180+80 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Loads-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/LoadsConfig.json" &
cd $pathtofederate_LocalController
xterm -fg green     -bg white -l -lf $logs_directory/LocalController-${timestamp}.log       -T "LocalController"       -geometry 140x40+180+100 -e "java  -Dlog4j.configurationFile=$LOG4J -jar LocalController-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/LocalControllerConfig.json" &
cd $pathtofederate_SupervisoryController
xterm -fg blue      -bg white -l -lf $logs_directory/SupervisoryController-${timestamp}.log -T "SupervisoryController" -geometry 140x40+180+110 -e "java  -Dlog4j.configurationFile=$LOG4J -jar SupervisoryController-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/SupervisoryControllerConfig.json" &
cd $pathtofederate_TransactiveAgent
xterm -fg red       -bg white -l -lf $logs_directory/TransactiveAgent-${timestamp}.log      -T "TransactiveAgent"      -geometry 140x40+180+120 -e "java  -Dlog4j.configurationFile=$LOG4J -jar TransactiveAgent-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/TransactiveAgentConfig.json" &

cd $pathtofederate_Market
xterm -fg teal      -bg white -l -lf $logs_directory/Market-${timestamp}.log                -T "Market"                -geometry 140x40+180+130 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Market-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/MarketConfig.json" &
cd $pathtofederate_Generators
xterm -fg silver    -bg black -l -lf $logs_directory/Generators-${timestamp}.log            -T "Generators"            -geometry 140x40+180+140 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Generators-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/GeneratorsConfig.json" &
cd $pathtofederate_Storage
xterm -fg purple    -bg white -l -lf $logs_directory/Storage-${timestamp}.log               -T "Storage"               -geometry 140x40+180+150 -e "java  -Dlog4j.configurationFile=$LOG4J -jar Storage-0.1.0-SNAPSHOT.jar  -federationId=TEChallenge -configFile=conf/StorageConfig.json" &




##################################
# start the simulation
##################################
read -n 1 -r -s -p "Press any key to start the federation execution..."
printf "\n"

curl -o /dev/null -s -X POST http://127.0.0.1:8083/fedmgr --data '{"action": "START"}' -H "Content-Type: application/json"


##################################
# terminate the simulation
##################################
read -n 1 -r -s -p "Press any key to terminate the federation execution..."
printf "\n"

curl -o /dev/null -s -X POST http://127.0.0.1:8083/fedmgr --data '{"action": "TERMINATE"}' -H "Content-Type: application/json"

printf "Waiting for the federation manager to terminate.."
while $(curl -o /dev/null -s -f -X GET http://127.0.0.1:8083/fedmgr); do
    printf "."
    sleep 5
done
printf "\n"



