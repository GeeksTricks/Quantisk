from .models import PersonModel, WordpairModel, SiteModel, PageModel, RankModel, UserModel, db
from sqlalchemy.sql import func
from collections import namedtuple
from werkzeug.security import generate_password_hash


Person = namedtuple('Person', ['id', 'name', 'user_id'])
WordPair = namedtuple('WordPair', ['id', 'keyword1', 'keyword2', 'distance', 'person_id', 'user_id'])
Site = namedtuple('Site', ['id', 'name', 'user_id'])
Page = namedtuple('Page', ['id', 'url', 'site_id', 'found_date_time', 'last_scan_date'])
TotalRank = namedtuple('Rank', ['rank', 'site_id', 'person_id'])
DailyRank = namedtuple('DailyRank', ['rank', 'day', 'site_id', 'person_id'])
User = namedtuple('User', ['id', 'login', 'pass_hash', 'role'])


class NonUniqueError(Exception):
    pass

class Repo:

    def __init__(self):
        pass


class UserRepo(Repo):

    def get_by_id(self, id):
        u = UserModel.query.get(id)
        if u is None:
            return None
        return User(u.id, u.login, u.pass_hash, u.role)

    def get_by_login(self, login):
        query = UserModel.query
        u = query.filter_by(login=login).first()
        if u is None:
            return None
        return User(u.id, u.login, u.pass_hash, u.role)

    def get_user_role(self, id):
        pass

    def get_all(self):
        u_list = UserModel.query.all()
        if u_list is None:
            return None
        return [User(u.id, u.login, u.pass_hash, u.role) for u in u_list]

    def add(self, login, password, role):
        if self.get_by_login(login):
            raise NonUniqueError('This login is already taken')
        p_hash = generate_password_hash(password)
        u = UserModel(login, p_hash, role)
        db.session.add(u)
        db.session.commit()
        return User(u.id, u.login, u.pass_hash, u.role)

    def delete(self, id):
        u = UserModel.query.get(id)
        if u is None:
            return None

        db.session.delete(u)
        db.session.commit()
        return User(u.id, u.name)


class PersonRepo(Repo):

    def get_owner_id(self, id):
        p = UserModel.query.get(id)
        user_id = p.user_id
        if p is None or user_id is None:
            return None
        return user_id

    def get_by_id(self, id):
        p = PersonModel.query.get(id)
        if p is None:
            return None
        return Person(p.id, p.name, p.user_id)

    def get_all(self):
        p_list = PersonModel.query.all()
        if p_list is None:
            return None
        return [Person(p.id, p.name, p.user_id) for p in p_list]

    def set(self, id, name):
        p = PersonModel.query.get(id)
        if p is None:
            return None
        p.name = name
        db.session.commit()
        return Person(p.id, p.name, p.user_id)

    def add(self, name, user_id):
        p = PersonModel(name, user_id)
        db.session.add(p)
        db.session.commit()
        return Person(p.id, p.name, p.user_id)

    def delete(self, id):
        p = PersonModel.query.get(id)
        if p is None:
            return None
        db.session.delete(p)
        db.session.commit()
        return Person(p.id, p.name, p.user_id)


class WordPairRepo(Repo):

    def get_owner_id(self, id):
        user_id = db.session.query(PersonModel.user_id).join(WordpairModel).filter_by(id=id).scalar()
        return user_id

    def get_by_id(self, id):
        wp = WordpairModel.query.get(id)
        user_id = self.get_owner_id(id)
        if wp is None or user_id is None:
            return None
        return WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id, user_id)

    def get_by_person_id(self, person_id):
        query = WordpairModel.query
        query = query.filter_by(person_id=person_id)
        if query is None:
            return None
        wp_list = query.all()
        return [WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id, self.get_owner_id(wp.id)) for wp in wp_list]

    def get_all(self):
        wp_list = WordpairModel.query.all()
        if wp_list is None:
            return None
        return [WordPair(wp.id,
                         wp.keyword1,
                         wp.keyword2,
                         wp.distance,
                         wp.person_id,
                         self.get_owner_id(wp.id)) for wp in wp_list]

    def add(self, keyword1, keyword2, distance, person_id):
        wp = WordpairModel(keyword1, keyword2, distance, person_id)
        db.session.add(wp)
        db.session.commit()
        return WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id)

    def set(self, id, keyword1, keyword2, distance, person_id):
        wp = WordpairModel.query.get(id)
        if wp is None:
            return None
        wp.keyword1 = keyword1
        wp.keyword2 = keyword2
        wp.distance = distance
        wp.person_id = person_id
        db.session.commit()
        return WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id)

    def delete(self, id):
        wp = WordpairModel.query.get(id)
        if wp is None:
            return None
        db.session.delete(wp)
        db.session.commit()
        return WordPair(wp.id, wp.keyword1, wp.keyword2, wp.distance, wp.person_id)


class SiteRepo(Repo):

    def get_by_id(self, id):
        s = SiteModel.query.get(id)
        if s is None:
            return None
        return Site(s.id, s.name, s.user_id)

    def get_all(self):
        s_list = SiteModel.query.all()
        if s_list is None:
            return None
        return [Site(s.id, s.name, s.user_id) for s in s_list]

    def set(self, id, name):
        s = SiteModel.query.get(id)
        if s is None:
            return None
        s.name = name
        db.session.commit()
        return Site(s.id, s.name, s.user_id)

    def add(self, name, user_id):
        s = SiteModel(name, user_id)
        db.session.add(s)
        db.session.commit()
        return Site(s.id, s.name, s.user_id)

    def delete(self, id):
        s = SiteModel.query.get(id)
        if s is None:
            return None
        db.session.delete(s)
        db.session.commit()
        return Site(s.id, s.name, s.user_id)


class RankRepo(Repo):

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

    def add(self, url, site_id, found_date_time, last_scan_date):
        p = PageModel(url, site_id, found_date_time, last_scan_date)
        db.session.add(p)
        db.session.commit()
        return Page(p.id, p.url, p.site_id, p.found_date_time, p.last_scan_date)

person_repo = PersonRepo()
wordpair_repo = WordPairRepo()
site_repo = SiteRepo()
rank_repo = RankRepo()
page_repo = PageRepo()
user_repo = UserRepo()
