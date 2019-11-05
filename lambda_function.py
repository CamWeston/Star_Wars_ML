import json
# from botocore.vendored import requests
import twitter
import re
import string
import time



def strip_all_entities(text):
    entity_prefixes = ['@', '#']
    for separator in string.punctuation:
        if separator not in entity_prefixes:
            text = text.replace(separator, ' ')
    words = []
    for word in text.split():
        word = word.strip()
        if word:
            if word[0] not in entity_prefixes:
                words.append(word)
    return ' '.join(words)


def clean_tweets(tweet):
    tweet = re.sub('https?://[A-Za-z0-9./]+', '', tweet)  # remove links
    tweet = re.sub('http?://[A-Za-z0-9./]+', '', tweet)
    tweet.encode('ascii', 'ignore').decode('ascii')  # decode emoji
    emoji_pattern = re.compile("["
                               u"\U0001F600-\U0001F64F"  # emoticons
                               u"\U0001F300-\U0001F5FF"  # symbols & pictographs
                               u"\U0001F680-\U0001F6FF"  # transport & map symbols
                               u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
                               "]+", flags=re.UNICODE)
    tweet = emoji_pattern.sub(r'', tweet)  # no emoji
    tweet = strip_all_entities(tweet)
    return tweet


def lambda_handler(event, context):
    # TODO implement
    val = "httpEndpoint not created for " + str(event['httpMethod'])
    if event['httpMethod'] == "POST":
        val = "POST"

    elif event['httpMethod'] == "GET":
        handle = event['headers']['Handle']
        api = twitter.Api(consumer_key='87wTE0djl7gryEqC8XO9Ah6A3',
                          consumer_secret='4DgeEVYjzOqpi0DvcdlaNRyA3sljLdwrlAeERCfP65lUKj7zd1',
                          access_token_key='1173976024377450497-UQgVNZFhg4gQCPu53bM8QX8FRzmQ3y',
                          access_token_secret='ADmkJqelEIAQEcLNPJGHcO5ku72e7J3OuW5e7A0SRHaxh', )
        userExists = True
        prof_img_url = None
        try:
            prof_img_url = api.GetUser(screen_name=handle)
        except:
            userExists = False

        if userExists:
            statuses = api.GetUserTimeline(screen_name=handle)

        tweet_list = []
        tweet_dict = {'tweets': tweet_list,
                      'prof_image': prof_img_url.profile_image_url if prof_img_url else None,
                      'user_exists' : userExists
                            }
        if userExists:

            statuses = api.GetUserTimeline(screen_name=handle, count=30)  # might be a fix


            for s in statuses:
                s.text = clean_tweets(s.text) #removes emojis/links
                if len(s.text) > 0:
                    tweet_dict['tweets'].append(s.text)

        val = tweet_dict
    return {
        'statusCode': 200,
        'body': json.dumps(val)
    }

# # for testing on local
# event = { 'httpMethod' : 'GET', 'headers' : {'Handle' : 'realDonaldTrump'}}
# context =  {}
# print(lambda_handler(event, context))