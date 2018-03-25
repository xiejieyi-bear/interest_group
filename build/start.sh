#!/bin/bash
# 运行时
# 创建mysql

#docker pull mysql:5.6

# MYSQL_ROOT_PASSWORD 指定 root密码
docker run -p 3306:3306 --name mymysql -v $PWD/data:/var/lib/mysql \
-e MYSQL_DATABASE="db_example" \
-e MYSQL_USER="springuser" \
-e MYSQL_PASSWORD="root" \
-e MYSQL_ROOT_PASSWORD="Admin123" \
-d mysql:5.6

# 初始化数据库

# docker run -it --link mymysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
docker run --name auth --net="host"  -d xiejieyi/auth
docker run --name server --net="host"  -d xiejieyi/eureka_server
docker run --name gateway --net="host"  -d xiejieyi/gateway
docker run --name mynginx --net="host"  -v /home/hobby_user/nginx/conf:/etc/nginx/:ro -d xiejieyi/nginx
