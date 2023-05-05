#!/usr/bin/env bash
echo "#### Create QuarkusFruits table ####"
aws dynamodb create-table --endpoint-url=http://localhost:4566 \
  --table-name unicorn-locations \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
  --profile=localstack \
  --region=us-east-1
echo "#### Dynamodb init completed"
