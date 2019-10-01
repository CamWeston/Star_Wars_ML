import json
from botocore.vendored import requests
import twitter


def lambda_handler(event, context):
    # TODO implement
    val = "httpEndpoint not created for " + str(event['httpMethod'])
    if event['httpMethod'] == "POST":
        toJson = json.loads(event['body'])

        handle = toJson['handle']

        api = twitter.Api(consumer_key='87wTE0djl7gryEqC8XO9Ah6A3',
                          consumer_secret='4DgeEVYjzOqpi0DvcdlaNRyA3sljLdwrlAeERCfP65lUKj7zd1',
                          access_token_key='1173976024377450497-UQgVNZFhg4gQCPu53bM8QX8FRzmQ3y',
                          access_token_secret='ADmkJqelEIAQEcLNPJGHcO5ku72e7J3OuW5e7A0SRHaxh', )
        statuses = api.GetUserTimeline(screen_name=handle)
        tweet_dict = {}
        for s in statuses:
            tweet_dict[s.created_at] = s.text

        val = tweet_dict

    elif event['httpMethod'] == "GET":
        val = "GET"

    return {
        'statusCode': 200,
        'body': json.dumps(val)
    }
