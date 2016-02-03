from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy import create_engine

engine = create_engine('sqlite:///./GeeksDB.db', echo=True)
Base = declarative_base()


class Persons(Base):

    __tablename__ = 'Persons'

    id = Column(Integer, primary_key=True)
    name = Column(String(2048))

    def __repr__(self):
        return "<Person '{}'>".format(self.name)


class Wordpairs(Base):

    __tablename__ = 'Wordpairs'

    id = Column(Integer, primary_key=True)
    keyword_1 = Column(String(2048))
    keyword_2 = Column(String(2048))
    distance = Column(Integer)
    person_id = Persons.id

    def __repr__(self):
        pass

class Sites(Base):

    __tablename__ = 'Sites'

    id = Column(Integer, primary_key=True)
    name = Column(String(256))

    def __repr__(self):
        pass

class Pages(Base):

    __tablename__ = 'Pages'

    id = Column(Integer, primary_key=True)
    url = Column(String(2048))
    site_id = Sites.id
    found_date_time = Column(DateTime)
    last_scan_date = Column(DateTime)

    def __repr__(self):
        pass



class PersonsPageRank(Base):

    __tablename__ = 'PersonsPageRank'

    rank = Column(Integer, primary_key=True)
    page_id = Pages.id
    person_id = Persons.id

    def __repr__(self):
        pass


Base.metadata.create_all(engine)
