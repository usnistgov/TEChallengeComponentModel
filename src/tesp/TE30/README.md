## Overview

This project converts the PNNL TE30 example project (https://github.com/pnnl/tesp/tree/v0.9/examples/te30) into UCEF federation of Java Federates.

## Installation
Run the build.sh script to compile the code. Then execute the following command to install the python dependencies:

`pip3 install -r TE30_generated/TE30-java-federates/TE30-impl-java/PyPower/src/main/resources/requirements.txt`

## Running
Run the run.sh script to run the TE30 example.

The Auction will output a NumberFormatException: empty String frequently. This is because it tries to read a floating point value from HLA that is not always present, sometimes leading to this error message. This should have no impact on performance and can be ignored.

The GridLAB-D federate will crash on the final time step, causing all other federates to freeze. This is the expected behavior of GridLAB-D when run in server mode; the other federates will need to be closed by hand.

The terminal output will be recorded in the logs/ directory. A new log will be created for each run with the timestamp of the run contained in the filename.

The data output (CSV) will be recorded in the TE30_deployment/target/classes/ directory. Only the most recent run will be recorded; previous runs will be deleted when the run.sh script is executed.

## Components

### WebGME Model
The WebGME model used to generate the federates is webgme/TE30.webgmex

### Auction Federate
The Auction federate is a direct Java conversion of the __substation.py__ script in tesp_support (https://github.com/pnnl/tesp/blob/v0.9/src/tesp_support/tesp_support/substation.py). It contains the following classes:

- Auction.java – Java federate that implements the main substation_loop
- AuctionConfig.java - configuration options for the Java federate
- ClearingType.java – describes the market clearing type
- Curve.java – accumulates a set of price and quantity bids for later aggregation
- SimpleAuction.java – double-action mechanism for the 5-minute markets
- hvac.java – controls the responsive thermostat for one house

The Auction federate defines a new configuration option configFileName that points to a JSON configuration file used to configure both the double-auction market and all (30) of the house controllers. Example JSON files can be found in the TE30_generated/TE30-java-federates/TE30-impl-java/Auction/src/main/resources directory.

The Auction federate writes two output files: auction.csv contains the market clearing information, and simple_auction.txt writes debug information.

### PyPower Federate
The PyPower Federate solves optimal power flow (OPF) and power flow (PF) to obtain the locational marginal price (LMP), load, generation, losses, and bus voltage at the GridLAB-D substation. The OPF and PF are solved using runopf.py and runpf.py scripts in the pypower libarary. These scripts (and all relevant sub-modules) were not converted to Java but are executed by the PyPower federate using the ProcessBuilder class.

The PyPower federate requires a JSON configuration file that defines the bus parameters and non-responsive loads for the distribution system being simulated in GridLAB-D. Examples for these files can be found in the TE30_generated/TE30-java-federates/TE30-impl-java/PyPower/src/main/resources directory.

The PyPower federate outputs the results of the power flow equations to the file pypower.csv.

### ExternalLoad Federate
The ExternalLoad federate is a simple file reader that replays a recorded run of EnergyPlus to the federation. This federate will always replay the same scenario and will not react to publications from other federates.

The ExternalLoad federate uses a text file annotated with logical time to determine which values to publish to the federation. An example file can be found in the TE30_generated/TE30-java-federates/TE30-impl-java/ExternalLoad/src/main/resources directory.

The ExternalLoad federate outputs what it publishes to the federation each time step to ExternalLoad.csv.

### GridLAB-D Federate
The electric power distribution systems implemented in GridLAB-D were edited and parametrized to allow stand-alone simulations. Specifically, the edits in the TE30 distribution system model included disabling of the connection module and of the object fncs_msg responsible for the FNCS communication. Further, the residential time-of-use rates for all triplex_meters were replaced with rates published by the Auction federate and the bill_mode settings were changed from TIERED_TOU to UNIFORM. In addition, the project team had to disable all mertrics_collector_writer and recorder objects, and reduce the load scale factor to 400 to improve performance and reduce simulation times. The modified model can be found in the TE30_generated/Grid30/model directory.
