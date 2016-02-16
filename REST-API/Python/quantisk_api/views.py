from flask import request, abort
from flask_restful import Resource
from dateutil import parser
from .repositories import person_repo, wordpair_repo, site_repo, rank_repo
from .auth import auth


class SingleResource(Resource):
    repo = None
    method_decorators = [auth.login_required]

    def get(self, id):
        item = self.repo.get_by_id(id)
        if item is None:
            abort(404)
        return item._asdict()

    def put(self, id):
        body = request.get_json()
        item = self.repo.set(id, **body)
        if item is None:
            abort(404)
        return item._asdict()

    def delete(self, id):
        if self.repo.delete(id) is None:
            abort(404)
        return None, 204


class ListResource(Resource):
    repo = None
    method_decorators = [auth.login_required]

    def get(self):
        return [i._asdict() for i in self.repo.get_all()]

    def post(self):
        body = request.get_json()
        try:
            item = self.repo.add(**body)
        except TypeError as e:
            abort(400, e)
        else:
            return item._asdict()


class PersonResource(SingleResource):
    repo = person_repo


class PersonListResource(ListResource):
    repo = person_repo


class WordPairResource(SingleResource):
    repo = wordpair_repo


class WordPairListResource(ListResource):
    repo = wordpair_repo


class SiteResource(SingleResource):
    repo = site_repo


class SiteListResource(ListResource):
    repo = site_repo


class WordPairsForPersonResource(Resource):
    # Fixme
    method_decorators = [auth.login_required]

    def get(self, person_id):
        wordpairs = wordpair_repo.get_by_person_id(person_id)
        if wordpairs is None:
            abort(404)
        return [wordpair._asdict() for wordpair in wordpairs]


class TotalRankResource(Resource):
    # Fixme
    method_decorators = [auth.login_required]

    def get(self, site_id):
        ranks = rank_repo.get_total(site_id)
        if ranks is None:
            abort(404)
        return [rank._asdict() for rank in ranks]


class DailyRankResource(Resource):
    # Fixme
    method_decorators = [auth.login_required]

    def get(self):
        args = request.args
        person_id = int(args['person_id'])
        site_id = int(args['site_id'])
        start_date = parser.parse(args['start_date'])
        end_date = parser.parse(args['end_date'])
        ranks = rank_repo.get_daily(person_id, site_id, start_date, end_date)
        return [rank._asdict() for rank in ranks]




