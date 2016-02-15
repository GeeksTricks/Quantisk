from flask import Flask, jsonify
from flask_restful import Api
from .views import *
from .models import db
import os
from werkzeug.exceptions import default_exceptions
from werkzeug.exceptions import HTTPException


def make_json_app(import_name, **kwargs):
    def make_json_error(ex):
        response = jsonify(message=str(ex))
        response.status_code = (ex.code
                                if isinstance(ex, HTTPException)
                                else 500)
        return response

    app = Flask(import_name, **kwargs)

    for code in default_exceptions.keys():
        app.error_handler_spec[None][code] = make_json_error

    return app


# app = Flask(__name__)
app = make_json_app(__name__)
api = Api(app)
db.app = app
db.init_app(app)

app.config['RESTFUL_JSON'] = {'ensure_ascii': False}

db_path = os.environ.get('OPENSHIFT_DATA_DIR', os.path.dirname(__file__)) 
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + db_path + '/GeeksDB.db'
# app.config['SQLALCHEMY_ECHO'] = True

db.create_all()

api.add_resource(PersonListResource, '/v1/persons/')
api.add_resource(PersonResource, '/v1/persons/<int:id>/')
api.add_resource(WordPairsForPersonResource, '/v1/persons/<int:person_id>/wordpairs/')
api.add_resource(WordPairListResource, '/v1/wordpairs/')
api.add_resource(WordPairResource, '/v1/wordpairs/<int:id>/')
api.add_resource(SiteListResource, '/v1/sites/')
api.add_resource(SiteResource, '/v1/sites/<int:id>/')
api.add_resource(TotalRankResource, '/v1/totalrank/<int:site_id>/')
api.add_resource(DailyRankResource, '/v1/dailyrank/')




