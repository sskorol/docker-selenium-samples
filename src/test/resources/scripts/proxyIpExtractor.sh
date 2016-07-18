#!/bin/bash
PROXY_NAME=`docker ps --format {{.Names}} | grep 'proxy'`
IP=`docker inspect --format {{.NetworkSettings.IPAddress}} $PROXY_NAME`
echo $IP