from flask import request
from flask_restful import Resource, abort
from .repositories import person_repo, wordpair_repo, site_repo, rank_repo, user_repo
from .repositories import NonUniqueError
from .auth import requires_auth, g
import arrow
from arrow.parser import ParserError
from . import app


class SingleResource(Resource):
    repo = None
    method_decorators = [requires_auth]

    def get(self, id):
        item = self.repo.get_by_id(id)
        if item is None:
            abort(404)
        return item._asdict()

    def put(self, id):
        body = request.get_json()
        app.logger.info('PUT request at %s by %s. Body: %s', request.path, g.user, body)
        item = self.repo.set(id, **body)
        if item is None:
            abort(404)
        app.logger.info('Success. Returned: %s', item._asdict())
        return item._asdict()

    def delete(self, id):
        app.logger.info('DELETE request at %s by %s', request.path, g.user)
        if self.repo.delete(id) is None:
            app.logger.error('404: Not found.')
            abort(404)
        app.logger.info('Success.')
        return None, 204


class ListResource(Resource):
    repo = None
    method_decorators = [requires_auth]

    def get(self):
        return [i._asdict() for i in self.repo.get_all()]

    def post(self):
        body = request.get_json()
        app.logger.info('POST request at %s by %s. Body: %s', request.path, g.user, body)
        try:
            item = self.repo.add(**body)
        except TypeError:
            abort(400)
        except NonUniqueError:
            abort(409)
        else:
            app.logger.info('Success. Returned: %s', item._asdict())
            return item._asdict()

class UserResource(SingleResource):
    repo = user_repo


class UserListResource(ListResource):
    repo = user_repo
    method_decorators = []

    @requires_auth
    def get(self):
        return super().get()


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


class WordPairsForPersonListResource(Resource):
    method_decorators = [requires_auth]

    def get(self, person_id):
        wordpairs = wordpair_repo.get_by_person_id(person_id)
        if wordpairs is None:
            abort(404)
        return [wordpair._asdict() for wordpair in wordpairs]

    def post(self, person_id):
        body = request.get_json()
        app.logger.info('POST request at %s by %s. Body: %s', request.path, g.user, body)
        try:
            wordpair = wordpair_repo.add(person_id=person_id, **body)
        except TypeError as e:
            abort(400)
        except NonUniqueError as e:
            abort(409)
        else:
            app.logger.info('Success. Returned: %s', wordpair._asdict())
            return wordpair._asdict()

class TotalRankResource(Resource):
    method_decorators = [requires_auth]

    def get(self, site_id):
        ranks = rank_repo.get_total(site_id)
        if ranks is None:
            abort(404)
        return [rank._asdict() for rank in ranks]


class DailyRankResource(Resource):
    method_decorators = [requires_auth]

    def get(self):
        args = request.args
        person_id = int(args['person_id'])
        site_id = int(args['site_id'])
        try:
            start_date = arrow.get(args['start_date']).to('utc').datetime
            end_date = arrow.get(args['end_date']).to('utc').datetime
            ranks = rank_repo.get_daily(person_id, site_id, start_date, end_date)
            return [rank._asdict() for rank in ranks]
        except ParserError:
            abort(400)
