#!/bin/bash

sed -i \
    -e "s/\${env:PostgresqlUser}/${PostgresqlUser}/g" \
    -e "s/\${env:PostgresqlPassword}/${PostgresqlPassword}/g" \
    "src/main/resources/application.yml"