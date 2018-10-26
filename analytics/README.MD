

Setup for Ubuntu VM

    Install xsltproc:

        sudo apt-get install xsltproc

    Upgrade nodejs from v 4.5.0 to 10.8.0

        upgrade nodejs from v 4.5.0 to 10.8.0
        nvm use 10.8.0
        nvm alias default 10.8.0

    Edit .bashrc

    Towards the end of the file find export NVM_DIR

    change from this configuration:

        export NVM_DIR="/home/vagrant/.nvm"
        [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm
        export BOOST_NETWORK_INC_DIR=/usr/local/cpp-netlib/cpp-netlib-cpp-netlib-0.11.2-final
        export PATH=/opt/omnetpp/omnetpp-5.2.1/bin/:/home/vagrant/.nvm/versions/node/v4.5.0/bin:/home/vagrant/bin:/home/vagrant/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
        export OMNETPP_CONFIGFILE=/opt/omnetpp/omnetpp-5.2.1/Makefile.inc
        export PATH=/home/vagrant/ucefcodebase/ucef-devtools/test:/opt/omnetpp/omnetpp-5.2.1/bin/:/home/vagrant/.nvm/versions/node/v4.5.0/bin:/home/vagrant/bin:/home/vagrant/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
        export PS1='\[\e[1;32m\][\u@\h \W]$\[\e[0m\] '

    to this new configuration

        #export NVM_DIR="/home/vagrant/.nvm"
        #[ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm
        export BOOST_NETWORK_INC_DIR=/usr/local/cpp-netlib/cpp-netlib-cpp-netlib-0.11.2-final
        export PATH=/opt/omnetpp/omnetpp-5.2.1/bin/:/home/vagrant/.nvm/versions/node/v4.5.0/bin:/home/vagrant/bin:/home/vagrant/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
        export OMNETPP_CONFIGFILE=/opt/omnetpp/omnetpp-5.2.1/Makefile.inc
        export PATH=/home/vagrant/ucefcodebase/ucef-devtools/test:/opt/omnetpp/omnetpp-5.2.1/bin/:/home/vagrant/.nvm/versions/node/v4.5.0/bin:/home/vagrant/bin:/home/vagrant/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
        export PS1='\[\e[1;32m\][\u@\h \W]$\[\e[0m\] '

        export NVM_DIR="/home/vagrant/.nvm"
        [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"  # This loads nvm




The tool provides the following bash shell scripts

1) execGenerateObjDefs.sh

	- invokes xsltproc which transforms ./artifacts/TEChallenge.xml into sqlDefs.js
	- sqlDefs.js defines a function used to generate SQL query strings to obtain JSON objects from the DB

2) execGenerateJSON.sh

	- uses node.js to invoke generateJSON.js
	- a config.json file is provided as an input parameter
	- config.js allows configuration of the DB connection parameters, an output directory name and optional parameters (TBD)
	- scans the IndexTable for the name of all object and interaction tables
	- uses the sqlDefs.js getQueryString function to query all object and interaction tables to produce JOSN output files containing all objects and interactions as JSON
	- stdout includes processing time for production of each JSON file

3) execGenerateMetrics.sh

	- uses node.js to invoke generateMetrics.js
	- a config.json file is provided as an input parameter
	- config.js allows configuration of the DB connection parameters (although this is not used by generateMetrics.js), an output directory name and optional parameters (TBD)
	- uses the JSON files produced by generateJSON.js as data input
	- Demonstrates reading and parsing ObjectRoot.gridVoltageState.json and using the node math library to generate averages for the real and imaginary values across all time steps
	- Also demonstrates looping and producing stdout of the various other ObjectRoot JSON data


Open Issues for Discussion

— None of the processing currently limits queries or metrics to specific instanceName(s)
	- should the generated JSON objects be limited to specific instances or should a filter on the generation of the metrics be used?

— None of the processing currently limits queries or metrics production to specific timeStep ranges- is this a requirement?
	- should the generated JSON objects be limited to specific timeStep ranges or should a filter on the generation of the metrics be used?

— How should these parameters be provided? They could be in the config file or as command line parameters

— Need GitHub project to put project under source code control

— Once in GitHub need Marty to test on linux VM

— Reserve time for walk-through/discussion with NIST team

— Reserve time for working out VM if Marty discovers any anomalies etc.

Githubs:

