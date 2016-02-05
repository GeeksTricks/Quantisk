from flask import Flask
from flask_restful import Api
from .views import *
from .models import db
import os

app = Flask(__name__)
api = Api(app)
db.app = app
db.init_app(app)
app.config['RESTFUL_JSON'] = {'ensure_ascii': False}
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.dirname(__file__) + '/GeeksDB.db'


api.add_resource(PersonsListResource, '/persons/')
api.add_resource(PersonResource, '/persons/<id>/')
api.add_resource(WordPairListResource, '/wordpairs/')
api.add_resource(WordPairResource, '/wordpairs/<id>/')
api.add_resource(SiteListResource, '/sites/')
api.add_resource(SiteResource, '/sites/<id>/')



