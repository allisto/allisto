import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from keras.models import Sequential
from keras.layers import Dense
from keras.models import model_from_json

df = pd.read_csv('cry.csv')
X = df.iloc[:, 0:19].values
y = df.iloc[:, 20]

from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=0)

from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

classifier = Sequential()
classifier.add(Dense(output_dim=6, init='uniform',
                     activation='relu', input_dim=19))
classifier.add(Dense(output_dim=6, init='uniform', activation='relu'))
classifier.add(Dense(output_dim=1, init='uniform', activation='sigmoid'))
classifier.compile(
    optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

classifier.fit(X_train, y_train, batch_size=10, nb_epoch=100)

y_pred = classifier.predict(X_test)
for index, item in enumerate(y_pred):
    if item >= 0.5:
        y_pred[index] = 1
    else:
        y_pred[index] = 0

y_pred = np.array(y_pred, dtype=int)
y_pred = pd.Series(y_pred)
y_test = pd.Series(y_test)

y_pred = y_pred.reshape(634,)

count = 0
for i in range(635):
    if y_pred[i] == y_test[i]:
        count = count+1


from sklearn.metrics import confusion_matrix
cm = confusion_matrix(y_test, y_pred)

# Evalutation of Model
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import cross_val_score
from keras.models import Sequential
from keras.layers import Dense


def build_classifier():
    classifier = Sequential()
    classifier.add(Dense(units=6, kernel_initializer='uniform',
                         activation='relu', input_dim=19))
    classifier.add(
        Dense(units=6, kernel_initializer='uniform', activation='relu'))
    classifier.add(
        Dense(units=1, kernel_initializer='uniform', activation='sigmoid'))
    classifier.compile(
        optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    return classifier


classifier = KerasClassifier(
    build_fn=build_classifier, batch_size=10, epochs=100)
accuracies = cross_val_score(estimator=classifier, X=X_train, y=y_train, cv=10)
mean = accuracies.mean()
variance = accuracies.std()
# tunning ann
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import GridSearchCV
from keras.models import Sequential
from keras.layers import Dense


def build_classifier(optimizer):
    classifier = Sequential()
    classifier.add(Dense(units=6, kernel_initializer='uniform',
                         activation='relu', input_dim=19))
    classifier.add(
        Dense(units=6, kernel_initializer='uniform', activation='relu'))
    classifier.add(
        Dense(units=1, kernel_initializer='uniform', activation='sigmoid'))
    classifier.compile(optimizer=optimizer,
                       loss='binary_crossentropy', metrics=['accuracy'])
    return classifier


classifier = KerasClassifier(build_fn=build_classifier)
parameters = {'batch_size': [25, 32],
              'epochs': [100, 500],
              'optimizer': ['adam', 'rmsprop']}
grid_search = GridSearchCV(estimator=classifier,
                           param_grid=parameters,
                           scoring='accuracy',
                           cv=10)
grid_search = grid_search.fit(X_train, y_train)
best_parameters = grid_search.best_params_
best_accuracy = grid_search.best_score_

# Saving file to json (Javascript Object Notation)

model = classifier

# TO H5

from keras.models import load_model

model.save('my_model.h5')  # creates a HDF5 file 'my_model.h5'


def save_model(model):
    model_json = model.to_json()
    with open("model.json", "w") as json_file:
        json_file.write(model_json)

    model.save_weights("model.h5")
    print("Saved model to disk")


def load_model(model):
    # load json and create model
    json_file = open('model.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights("model.h5")
    print("Loaded model from disk")

 # load json and create model
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
# load weights into new model
loaded_model.load_weights("model.h5")
print("Loaded model from disk")
y_pred = loaded_model.predict(X_test)
y_pred = y_pred > 0.5


save_model(model)
load_model(model)
