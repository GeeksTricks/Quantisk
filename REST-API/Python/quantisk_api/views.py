#!flask/bin/python
from flask import request
from flask_restful import Resource
from collections import namedtuple
from .models import Persons, Wordpairs, Sites, db


Person = namedtuple('Person', ['id', 'name'])
WordPair = namedtuple('WordPair', ['id', 'keyword1', 'keyword2', 'distance', 'person_id'])
Site = namedtuple('Site', ['id', 'name'])

class Repo:

    def __init__(self):
        pass


class PersonRepo(Repo):

    def get_by_id(self, id):
        p = Persons.query.get(id)
        return Person(p.id, p.name)

    def get_all(self):
        p_list = Persons.query.all()
        return [Person(p.id, p.name) for p in p_list]

    def set(self, id, name):
        p = Persons.query.get(id)
        p.name = name
        db.session.commit()

    def add(self, name):
        """
        :param name:
        :return: id of created object
        """
        # p = Persons(name)
        # db.session.add(p)
        pass

    def delete(self, id):
        pass


class WordPairRepo(Repo):

    def get_by_id(self, id):
        return WordPair(id, 'Дикаприо', 'Оскар', 2, 5)

    def get_by_person_id(self, person_id):
        return [
            WordPair(1, 'Дикаприо', 'Оскар', 2, person_id),
            WordPair(2, 'Дикаприо', 'Оскар1', 2, person_id),
            WordPair(3, 'Дикаприо', 'Оскар2', 2, person_id),
        ]

    def get_all(self):
        return [
            WordPair(1, 'Дикаприо', 'Оскар', 2, 1),
            WordPair(2, 'Дикаприо', 'Оскар1', 2, 2),
            WordPair(3, 'Дикаприо', 'Оскар2', 2, 3),
        ]

    def add(self, keyword1, keyword2, distance, person_id):
        return 3

    def set(self, id, keyword1, keyword2, distance, person_id):
        pass

    def delete(self, id):
        pass


class SiteRepo(Repo):

    def get_by_id(self, id):
        return Site(id, 'placeholder.com')

    def get_all(self):
        return [Site(1, 'placeholder.ru'), Site(2, 'ohg.hg'), Site(3, 'Дикаприо.рф')]

    def set(self, id, name):
        pass

    def add(self, name):
        return 9

    def delete(self, id):
        pass


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


class PersonsListResource(Resource):

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
        wordpair1 = body['wordpair1']
        wordpair2 = body['wordpair2']
        distance = body['distance']
        person_id = body['person_id']
        wordpair_repo.set(id, wordpair1, wordpair2, distance, person_id)
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
        wordpair1 = body['wordpair1']
        wordpair2 = body['wordpair2']
        distance = body['distance']
        person_id = body['person_id']
        wordpair_repo.add(wordpair1, wordpair2, distance, person_id)
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




