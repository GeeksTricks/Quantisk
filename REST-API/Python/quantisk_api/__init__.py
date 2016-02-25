from flask import Flask, jsonify
from flask_restful import Api
from .models import db
import os
from werkzeug.exceptions import default_exceptions
from werkzeug.exceptions import HTTPException


def make_json_app(import_name, **kwargs):
    def make_json_error(ex):
        response = jsonify(message=str(ex.description)
                            if isinstance(ex, HTTPException)
                            else str(ex))
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
mysql_local = 'mysql+pymysql://root@/api?unix_socket=/var/lib/mysql/mysql.sock?charset=utf8'
mysql_openshift = os.environ.get('OPENSHIFT_MYSQL_DB_URL', None)
if mysql_openshift:
    mysql_openshift = mysql_openshift.replace('mysql', 'mysql+pymysql')
    mysql_openshift += 'api?charset=utf8'
app.config['SQLALCHEMY_DATABASE_URI'] = mysql_openshift or mysql_local
# app.config['SQLALCHEMY_ECHO'] = True


db.create_all()

from .views import *

api.add_resource(PersonListResource, '/v1/persons/')
api.add_resource(PersonResource, '/v1/persons/<int:id>/')
api.add_resource(WordPairsForPersonListResource, '/v1/persons/<int:person_id>/wordpairs/')
api.add_resource(WordPairListResource, '/v1/wordpairs/')
api.add_resource(WordPairResource, '/v1/wordpairs/<int:id>/')
api.add_resource(SiteListResource, '/v1/sites/')
api.add_resource(SiteResource, '/v1/sites/<int:id>/')
api.add_resource(TotalRankResource, '/v1/totalrank/<int:site_id>/')
api.add_resource(DailyRankResource, '/v1/dailyrank/')
api.add_resource(UserListResource, '/v1/users/')
api.add_resource(UserResource, '/v1/users/<int:id>/')
