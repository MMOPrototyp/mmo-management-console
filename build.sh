#!/bin/bash

# Git Pull
git fetch --all
git reset --hard origin/master
git pull

chmod 755 build.sh

mvn clean install

cp -rf mmo-management-main/target/mmo-management-main-1.0.0-SNAPSHOT.jar ../bin/app.jar
#cp -rf mmo-management-main/target/lib ../bin/lib

# Copy configuration
cp ../config/application.properties ../bin/application.properties

