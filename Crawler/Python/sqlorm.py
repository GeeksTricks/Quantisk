from sqlalchemy import create_engine
from sqlalchemy import Table, Column, Integer, String, MetaData, ForeignKey
import sqlite3

engine = create_engine('sqlite:////media/mpak/Mainframe/python/Test/Pages.db', echo=True)


metadata = MetaData()
pagess_table = Table('pages', metadata,
                    Column('id', Integer, primary_key=True),
                    Column('page', String),
                    )



metadata.create_all(engine)

""" TODO
1. Добавить ограничение VARCHAR
2. Преобразовать в класс для каждой таблицы