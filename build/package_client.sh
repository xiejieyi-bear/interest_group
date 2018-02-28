#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
git pull
root_path=$(dirname $(readlink -f "$0"))

# 打包client
echo ******************1. client********************
cd $root_path/client
# 复制二进制文件
npm install
npm run build:prod
cp $root_path/client/dist $root_path/release

echo ******************1. client end****************

