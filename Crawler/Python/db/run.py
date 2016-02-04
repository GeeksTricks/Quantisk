#!/usr/bin/env python
from db import Persons, Wordpairs, Sites, Pages, PersonsPageRank
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import datetime

engine = create_engine('sqlite:///./GeeksDB.db', echo=True)
Base = declarative_base()
Session = sessionmaker(bind=engine)
session = Session()


def GetQueryForParse():
	result_set = []
	for row in session.query(Wordpairs.keyword_1,\
	Wordpairs.keyword_2, Wordpairs.distance, Wordpairs.person_id, Persons.name).all():
		result_set.append(row)
	return result_set

def GetSite():
	result_set = []
	for row in session.query(sites.id, sites.name).all():
		result_set.append(row)
	return result_set

def PushIntoPages(link, siteid):
	bullet = Pages(url = link, site_id = siteid, found_date_time = '{0:%Y-%m-%d %H:%M:%S}'.format(datetime.now()))
	session.add(bullet)

def GetUrlForParse():
	result_set = []
	for row in session.query(Pages.id, Pages.url).all():
		result_set.append(row)
	return result_set

def PushToPersonPageRank(rank, pageid, personid):
	pass


