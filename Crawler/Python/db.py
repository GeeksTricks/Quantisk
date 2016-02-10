from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, DateTime, ForeignKey

Base = declarative_base()


# Создаем класс для каждой таблицы
class Persons(Base):
    __tablename__ = 'persons'

    id = Column(Integer, primary_key=True, unique=True, nullable=False)
    name = Column(String(2048))

    def __repr__(self):
        return "<Person '{}'>".format(self.name)

    def __init__(self, name):
        self.name = name


class Wordpairs(Base):
    __tablename__ = 'wordpairs'

    id = Column(Integer, primary_key=True, unique=True, nullable=False)
    keyword_1 = Column(String(2048))
    keyword_2 = Column(String(2048))
    distance = Column(Integer)
    person_id = Column(ForeignKey('persons.id'))

    def __repr__(self):
        return "<KeyWord1 '{}', KeyWord2 '{}', Distance '{}', PersonID '{}'>" \
            .format(self.keyword_1, self.keyword_2, self.distance,
                    self.person_id)

    def __init__(self, keyword_1, keyword_2, distance, person_id):
        self.keyword_1 = keyword_1
        self.keyword_2 = keyword_2
        self.distance = distance
        self.person_id = person_id


class Sites(Base):
    __tablename__ = 'sites'

    id = Column(Integer, primary_key=True, unique=True, nullable=False)
    name = Column(String(256))

    def __repr__(self):
        return "<Site '{}'>".format(self.name)

    def __init__(self, name):
        self.name = name


class Pages(Base):
    __tablename__ = 'pages'

    id = Column(Integer, primary_key=True, unique=True, nullable=False)
    url = Column(String(2048))
    site_id = Column(ForeignKey('sites.id'))
    found_date_time = Column(DateTime)
    last_scan_date = Column(DateTime, default=None)

    def __repr__(self):
        return "<Url '{}', SiteID '{}', FounDateTime '{}', LastScanDate '{}'>"\
            .format(self.url, self.site_id, self.found_date_time,
                    self.last_scan_date)

    def __init__(self, url, site_id, found_date_time):
        self.url = url
        self.site_id = site_id
        self.found_date_time = found_date_time
        # self.last_scan_date = last_scan_date   , last_scan_date


class PersonsPageRank(Base):
    __tablename__ = 'PersonsPageRank'

    id = Column(Integer, primary_key=True, unique=True, nullable=False)
    rank = Column(Integer)
    page_id = Column(ForeignKey('pages.id'))
    person_id = Column(ForeignKey('persons.id'))

    def __repr__(self):
        return "<Rank '{}', PageID '{}', PersonID '{}'>" \
            .format(self.rank, self.page_id, self.person_id)

    def __init__(self, rank, page_id, person_id):
        self.rank = rank
        self.page_id = page_id
        self.person_id = person_id
