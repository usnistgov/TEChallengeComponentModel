cd Grid_generated
mvn clean $1 install
cd ..
cd Loads_generated
mvn clean $1 install
cd ..
cd LocalController_generated
mvn clean $1 install
cd ..
cd SupervisoryController_generated
mvn clean $1 install
cd ..
cd TransactiveAgent_generated
mvn clean $1 install
cd ..
cd Generators_generated
mvn clean $1 install
cd ..
cd Storage_generated
mvn clean $1 install
cd ..
cd Market_generated
mvn clean $1 install
cd ..
cd TEChallenge_generated
mvn clean $1 install
cd ..
cd TEChallenge_deployment
mvn clean $1 install
cd ..


