#!/bin/bash

# Store the current directory
CURRENT_DIR=$(pwd)

# Change to the directory containing the Maven project
cd ./UnicornLocationFunction

# Build the Maven project
mvn clean install

# Change back to the original directory
cd $CURRENT_DIR

# Build the AWS SAM application
sam build

# Deploy the AWS SAM application
sam deploy --guided --stack-name unicorn-location-api

#export API_GW_URL=$(aws cloudformation describe-stacks --stack-name unicorn-location-api | jq -r '.Stacks[0].Outputs[] | select(.OutputKey == "UnicornLocationApi").OutputValue')
#artillery run -t $API_GW_URL loadtest.yaml
