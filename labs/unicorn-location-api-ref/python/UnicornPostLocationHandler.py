import json
import os
import uuid
import boto3

dynamodb = boto3.client('dynamodb')

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])
        unicorn_location = UnicornLocation(body['unicornName'], body['latitude'], body['longitude'])
        create_location_item(unicorn_location)

        return {
            'statusCode': 200,
            'body': 'Received unicorn location: ' + json.dumps(unicorn_location.__dict__)
        }
    except Exception as e:
        print(f'Error while processing the request: {str(e)}')
        return {
            'statusCode': 400,
            'body': 'Error processing the request'
        }

def create_location_item(unicorn_location):
    item = {
        'id': {'S': str(uuid.uuid4())},
        'unicornName': {'S': unicorn_location.unicornName},
        'latitude': {'S': unicorn_location.latitude},
        'longitude': {'S': unicorn_location.longitude},
    }

    dynamodb.put_item(TableName='unicorn-locations', Item=item)

class UnicornLocation:
    def __init__(self, unicornName, latitude, longitude):
        self.unicornName = unicornName
        self.latitude = latitude
        self.longitude = longitude
