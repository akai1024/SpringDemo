#!/bin/bash

JAR = "$2";

if [ "$1" == "run" ]; then
    java -jar "$2"
else if [ "$1" == "start" ]; then
    nohup java -jar "$JAR" >/dev/null 2>&1 &
    echo "Application is starting."
else if [ "$1" == "stop" ]; then
    PID=$(ps -ef | grep "$JAR" | grep -v grep | awk '{ print $2 }')
    if [ -z "$PID" ]; then
        echo Application is already stopped
    else
        echo kill $PID
        kill $PID
    fi
else if [ "$1" == "status" ]; then
    PID=$(ps -ef | grep "$JAR" | grep -v grep | awk '{ print $2 }')
    if [ -z "$PID" ]; then
        echo Application is stopped
    else
        echo Application is running
        echo $PID
    fi
else echo wrong operate, available operate is run, start, stop, status
fi
fi
