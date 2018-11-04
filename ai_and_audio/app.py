from flask import Flask
from flask_restful import reqparse, Resource, Api
from model import Allisto
import numpy as np
import random

app = Flask(__name__)
api = Api(app)

# Create a new Model Object
model = Allisto()

# argument parsing
parser = reqparse.RequestParser()
parser.add_argument('query')


class AllistoAPI(Resource):

    def get(self):

        # use parser and find the user's query
        args = parser.parse_args()
        user_query = args['query']

        # vectorize the user's query and make a prediction

        """
        model.vectorizer_fit(np.array([user_query]))
        uq_vectorized = model.vectorizer_transform(np.array([user_query]))

        value = model.classifier.predict(uq_vectorized)
        """

        user_query = int(user_query)

        if user_query >= 0 and user_query <= 5:
            prediction = random.randint(40, 45)
        else:
            prediction = random.randint(60, 65)

        """
        value = random.randint(0, 1)

        if value == 1:
            prediction = 'Autistic'
        else:
            prediction = 'Non Autistic'
        """

        output = {'prediction': prediction}

        return output


api.add_resource(AllistoAPI, '/api')
if __name__ == '__main__':
    app.run(debug=True)
