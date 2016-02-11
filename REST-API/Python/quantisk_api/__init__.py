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
db_path = os.environ.get('OPENSHIFT_DATA_DIR', os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.dirname(__file__) + '/GeeksDB.db'


api.add_resource(PersonListResource, '/v1/persons/')
api.add_resource(PersonResource, '/v1/persons/<int:id>/')
api.add_resource(WordPairsForPersonResource, '/v1/persons/<int:id>/wordpairs/')
api.add_resource(WordPairListResource, '/v1/wordpairs/')
api.add_resource(WordPairResource, '/v1/wordpairs/<int:id>/')
api.add_resource(SiteListResource, '/v1/sites/')
api.add_resource(SiteResource, '/v1/sites/<int:id>/')
api.add_resource(TotalRankResource, '/v1/totalrank/<int:site_id>/')
api.add_resource(DailyRankResource, '/v1/dailyrank/')



