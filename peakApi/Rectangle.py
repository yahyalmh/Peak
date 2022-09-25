import json

from flask_restful import Resource


class Rectangle(Resource):
    def get(self):
        with open('rectangles.json') as f:
            data = json.load(f)
            return {'data': data}, 200  # return data and 200 OK code
