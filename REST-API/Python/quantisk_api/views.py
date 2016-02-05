#!flask/bin/python
from flask import request
from flask_restful import Resource
from collections import namedtuple
from .models import PersonModel, WordpairModel, SiteModel, db


Person = namedtuple('Person', ['id', 'name'])
WordPair = namedtuple('WordPair', ['id', 'keyword1', 'keyword2', 'distance', 'person_id'])
Site = namedtuple('Site', ['id', 'name'])

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
        wp_list = WordpairModel.query.filter_by(person_id=person_id)
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


person_repo = PersonRepo()
wordpair_repo = WordPairRepo()
site_repo = SiteRepo()


class PersonResource(Resource):

    def get(self, id):
        person = person_repo.get_by_id(id)
        return vars(person)

    def put(self, id):
        body = request.get_json()
        name = body['name']
        person_repo.set(id, name)
        person = person_repo.get_by_id(id)
        return vars(person)

    def delete(self, id):
        person_repo.delete(id)
        return 204


class PersonListResource(Resource):

    def get(self):
        persons = person_repo.get_all()
        return [vars(person) for person in persons]

    def post(self):
        body = request.get_json()
        name = body['name']
        id = person_repo.add(name)
        person = person_repo.get_by_id(id)
        return vars(person)


class WordPairResource(Resource):

    def get(self, id):
        wordpair = wordpair_repo.get_by_id(id)
        return vars(wordpair)

    def put(self, id):
        body = request.get_json()
        keyword1 = body['keyword1']
        keyword2 = body['keyword2']
        distance = body['distance']
        person_id = body['person_id']
        wordpair_repo.set(id, keyword1, keyword2, distance, person_id)
        wordpair = wordpair_repo.get_by_id(id)
        return vars(wordpair)

    def delete(self, id):
        wordpair_repo.delete(id)
        return 204

class WordPairListResource(Resource):

    def get(self):
        wordpairs = wordpair_repo.get_all()
        return [vars(wordpair) for wordpair in wordpairs]

    def post(self):
        body = request.get_json()
        keyword1 = body['keyword1']
        keyword2 = body['keyword2']
        distance = body['distance']
        person_id = body['person_id']
        id = wordpair_repo.add(keyword1, keyword2, distance, person_id)
        wordpair = wordpair_repo.get_by_id(id)
        return vars(wordpair)

class SiteResource(Resource):

    def get(self, id):
        site = site_repo.get_by_id(id)
        return vars(site)

    def put(self, id):
        body = request.get_json()
        name = body['name']
        site_repo.set(id, name)
        site = site_repo.get_by_id(id)
        return vars(site)

    def delete(self, id):
        site_repo.delete(id)
        return 204

class SiteListResource(Resource):

    def get(self):
        sites = site_repo.get_all()
        return [vars(site) for site in sites]

    def post(self):
        body = request.get_json()
        name = body['name']
        id = site_repo.add(name)
        site = site_repo.get_by_id(id)
        return vars(site)




