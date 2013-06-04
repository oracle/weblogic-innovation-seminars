#!/bin/bash

cd ${DEMOS_HOME}/environment

mvn -P stop-domain
mvn -P start-domain
