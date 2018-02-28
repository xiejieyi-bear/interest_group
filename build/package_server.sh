#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
root_path=$(dirname $(readlink -f "$0"))


echo ******************3. build server image********

cd $root_path/src/eureka_server
mvn clean
mvn package docker:build

cd $root_path/src/auth
mvn clean
mvn package docker:build

cd $root_path/src/gateway
mvn clean
mvn package docker:build

echo ******************3. build server image end**********

