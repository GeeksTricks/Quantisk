#!/usr/bin/env python

from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///./GeeksDB.db', echo=True)
Base = declarative_base()

class Sites(Base):
	__tablename__ = 'sites'

	id = Column(Integer, primary_key=True)
	name = Column(String)

	def __repr__(self):
		return "<Site '{}'>".format(self.name)


class Persons(Base):
	__tablename__ = 'persons'

	id = Column(Integer, primary_key=True)
	name = Column(String(2048))

	def __repr__(self):
		return "<Person '{}'>".format(self.name)

class Wordpairs(Base):
	__tablename__ = 'wordpairs'

	id = Column(Integer, primary_key=True)
	KeyWord1 = Column(String(2048))
	KeyWord2 = Column(String(2048))
	Distance = Column(Integer)
	PersonID = Column(ForeignKey('persons.id'))

	def __repr__(self):
		return "<KeyWord1 '{}', KeyWord2 '{}', Distance '{}', PersonID '{}'>"\
				.format(self.KeyWord1, self.KeyWord2, self.Distance, self.PersonID)

class PersonPageRank(Base):
	__tablename__ = 'PersonPageRank'

	Rank = Column(Integer)
	PageID = Column(ForeignKey('pages.id'))
	PersonID = Column(ForeignKey('persons.id'))

	def __repr__(self):
		return "<Rank '{}', PageID '{}', PersonID '{}'>"\
				.format(self.Rank, self.PageID, self.PersonID)


class Pages(Base):
	__tablename__ = 'pages'

	id = Column(Integer, primary_key=True)
	Url = Column(String(2048))
	SiteID = Column(ForeignKey('sites.id'))
	FounDateTime = Column(datetime)
	LastScanDate = Column(datetime)

	def __repr__(self):
		return "<Url '{}', SiteID '{}', FounDateTime '{}', LastScanDate '{}'>"\
				.format(self.Url, self.SiteID, self.FounDateTime, self.LastScanDate)
		

Base.metadata.create_all(engine)
Session = sessionmaker(bind=engine)
session.commit()


