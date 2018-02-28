#!/bin/bash

user=hobby_user

# 检查用户是否存在
if id $user &> /dev/null ;then 
    # 存在，删除
    userdel -r  hobby_user     
fi

# 检查docker是否已安装
docker_version=`docker -v | grep version`
if [ -z "$STRING" ]; then 
    # 准备docker环境（暂时在线安装）离线安装未实施
    apt-get update

    #允许apt使用HTTPS的镜像源
    apt-get install \
        apt-transport-https \
        ca-certificates \
        curl \
        software-properties-common

    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -

    apt-key fingerprint 0EBFCD88

    add-apt-repository \
       "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
       $(lsb_release -cs) \
       stable"

    apt-get update
    apt-get install docker-ce
fi

# 创建用户名和密码，并加入docker组
groupadd docker
useradd hobby_user -d /home/hobby_user -p "Admin123"
sudo usermod -aG docker hobby_user

cp -rf ./nginx /home/hobby_user/
mkdir /home/hobby_user/mysql
mkdir -p /home/hobby_user/mysql/logs /home/hobby_user/mysql/data
cp -rf ./bin/start.sh /home/hobby_user/start.sh

