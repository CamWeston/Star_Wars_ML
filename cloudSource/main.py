from sklearn.linear_model import SGDClassifier
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import pandas as pd
import json
from flask import escape

def load_model():
    """
    Uses formatted train and test data
    """

    # Load formatted data
    train = pd.read_csv('sw_model_train_data.csv')

    # Define model pipeline and fit data
    pipeline = Pipeline([('vect', CountVectorizer(stop_words='english')), ('tfidf', TfidfTransformer()), ('clf', SGDClassifier(loss='hinge', penalty='l2', alpha=1e-3, random_state=42, max_iter=5, tol=None)),])
    model = pipeline.fit(train.dialogue, train.character)

    return model

def predict(data):

    # Load the ML model and return prediction
    ai = load_model()
    return ai.predict(data)[0]
    
def handler(request):

    # Get and validate request, then return prediction based on packaged data
    request_json = request.get_json(silent=True)
    if request_json and 'tweets' in request_json:
        tweets = request_json['tweets']
        data = str()
        for tweet in tweets:
            data += ' ' + tweet
        return predict([data])
    else:
        return "Error: tweets could not be retrieved from the request you sent."
    
