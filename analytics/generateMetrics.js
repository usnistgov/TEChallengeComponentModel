#!/usr/bin/env node
'use strict';

const program = require('commander');
const math = require('mathjs');
const fs = require("fs");

(async function () {

	let configFileName = '';

	program
		.version('1.0.0')
		.arguments('<configFile>')
		.action(function (configFile) {
			configFileName = configFile;
		});

	program.parse(process.argv);

	if (typeof configFileName === 'undefined' || configFileName === '') {
		console.error('no configFile given!');
		process.exit(1);
	}
	console.log('configFile:', configFileName);

	try {

		let config = JSON.parse(fs.readFileSync(__dirname + '/' + configFileName , 'utf8'));

		// let params = config.params;

		let path = __dirname + '/' + config.inputDir + '/';

		//////////////////////////
		//////////////////////////
		console.info("--------- ObjectRoot.gridVoltageState ---------------");
		let result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.gridVoltageState.json', 'utf8'));

		let arrays = {
			grid_Voltage_Real_A : [],
			grid_Voltage_Real_B : [],
			grid_Voltage_Real_C : [],
			grid_Voltage_Imaginary_A : [],
			grid_Voltage_Imaginary_B : [],
			grid_Voltage_Imaginary_C : []
		};

		result.forEach(function(row){

			console.log(JSON.stringify(row));

			arrays.grid_Voltage_Real_A.push(row.grid_Voltage_Real_A);
			arrays.grid_Voltage_Real_B.push(row.grid_Voltage_Real_B);
			arrays.grid_Voltage_Real_C.push(row.grid_Voltage_Real_C);

			arrays.grid_Voltage_Imaginary_A.push(row.grid_Voltage_Imaginary_A);
			arrays.grid_Voltage_Imaginary_B.push(row.grid_Voltage_Imaginary_B);
			arrays.grid_Voltage_Imaginary_C.push(row.grid_Voltage_Imaginary_C);

		});

		let results = {
			grid_Voltage_Real_A_avg : math.mean(arrays.grid_Voltage_Real_A),
			grid_Voltage_Real_B_avg : math.mean(arrays.grid_Voltage_Real_B),
			grid_Voltage_Real_C_avg : math.mean(arrays.grid_Voltage_Real_C),
			grid_Voltage_Imaginary_A_avg : math.mean(arrays.grid_Voltage_Imaginary_A),
			grid_Voltage_Imaginary_B_avg : math.mean(arrays.grid_Voltage_Imaginary_B),
			grid_Voltage_Imaginary_C_avg : math.mean(arrays.grid_Voltage_Imaginary_C),
		};

		console.info("Results: " + JSON.stringify(results));


		////////////////////////////
		////////////////////////////
		// result = await getData(connection,'ObjectRoot.Quote');
		console.info("--------- ObjectRoot.Quote ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.Quote.json', 'utf8'));
		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		//////////////////////////
		//////////////////////////
		console.info("--------- ObjectRoot.Tender ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.Tender.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		////////////////////////////
		////////////////////////////
		console.info("--------- ObjectRoot.Transaction ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.Transaction.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		////////////////////////////
		////////////////////////////
		console.info("--------- ObjectRoot.marketStatus ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.marketStatus.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		////////////////////////////
		////////////////////////////
		console.info("--------- ObjectRoot.supervisoryControlSignal ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.supervisoryControlSignal.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		////////////////////////////
		////////////////////////////
		console.info("--------- ObjectRoot.resourcesPhysicalStatus ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.resourcesPhysicalStatus.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

		////////////////////////////
		////////////////////////////
		console.info("--------- ObjectRoot.resourceControl ---------------");
		result = JSON.parse(fs.readFileSync(path + 'ObjectRoot.resourceControl.json', 'utf8'));

		result.forEach(function(row){
			console.log(JSON.stringify(row));
		});

	} catch (err) {
		console.error(err.message);
	} finally {

	}

})();

