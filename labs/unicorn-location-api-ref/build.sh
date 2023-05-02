#!/bin/bash

# Build the AWS SAM application
sam build

# Deploy the AWS SAM application
sam deploy --guided --stack-name unicorn-location-api

export API_GW_URL=$(aws cloudformation describe-stacks --stack-name unicorn-location-api | jq -r '.Stacks[0].Outputs[] | select(.OutputKey == "UnicornLocationApi").OutputValue')
