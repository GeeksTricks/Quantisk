from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


class UserModel(db.Model):

    __tablename__ = 'Users'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    login = db.Column(db.String(255), unique=True, nullable=False)
    pass_hash = db.Column(db.String(255), nullable=False)

    def __init__(self, login, pass_hash):
        self.login = login
        self.pass_hash = pass_hash

    def __repr__(self):
        return '<ID: {0} Login: {1} Password hash: {2}'.format(self.id, self.login, self.pass_hash)


class PersonModel(db.Model):

    __tablename__ = 'Persons'
    
    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(255), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Person {0}>'.format(self.name)


class WordpairModel(db.Model):

    __tablename__ = 'Wordpairs'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    keyword_1 = db.Column(db.String(255), nullable=False)
    keyword_2 = db.Column(db.String(255), nullable=False)
    distance = db.Column(db.Integer, nullable=False)
    person_id = db.Column(db.Integer, db.ForeignKey('Persons.id', ondelete='CASCADE'), nullable=False)

    def __init__(self, keyword_1, keyword_2, distance, person_id):
        self.keyword_1 = keyword_1
        self.keyword_2 = keyword_2
        self.distance = distance
        self.person_id = person_id

    def __repr__(self):
        return '<PersonID: {0}, keyword_1: {1}, keyword_2: {2}, Distance: {3}>'.format(
            self.person_id,
            self.keyword_1,
            self.keyword_2,
            self.distance,
        )


class SiteModel(db.Model):

    __tablename__ = 'Sites'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(255), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Site: {0}>'.format(self.name)


class PageModel(db.Model):

    __tablename__ = 'Pages'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    url = db.Column(db.String(255), unique=True, nullable=False)
    site_id = db.Column(db.Integer, db.ForeignKey('Sites.id', ondelete='CASCADE'), nullable=False)
    found_date_time = db.Column(db.DateTime)
    last_scan_date = db.Column(db.DateTime)

    def __init__(self, url, site_id, found_date_time=None, last_scan_date=None):
        self.url = url
        self.site_id = site_id
        self.found_date_time = found_date_time
        self.last_scan_date = last_scan_date

    def __repr__(self):
        return '<ID: {}, SiteID: {}, URL: {}, Found: {}, LastScanned: {}>'.format(
            self.id,
            self.site_id,
            self.url,
            self.found_date_time,
            self.last_scan_date,
        )


class RankModel(db.Model):

    __tablename__ = 'PersonPageRank'

    rank = db.Column(db.Integer, nullable=False)
    page_id = db.Column(db.Integer, db.ForeignKey('Pages.id', ondelete='CASCADE'), primary_key=True, nullable=False)
    person_id = db.Column(db.Integer, db.ForeignKey('Persons.id', ondelete='CASCADE'), primary_key=True, nullable=False)

    def __init__(self, rank, page_id, person_id):
        self.rank = rank
        self.page_id = page_id
        self.person_id = person_id

    def __repr__(self):
        return '<PageID: {0}, PersonID: {1}, Rank: {2}>'.format(self.page_id, self.person_id, self.rank)