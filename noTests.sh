#!/bin/bash

#A helper script for cleaning local builds for simplicty. May be used more.
function cleanAndRun() {
	mvn clean
	rm -rf docs
	find . '-name' '.DS_Store' -type f -delete
	find . '-name' '*.class' -type f -delete
	mvn package -DskipTests=true
	cd target
	java -jar train-station-1.0.jar
}
cleanAndRun
