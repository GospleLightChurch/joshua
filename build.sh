#!/usr/bin/env bash

set HOME = `pwd`

cd app-web/src/main/websrc
webpack --progress --colors

cd $HOME
