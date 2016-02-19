from flask import request, abort, g
from flask_restful import Resource
from .repositories import person_repo, wordpair_repo, site_repo, rank_repo, user_repo
from .repositories import NonUniqueError
from .auth import requires_auth, requires_roles, unauthorized, ADMIN, USER
import arrow
from arrow.parser import ParserError


class SingleResource(Resource):
    repo = None
    method_decorators = [requires_auth]

    @requires_roles(USER, ADMIN)
    def get(self, id):
        item = self.repo.get_by_id(id)
        if item is None:
            abort(404)
        return item._asdict()

    @requires_roles(ADMIN)
    def put(self, id):
        item = self.repo.get_by_id(id)
        if hasattr(item, 'user_id') and not g.user.id == item.user_id:
            return unauthorized('You can only edit your own stuff')
        body = request.get_json()
        item = self.repo.set(id, **body)
        if item is None:
            abort(404)
        return item._asdict()

    @requires_roles(ADMIN)
    def delete(self, id):
        item = self.repo.get_by_id(id)
        if hasattr(item, 'user_id') and not g.user.id == item.user_id:
            return unauthorized('You can only edit your own stuff')
        if self.repo.delete(id) is None:
            abort(404)
        return None, 204


class ListResource(Resource):
    repo = None
    method_decorators = [requires_auth]

    @requires_roles(USER, ADMIN)
    def get(self):
        return [i._asdict() for i in self.repo.get_all()]

    @requires_roles(ADMIN)
    def post(self):
        body = request.get_json()
        try:
            item = self.repo.add(**body)
        except TypeError as e:
            abort(400, e)
        except NonUniqueError as e:
            abort(409, e)
        else:
            return item._asdict()

class UserResource(SingleResource):
    repo = user_repo


class UserListResource(ListResource):
    repo = user_repo


class PersonResource(SingleResource):
    repo = person_repo

class PersonListResource(ListResource):
    repo = person_repo

    @requires_roles(ADMIN)
    def post(self):
        body = request.get_json()
        user_id = g.user.id
        try:
            item = self.repo.add(user_id=user_id, **body)
        except TypeError as e:
            abort(400, e)
        except NonUniqueError as e:
            abort(409, e)
        else:
            return item._asdict()

class WordPairResource(SingleResource):
    repo = wordpair_repo


class WordPairListResource(ListResource):
    #Fixme
    repo = wordpair_repo

    @requires_roles(ADMIN)
    def post(self):
        body = request.get_json()
        owner_id = person_repo.get_owner_id(body.person_id)
        if g.user.id == owner_id:
            try:
                item = self.repo.add(**body)
            except TypeError as e:
                abort(400, e)
            except NonUniqueError as e:
                abort(409, e)
            else:
                return item._asdict()
        return unauthorized('You can only edit your own stuff')

class SiteResource(SingleResource):
    repo = site_repo


class SiteListResource(ListResource):
    repo = site_repo

    @requires_roles(ADMIN)
    def post(self):
        body = request.get_json()
        user_id = g.user.id
        try:
            item = self.repo.add(user_id=user_id, **body)
        except TypeError as e:
            abort(400, e)
        except NonUniqueError as e:
            abort(409, e)
        else:
            return item._asdict()


class WordPairsForPersonResource(Resource):
    # Fixme
    method_decorators = [requires_auth]

    @requires_roles(USER, ADMIN)
    def get(self, person_id):
        wordpairs = wordpair_repo.get_by_person_id(person_id)
        if wordpairs is None:
            abort(404)
        return [wordpair._asdict() for wordpair in wordpairs]


class TotalRankResource(Resource):
    # Fixme
    method_decorators = [requires_auth]

    @requires_roles(USER, ADMIN)
    def get(self, site_id):
        ranks = rank_repo.get_total(site_id)
        if ranks is None:
            abort(404)
        return [rank._asdict() for rank in ranks]


class DailyRankResource(Resource):
    # Fixme
    method_decorators = [requires_auth]

    @requires_roles(USER, ADMIN)
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
