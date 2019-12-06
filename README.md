# Star Wars ML
This branch contains all work for the machine learning functionality of the *Star Wars ML* app.
The directory *ipythonNotebooks* contains source work that was the foundation for the deployed Python ML functions. 

The directory *pythonMlWork* contains the initial ports from iPython notebooks to actual Python scripts.

Finally, *cloudSource* contains the actual package deployed onto Google Cloud Platform (GCP) for use within the Java app. In this directory, *requirements.txt* defines the Python libraries that GCP must install in order to use the ML functionality, *sw_model_train_data.csv* is the training data used to construct the ML model in Python, and *main.py* contains the Python script that the Android client accesses. It takes in a set of Tweets to analyze and map to a Star Wars character, then returns a String: the name of the predicted Star Wars character.
