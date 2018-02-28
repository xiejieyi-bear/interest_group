#!/bin/bash


work_path=$(dirname $(readlink -f "$0"))
echo $work_path

cd ..
rm -rf release
mkdir -p release

cd build
./package_client.sh
./package_nginx_image.sh
./package_server.sh
./package_env.sh

echo ******************package sueccess  *****************
