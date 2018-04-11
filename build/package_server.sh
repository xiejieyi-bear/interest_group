#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
root_path=$(dirname $(readlink -f "$0"))

horber_registry=10.129.84.9/xiejieyi
publish_version=0.1
self_registry=xiejieyi

echo ******************3. build server image********

cd $root_path/src/eureka_server
mvn clean
mvn package docker:build
docker tag $self_registry/eureka_server:latest $horber_registry/eureka_server:$publish_version
docker push  $horber_registry/eureka_server:$publish_version

cd $root_path/src/auth
mvn clean
mvn package docker:build

docker tag $self_registry/auth:latest $horber_registry/auth:$publish_version
docker push  $horber_registry/auth:$publish_version

cd $root_path/src/gateway
mvn clean
mvn package docker:build

docker tag $self_registry/gateway:latest $horber_registry/gateway:$publish_version
docker push  $horber_registry/gateway:$publish_version

echo ******************3. build server image end**********

