#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path
cd ..
root_path=$(dirname $(readlink -f "$0"))

echo ******************4. package env*********************
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

