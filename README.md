# Star_Wars_ML

Understanding the AWS API Gateway -> Lambda twitter function call

Using the lamda endpoint and ex: handle this will return twitter info:

handle = { 'handle' : 'realDonaldTrump' }
lamdaEndpoint = 'https://q6yxb3mll8.execute-api.us-east-1.amazonaws.com/live/handle'
response = requests.post(url=lamdaEndpoint, data=json.dumps(handle))
print(response.text)
