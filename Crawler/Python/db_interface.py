#!/usr/bin/env python


from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

Base = declarative_base()


class Sites(Base):
	__tablename__ = 'sites'

	id = Column(Integer, primary_key=True)
	name = Column(String(2048))

class Persons(Base):
	__tablename__ = 'persons'

	id = Column(Integer, primary_key=True)
	name = Column(String(2048))

class Wordpairs(Base):
	__tablename__ = 'wordpairs'

	id = Column(Integer, primary_key=True)
	KeyWord1 = Column(String(2048))
	KeyWord2 = Column(String(2048))
	Distance = Column(Integer)
	PersonID = Column(ForeignKey('persons.id'))

class PersonPageRank(Base):
	__tablename__ = 'PersonPageRank'

	Rank = Column(Integer)
	PageID = Column(ForeignKey('pages.id'))
	PersonID = Column(ForeignKey('persons.id'))


class Pages(Base):
	__tablename__ = 'pages'

	id = Column(Integer, primary_key=True)
	Url = Column(String(2048))
	SiteID = Column(ForeignKey('sites.id'))
	FounDateTime = Column(datetime)
	LastScanDate = Column(datetime)
		

		
		


