from flask import request, abort
from flask_restful import Resource
from dateutil import parser
from .repositories import person_repo, wordpair_repo, site_repo, rank_repo
from .auth import auth


class PersonResource(Resource):

    method_decorators = [auth.login_required]

    def get(self, id):
        p = person_repo.get_by_id(id)
        if p is None:
            abort(404)
        return p._asdict()

    def put(self, id):
        body = request.get_json()
        name = body['name']
        person_repo.set(id, name)
        person = person_repo.get_by_id(id)
        return person._asdict()

    def delete(self, id):
        person_repo.delete(id)
        return 204


class PersonListResource(Resource):

    method_decorators = [auth.login_required]

    def get(self):
        persons = person_repo.get_all()
        if persons is None:
            abort(404)
        return [person._asdict() for person in persons]

    def post(self):
        body = request.get_json()
        name = body['name']
        id = person_repo.add(name)
        person = person_repo.get_by_id(id)
        return person._asdict()


class WordPairResource(Resource):

    method_decorators = [auth.login_required]

    def get(self, id):
        wp = wordpair_repo.get_by_id(id)
        if wp is None:
            abort(404)
        return wp._asdict()

    def put(self, id):
        body = request.get_json()
        keyword1 = body['keyword1']
        keyword2 = body['keyword2']
        distance = body['distance']
        person_id = body['person_id']
        wordpair_repo.set(id, keyword1, keyword2, distance, person_id)
        wp = wordpair_repo.get_by_id(id)
        if wp is None:
            abort(404)
        return wp._asdict()

    def delete(self, id):
        wordpair_repo.delete(id)
        return 204


class WordPairListResource(Resource):

    method_decorators = [auth.login_required]

    def get(self):
        wordpairs = wordpair_repo.get_all()
        return [wordpair._asdict() for wordpair in wordpairs]

    def post(self):
        body = request.get_json()
        keyword1 = body['keyword1']
        keyword2 = body['keyword2']
        distance = body['distance']
        person_id = body['person_id']
        id = wordpair_repo.add(keyword1, keyword2, distance, person_id)
        wordpair = wordpair_repo.get_by_id(id)
        return wordpair._asdict()


class WordPairsForPersonResource(Resource):

    method_decorators = [auth.login_required]

    def get(self, person_id):
        wordpairs = wordpair_repo.get_by_person_id(person_id)
        return [wordpair._asdict() for wordpair in wordpairs]


class SiteResource(Resource):

    method_decorators = [auth.login_required]

    def get(self, id):
        site = site_repo.get_by_id(id)
        return site._asdict()

    def put(self, id):
        body = request.get_json()
        name = body['name']
        site_repo.set(id, name)
        site = site_repo.get_by_id(id)
        return site._asdict()

    def delete(self, id):
        site_repo.delete(id)
        return 204


class SiteListResource(Resource):

    method_decorators = [auth.login_required]

    def get(self):
        sites = site_repo.get_all()
        return [site._asdict() for site in sites]

    def post(self):
        body = request.get_json()
        name = body['name']
        id = site_repo.add(name)
        site = site_repo.get_by_id(id)
        return site._asdict()


class TotalRankResource(Resource):

    method_decorators = [auth.login_required]

    def get(self, site_id):
        ranks = rank_repo.get_total(site_id)
        return [rank._asdict() for rank in ranks]


class DailyRankResource(Resource):

    method_decorators = [auth.login_required]

    def get(self):
        args = request.args
        person_id = int(args['person_id'])
        site_id = int(args['site_id'])
        start_date = parser.parse(args['start_date'])
        end_date = parser.parse(args['end_date'])
        ranks = rank_repo.get_daily(person_id, site_id, start_date, end_date)
        return [rank._asdict() for rank in ranks]




