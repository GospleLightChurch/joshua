#!/usr/bin/env bash

set HOME = `pwd`

cd app-web/src/main/resources/websrc
gulp css
webpack

cd $HOME
