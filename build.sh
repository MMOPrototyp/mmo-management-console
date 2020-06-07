#!/bin/bash

cd /opt/mmo-management-console/mmo-management-console

# Git Pull
git fetch --all
git reset --hard origin/master
git pull

chmod 755 build.sh

echo "Stop old screen session"
../bin/stopScreen.sh

echo "Start build"
mvn clean install

echo "copy jars and configuration"
cp -rf mmo-management-main/target/mmo-management-main-1.0.0-SNAPSHOT.jar ../bin/app.jar
#cp -rf mmo-management-main/target/lib ../bin/lib

# Copy configuration
cp -rf ../config/application.properties ../bin/application.properties

echo "Start new screen session"

cd /opt/mmo-management-console/mmo-management-console/bin
./startScreen.sh