from .models import PersonModel, WordpairModel, SiteModel, PageModel, RankModel, UserModel, db
from sqlalchemy.sql import func
from collections import namedtuple
from werkzeug.security import generate_password_hash


Person = namedtuple('Person', ['id', 'name'])
WordPair = namedtuple('WordPair', ['id', 'keyword1', 'keyword2', 'distance', 'person_id'])
Site = namedtuple('Site', ['id', 'name'])
Page = namedtuple('Page', ['id', 'url', 'site_id', 'found_date_time', 'last_scan_date'])
TotalRank = namedtuple('Rank', ['rank', 'site_id', 'person_id'])
DailyRank = namedtuple('DailyRank', ['rank', 'day', 'site_id', 'person_id'])
User = namedtuple('User', ['id', 'login'])


class NonUniqueError(Exception):
    pass


class Repo:

    def __init__(self, model):
        self.model = model

    def model_to_dto(self, item):
        pass

    def get_by_id(self, id):
        item = self.model.query.get(id)
        if item is None:
            return None
        return self.model_to_dto(item)

    def get_all(self):
        item_list = self.model.query.all()
        if item_list is None:
            return None
        return [self.model_to_dto(item) for item in item_list]

    def delete(self, id):
        item = self.model.query.get(id)
        if item is None:
            return None
        db.session.delete(item)
        db.session.commit()
        return self.model_to_dto(item)


class UserRepo(Repo):

    def model_to_dto(self, user):
        return User(user.id, user.login)

    def get_by_login(self, login):
        query = self.model.query
        u = query.filter_by(login=login).first()
        if u is None:
            return None
        return self.model_to_dto(u)

    def get_pass_hash(self, id):
        query = self.model.query
        u = query.get(id)
        if u is None:
            return None
        return u.pass_hash

    def add(self, login, password):
        if self.get_by_login(login):
            raise NonUniqueError('This login is already taken')
        p_hash = generate_password_hash(password)
        u = self.model(login, p_hash)
        db.session.add(u)
        db.session.commit()
        return self.model_to_dto(u)


class PersonRepo(Repo):

    def model_to_dto(self, person):
        return Person(person.id, person.name)

    def set(self, id, name):
        p = self.model.query.get(id)
        if p is None:
            return None
        p.name = name
        db.session.commit()
        return self.model_to_dto(p)

    def add(self, name):
        p = self.model(name)
        db.session.add(p)
        db.session.commit()
        return self.model_to_dto(p)


class WordPairRepo(Repo):

    def model_to_dto(self, wp):
        return WordPair(wp.id, wp.keyword_1, wp.keyword_2, wp.distance, wp.person_id)

    def get_by_person_id(self, person_id):
        query = self.model.query
        query = query.filter_by(person_id=person_id)
        if query is None:
            return None
        wp_list = query.all()
        return [self.model_to_dto(wp) for wp in wp_list]

    def add(self, keyword1, keyword2, distance, person_id):
        wp = self.model(keyword1, keyword2, distance, person_id)
        db.session.add(wp)
        db.session.commit()
        return self.model_to_dto(wp)

    def set(self, id, keyword1, keyword2, distance):
        wp = self.model.query.get(id)
        if wp is None:
            return None
        wp.keyword_1 = keyword1
        wp.keyword_2 = keyword2
        wp.distance = distance
        db.session.commit()
        return self.model_to_dto(wp)


class SiteRepo(Repo):

    def model_to_dto(self, site):
        return Site(site.id, site.name)

    def set(self, id, name):
        s = self.model.query.get(id)
        if s is None:
            return None
        s.name = name
        db.session.commit()
        return Site(s.id, s.name)

    def add(self, name):
        s = self.model(name)
        db.session.add(s)
        db.session.commit()
        return Site(s.id, s.name)


class RankRepo:

    def get_total(self, site_id):
        s = SiteModel.query.get(site_id)
        if s is None:
            return None
        rank = func.sum(RankModel.rank).label('total_rank')
        query = db.session.query(rank, RankModel.person_id)
        query = query.join(PageModel).filter_by(site_id=site_id)
        query = query.group_by(RankModel.person_id)
        res = query.all()
        return [TotalRank(int(r.total_rank), site_id, r.person_id) for r in res]

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
        return [DailyRank(int(r.rank), r.day.isoformat(), site_id, person_id) for r in res]

    def add_rank(self, rank, page_id, person_id):
        r = RankModel(rank, page_id, person_id)
        db.session.add(r)
        db.session.commit()


class PageRepo(Repo):

    def model_to_dto(self, page):
        return Page(page.id, page.url, page.site_id, page.found_date_time, page.last_scan_date)

    def add(self, url, site_id, found_date_time, last_scan_date):
        p = PageModel(url, site_id, found_date_time, last_scan_date)
        db.session.add(p)
        db.session.commit()
        return self.model_to_dto(p)

rank_repo = RankRepo()
person_repo = PersonRepo(PersonModel)
wordpair_repo = WordPairRepo(WordpairModel)
site_repo = SiteRepo(SiteModel)
page_repo = PageRepo(PageModel)
user_repo = UserRepo(UserModel)
