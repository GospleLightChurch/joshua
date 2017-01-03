#!/usr/bin/env bash

# this shell script use to manage website package
# - start package
# - stop package
# - restart package

# common variable definition
applicationName="Website Tool"

OP=$1
APP=$2
RMI_HOSTNAME=$3
RMI_PORT=$4

startApplication() {
    nohup java \
    -Djava.rmi.server.hostname=$RMI_HOSTNAME \
    -Dcom.sun.management.jmxremote.port=$RMI_PORT \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -jar $HOME/website/$APP/$APP.jar > /dev/null &
}

stopApplication() {
    ps aux | grep $APP.jar | awk '{print $2}' | xargs kill -9
}

printUsage() {
    echo -e "$applicationName Usage: \
        \n\t wsite start   [application] [hostname] [port]\
        \n\t wsite stop    [application]\
        \n\t wsite restart [application] [hostname] [port]"
    exit -1;
}


main(){
    case $OP in
        start)
            echo "start application $APP with remote address $RMI_HOSTNAME:$RMI_PORT"
            startApplication
        ;;
        stop)
            echo "stop application $APP"
            stopApplication
        ;;
        restart)
            echo "stop application $APP"
            stopApplication
            echo "start application $APP with remote address $RMI_HOSTNAME:$RMI_PORT"
            startApplication
        ;;
        *)
            printUsage
        ;;
    esac
}

main