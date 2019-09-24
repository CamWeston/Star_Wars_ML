import twitter
import json



api = twitter.Api(consumer_key='87wTE0djl7gryEqC8XO9Ah6A3',
                      consumer_secret='4DgeEVYjzOqpi0DvcdlaNRyA3sljLdwrlAeERCfP65lUKj7zd1',
                      access_token_key='1173976024377450497-UQgVNZFhg4gQCPu53bM8QX8FRzmQ3y',
                      access_token_secret='ADmkJqelEIAQEcLNPJGHcO5ku72e7J3OuW5e7A0SRHaxh',)

print(api.VerifyCredentials())


statuses = api.GetUserTimeline(screen_name='realDonaldTrump')



tweet_dict = {}
for s in statuses:
    tweet_dict[s.created_at] = s.text

tweets_json = { 'tweets' :[tweet_dict] }
print(json.dumps(tweets_json))


