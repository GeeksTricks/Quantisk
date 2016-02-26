from flask import Flask, got_request_exception
from flask_restful import Api
from .models import db
import os

app = Flask(__name__)
api = Api(app, catch_all_404s=True, prefix='/v1')
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


if not app.debug:
    import logging
    import sys
    handler = logging.StreamHandler(sys.stderr)
    formatter = logging.Formatter('[%(asctime)s] %(levelname)s: %(message)s')
    handler.setFormatter(formatter)
    handler.setLevel(logging.INFO)
    app.logger.addHandler(handler)


def log_exception(sender, exception):
    url = request.path
    data = request.data
    sender.logger.error('Got exception at %s: %s. Request: %s', url, exception, data)

got_request_exception.connect(log_exception, app)

from .views import *

api.add_resource(PersonListResource, '/persons/')
api.add_resource(PersonResource, '/persons/<int:id>/')
api.add_resource(WordPairsForPersonListResource, '/persons/<int:person_id>/wordpairs/')
api.add_resource(WordPairListResource, '/wordpairs/')
api.add_resource(WordPairResource, '/wordpairs/<int:id>/')
api.add_resource(SiteListResource, '/sites/')
api.add_resource(SiteResource, '/sites/<int:id>/')
api.add_resource(TotalRankResource, '/totalrank/<int:site_id>/')
api.add_resource(DailyRankResource, '/dailyrank/')
api.add_resource(UserListResource, '/users/')
api.add_resource(UserResource, '/users/<int:id>/')
