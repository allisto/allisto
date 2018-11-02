import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.models import load_model
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler


class Allisto:

    def __init__(self):
        self.df = pd.read_csv('cry.csv')
        self.X = self.df.iloc[:, 0:19].values
        self.y = self.df.iloc[:, 20]
        self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(
            self.X, self.y, test_size=0.2, random_state=0)
        self.sc = StandardScaler()
        self.X_train = self.sc.fit_transform(self.X_train)
        self.X_test = self.sc.transform(self.X_test)
        self.classifier = Sequential()

    def build_model(self):
        self.classifier.add(Dense(output_dim=6, init='uniform',
                                  activation='relu', input_dim=19))
        self.classifier.add(
            Dense(output_dim=6, init='uniform', activation='relu'))
        self.classifier.add(
            Dense(output_dim=1, init='uniform', activation='sigmoid'))
        self.classifier.compile(
            optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    def train(self):
        self.classifier.fit(self.X_train, self.y_train,
                            batch_size=10, nb_epoch=100)

    def predict(self, input_params):
        return self.classifier.predict(input_params)>=0
        
        
    def save(self):
        self.classifier.save('model.h5')

    def load(self):
        self.classifier = load_model('model.h5')


model = Allisto()

import numpy as np
value = model.predict(np.array([[0.13201746241462,0.0846523059846263,0.130812215724496,0.0480311890838207,0.210136452241715,0.162105263157895,1.31334024389288,4.99252406943563,0.972450815429729,0.787036038235428,0.0282001299545159,0.13201746241462,0.178684467460517,0.015984015984016,0.275862068965517,0.751302083333333,0.0078125,6.1953125,6.1875
]]))
