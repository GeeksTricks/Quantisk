from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


class UserModel(db.Model):

    __tablename__ = 'Users'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    login = db.Column(db.String(256), unique=True, nullable=False)
    pass_hash = db.Column(db.String(256), nullable=False)
    # role_id = db.Column(db.String(50), db.ForeignKey('Roles.id'))

    def __init__(self, login, pass_hash):
        self.login = login
        self.pass_hash = pass_hash

    def __repr__(self):
        return '<Login: {0} Password hash: {1}'.format(self.login, self.pass_hash)

# class RoleModel(db.Model):
#
#     __tablename__ = 'Roles'
#
#     id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
#     role = db.Column(db.String(50), nullable=False)


class PersonModel(db.Model):

    __tablename__ = 'Persons'
    
    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(2048), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Person {0}>'.format(self.name)


class WordpairModel(db.Model):

    __tablename__ = 'Wordpairs'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    keyword1 = db.Column(db.String(2048), nullable=False)
    keyword2 = db.Column(db.String(2048), nullable=False)
    distance = db.Column(db.Integer, nullable=False)
    person_id = db.Column(db.Integer, db.ForeignKey('Persons.id'), nullable=False)

    def __init__(self, keyword1, keyword2, distance, person_id):
        self.keyword1 = keyword1
        self.keyword2 = keyword2
        self.distance = distance
        self.person_id = person_id

    def __repr__(self):
        return '<PersonID: {0}, Keyword1: {1}, Keyword2: {2}, Distance: {3}>'.format(
            self.person_id,
            self.keyword1,
            self.keyword2,
            self.distance,
        )


class SiteModel(db.Model):

    __tablename__ = 'Sites'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(256), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Site {0}>'.format(self.name)


class PageModel(db.Model):

    __tablename__ = 'Pages'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    url = db.Column(db.String(2048), nullable=False)
    site_id = db.Column(db.Integer, db.ForeignKey('Sites.id'), nullable=False)
    found_date_time = db.Column(db.DateTime)
    last_scan_date = db.Column(db.DateTime)

    def __init__(self, url, site_id, found_date_time, last_scan_date):
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
    page_id = db.Column(db.Integer, db.ForeignKey('Pages.id'), primary_key=True, nullable=False)
    person_id = db.Column(db.Integer, db.ForeignKey('Persons.id'), primary_key=True, nullable=False)

    def __init__(self, rank, page_id, person_id):
        self.rank = rank
        self.page_id = page_id
        self.person_id = person_id

    def __repr__(self):
        return '<PageID: {0}, PersonID: {1}, Rank: {2}>'.format(
            self.page_id,
            self.person_id,
            self.rank,
        )