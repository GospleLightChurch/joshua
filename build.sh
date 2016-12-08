#!/usr/bin/env bash

set HOME = `pwd`

cd app-web/src/main/websrc
gulp css
webpack

cd $HOME
