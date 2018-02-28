#!/bin/bash

# 前提条件，安装了git
git clone https://github.com/xiejieyi-bear/interest_group.git

work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
root_path=$(dirname $(readlink -f "$0"))

rm -rf release
mkdir -p release

# 打包client
echo ******************1. client********************
cd $root_path/client
# 复制二进制文件
cp $root_path/3rdparty/node_module.zip ./
unzip node_module.zip
npm run build:prod
cp $root_path/client/dist $root_path/release

echo ******************1. client end****************

echo ******************2. build nginx image*********

# 打包nginx docker 镜像
cd $root_path/src/nginx

cp -rf $root_path/client/dist ./
docker build -t xiejieyi/nginx .

echo ******************2. build nginx image end*****

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

echo ******************4. package environment*************
cd $root_path/release
mkdir hobby_group
cd hobby_group
# 1.复制nginx文件
mkdir nginx
cp -rf $root_path/src/nginx/conf nginx/
# 2.复制启动脚本
mkdir bin
cp $root_path/build/start.sh bin/
# 3.安装脚本
cp $root_path/build/install.sh ./

echo ******************4. package environment end*********

echo ******************package sueccess  *****************