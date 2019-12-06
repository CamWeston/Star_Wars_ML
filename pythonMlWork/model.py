from sklearn.linear_model import SGDClassifier
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import pandas as pd
import numpy as np


def load_model_manual():

	# Load in data
	data = pd.read_csv('sw_ml_optimal_data.csv')

	# Format data
	train_data = data.loc[:1600]
	test_data = data.loc[1601:]
	train = train_data.groupby(['character'])['dialogue'].apply(lambda text: ''.join(text.to_string(index=False))).str.replace('(\\n)', '').reset_index()
	test = test_data.groupby(['character'])['dialogue'].apply(lambda text: ''.join(text.to_string(index=False))).str.replace('(\\n)', '').reset_index()

	# Define model pipeline and fit data
	pipeline = Pipeline([('vect', CountVectorizer(stop_words='english')), ('tfidf', TfidfTransformer()), ('clf', SGDClassifier(loss='hinge', penalty='l2', alpha=1e-3, random_state=42, max_iter=5, tol=None)),])
	sgd = pipeline.fit(train.dialogue, train.character)


def load_model_auto(predict: bool = False):
	"""
	Uses formatted train and test data
	"""

	# Load formatted data
	train = pd.read_csv('sw_model_train_data.csv')
	test = pd.read_csv('sw_model_test_data.csv')

	# Define model pipeline and fit data
	pipeline = Pipeline([('vect', CountVectorizer(stop_words='english')), ('tfidf', TfidfTransformer()), ('clf', SGDClassifier(loss='hinge', penalty='l2', alpha=1e-3, random_state=42, max_iter=5, tol=None)),])
	sgd = pipeline.fit(train.dialogue, train.character)

	if predict:
		predictions_sample(pipeline, train, test)


def predictions_sample(pipeline, train, test):
	"""
	A prediction example
	"""
	
	# Predict test data
	sgd = pipeline.predict(test.dialogue)

	# Determine accuracy
	accuracy = np.mean(sgd == test.character)

	# Output
	predictions = sgd
	actual_values = test.character
	print("{0:20s} {1}".format("Prediction", "True Value"))
	for x, y in zip(predictions, actual_values): 
	    print("{0:20s} {1}".format(x, y))
	print('\nSGD Classifier Accuracy: {x}%'.format(x = accuracy*100))


def main():

	load_model_auto(predict=True)


if __name__ == '__main__':
	main()