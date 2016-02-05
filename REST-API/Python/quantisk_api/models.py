from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class Persons(db.Model):

    __tablename__ = 'Persons'
    
    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(50), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Person {0}>'.format(self.name)


class Wordpairs(db.Model):

    __tablename__ = 'Wordpairs'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    keyword1 = db.Column(db.String(50), nullable=False)
    keyword2 = db.Column(db.String(50), nullable=False)
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


class Sites(db.Model):

    __tablename__ = 'Sites'

    id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
    name = db.Column(db.String(50), nullable=False)

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return '<Site {0}>'.format(self.name)


# class Pages(db.Model):
#
#     __tablename__ = 'Pages'
#
#     id = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
#     url = db.Column(db.String(50), nullable=False)
#     site_id = db.Column(db.Integer, db.ForeignKey('Sites.id'), nullable=False)
#     found_date_time = db.Column(db.DateTime)
#     last_scan_date = db.Column(db.DateTime)
#
#     def __init__(self, url, site_id, found_date_time, last_scan_date):
#         self.url = url
#         self.site_id = site_id
#         self.found_date_time = found_date_time
#         self.last_scan_date = last_scan_date
#
#     def __repr__(self):
#         return '<SiteID: {0}, URL: {1}, Found: {2}, LastScanned: {3}>'.format(
#             self.site_id,
#             self.url,
#             self.found_date_time,
#             self.last_scan_date,
#         )

# class PersonPageRank(db.Model):
#
#     __tablename__ = 'PersonPageRank'
#
#     rank = db.Column(db.Integer, primary_key=True, unique=True, nullable=False)
#     page_id = db.Column(db.Integer, db.ForeignKey('Pages.id'), nullable=False)
#     person_id = db.Column(db.Integer, db.ForeignKey('Persons.id'), nullable=False)
#
#     def __init__(self, rank, page_id, person_id):
#         self.rank = rank
#         self.page_id = page_id
#         self.person_id = person_id
#
#     def __repr__(self):
#         return '<PageID: {0}, PersonID: {1}, Rank: {2}>'.format(
#             self.page_id,
#             self.person_id,
#             self.rank,
#         )
#
