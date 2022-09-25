from flask import Flask
from flask_restful import Api

from Rectangle import Rectangle

app = Flask(__name__)
api = Api(app)
api.add_resource(Rectangle, '/rectangles')

if __name__ == '__main__':
    app.run()
