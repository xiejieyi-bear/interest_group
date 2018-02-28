#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
root_path=$(dirname $(readlink -f "$0"))

echo ******************2. build nginx image*********

# 打包nginx docker 镜像
cd $root_path/src/nginx

cp -rf $root_path/client/dist ./
docker build -t xiejieyi/nginx .

echo ******************2. build nginx image end*****

