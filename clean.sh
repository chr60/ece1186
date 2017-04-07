#!/bin/bash

#A helper script for cleaning local builds for simplicty. May be used more.
mvn clean
rm -rf docs
find . '-name' '.DS_Store' -type f -delete
find . '-name' '*.class' -type f -delete