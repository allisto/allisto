from flask import Flask
from flask_restful import Resource, Api
app = Flask(__name__)
api = Api(app)

# Create a new Model Object

clf_path = 'path'
# with open(clf_path, 'rb') as f:
# Open Persistant Model

# argument parsing
parser = reqparse.RequestParser()
parser.add_argument('query')


class Allisto(Resource):

    def get(self):

        # use parser and find the user's query
        args = parser.parse_args()
        user_query = args['query']

        # vectorize
        parameters = 1  # just in case

        if parameters == 1:
            prediction = 'Autistic'
        else:
            prediction = 'Non Autistic'

        output = {'prediction': prediction}

        return output


api.add_resource(Allisto, '/')
if __name__ == '__main__':
    app.run(debug=True)
