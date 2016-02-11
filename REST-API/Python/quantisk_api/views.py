#!flask/bin/python
from flask import request
from flask_restful import Resource
from collections import namedtuple
from .models import PersonModel, WordpairModel, SiteModel, PageModel, RankModel, db
from sqlalchemy.sql import func
from dateutil import parser

Person = namedtuple('Person', ['id', 'name'])
WordPair = namedtuple('WordPair', ['id', 'keyword1', 'keyword2', 'distance', 'person_id'])
Site = namedtuple('Site', ['id', 'name'])
Page = namedtuple('Page', ['id', 'url', 'site_id', 'found_date_time', 'last_scan_date'])
TotalRank = namedtuple('Rank', ['rank', 'site_id', 'person_id'])
DailyRank = namedtuple('DailyRank', ['rank', 'day', 'site_id', 'person_id'])


class Repo:

    def __init__(self):
        pass


class PersonRepo(Repo):

    def get_by_id(self, id):
        p = PersonModel.query.get(id)
        return Person(p.id, p.name)

    def get_all(self):
        p_list = PersonModel.query.all()
        return [Person(p.id, p.name) for p in p_list]

    def set(self, id, name):
        p = PersonModel.query.get(id)
        p.name = name
        db.session.commit()

    def add(self, name):
        p = PersonModel(name)
        db.session.add(p)
        db.session.commit()
        return p.id

    def delete(self, id):
        PersonModel.query.filter_by(id=id).delete()
        db.session.commit()


class WordPairRepo(Repo):

    def get_by_id(self, id):
        wp = WordpairModel.query.get(id)
        return WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id)

    def get_by_person_id(self, person_id):
        query = WordpairModel.query
        query = query.filter_by(person_id=person_id)
        wp_list = query.all()
        return [WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, person_id) for wp in wp_list]

    def get_all(self):
        wp_list = WordpairModel.query.all()
        return [WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id) for wp in wp_list]

    def add(self, keyword1, keyword2, distance, person_id):
        wp = WordpairModel(keyword1, keyword2, distance, person_id)
        db.session.add(wp)
        db.session.commit()
        return wp.id

    def set(self, id, keyword1, keyword2, distance, person_id):
        wp = WordpairModel.query.get(id)
        wp.keyword1 = keyword1
        wp.keyword2 = keyword2
        wp.distance = distance
        wp.person_id = person_id
        db.session.commit()

    def delete(self, id):
        WordpairModel.query.filter_by(id=id).delete()
        db.session.commit()


class SiteRepo(Repo):

    def get_by_id(self, id):
        s = SiteModel.query.get(id)
        return Site(s.id, s.name)

    def get_all(self):
        s_list = SiteModel.query.all()
        return [Site(s.id, s.name) for s in s_list]

    def set(self, id, name):
        s = SiteModel.query.get(id)
        s.name = name
        db.session.commit()

    def add(self, name):
        s = SiteModel(name)
        db.session.add(s)
        db.session.commit()
        return s.id

    def delete(self, id):
        SiteModel.query.filter_by(id=id).delete()
        db.session.commit()


class RankRepo(Repo):

    def get_total(self, site_id):
        rank = func.sum(RankModel.rank).label('total_rank')
        query = db.session.query(rank, RankModel.person_id)
        query = query.join(PageModel).filter_by(site_id=site_id)
        query = query.group_by(RankModel.person_id)
        res = query.all()
        return [TotalRank(r.total_rank, r.person_id, site_id) for r in res]


    def get_daily(self, person_id, site_id, start_date, end_date):
        rank = func.sum(RankModel.rank).label('rank')
        day = func.date(PageModel.last_scan_date).label('day')
        query = db.session.query(rank, day)
        query = query.filter_by(person_id=person_id)
        query = query.join(PageModel).filter_by(site_id=site_id)
        query = query.group_by(day)
        query = query.filter(day >= func.date(start_date))
        query = query.filter(day <= func.date(end_date))
        res = query.all()
        return [DailyRank(r.rank, r.day, site_id, person_id) for r in res]


person_repo = PersonRepo()
wordpair_repo = WordPairRepo()
site_repo = SiteRepo()
rank_repo = RankRepo()


class PersonResource(Resource):

    def get(self, id):
        person = person_repo.get_by_id(id)
        return person._asdict()

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

    def get(self):
        persons = person_repo.get_all()
        return [person._asdict() for person in persons]

    def post(self):
        body = request.get_json()
        name = body['name']
        id = person_repo.add(name)
        person = person_repo.get_by_id(id)
        return person._asdict()


class WordPairResource(Resource):

    def get(self, id):
        wordpair = wordpair_repo.get_by_id(id)
        return wordpair._asdict()

    def put(self, id):
        body = request.get_json()
        keyword1 = body['keyword1']
        keyword2 = body['keyword2']
        distance = body['distance']
        person_id = body['person_id']
        wordpair_repo.set(id, keyword1, keyword2, distance, person_id)
        wordpair = wordpair_repo.get_by_id(id)
        return wordpair._asdict()

    def delete(self, id):
        wordpair_repo.delete(id)
        return 204


class WordPairListResource(Resource):

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

    def get(self, person_id):
        wordpairs = wordpair_repo.get_by_person_id(person_id)
        return [wordpair._asdict() for wordpair in wordpairs]


class SiteResource(Resource):

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

    def get(self, site_id):
        ranks = rank_repo.get_total(site_id)
        return [rank._asdict() for rank in ranks]


class DailyRankResource(Resource):

    def get(self):
        body = request.args
        person_id = int(body['person_id'])
        site_id = int(body['site_id'])
        start_date = parser.parse(body['start_date'])
        end_date = parser.parse(body['end_date'])
        ranks = rank_repo.get_daily(person_id, site_id, start_date, end_date)
        return [rank._asdict() for rank in ranks]



