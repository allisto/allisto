
# A very simple Flask Hello World app for you to get started with...

from flask import Flask
from flask_restful import reqparse, Resource, Api
import random

app = Flask(__name__)
api = Api(app)


@app.route('/')
def hello_world():
    return 'Hello from Flask!'


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

        value = random.randint(0, 1)

        if value == 1:
            prediction = 'Autistic'
        else:
            prediction = 'Non Autistic'

        output = {'prediction': prediction}

        return output


api.add_resource(AllistoAPI, '/api')
if __name__ == '__main__':
    app.run(debug=True)
