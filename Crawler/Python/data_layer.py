#!/usr/bin/python3

from db import Sites
from db import Wordpairs
from db import Pages
from db import Persons
from db import PersonsPageRank
from sqlalchemy import create_engine, or_
from sqlalchemy.orm import sessionmaker
from datetime import datetime, timedelta


class DataLayer():
    def __init__(self):
        self.engine = create_engine(
            'mysql+pymysql://Crawler:probation2016@178.218.115.116:64004'
            '/GeeksTricks2?charset=utf8',
            echo=True)
        self.Session = sessionmaker(bind=self.engine)
        self.session = self.Session()

    def get_site(self):
        # получаем весь список сайтов
        result_set = self.session.query(Sites.id, Sites.name).all()
        return result_set

    def get_persons(self):
        # получаем весь список Личностей
        result_set = self.session.query(Persons.id, Persons.name).all()
        return result_set

    def get_not_scan_urls(self):
        # получаем весь список ссылок с пустой датой сканирвоания
        print("Начинаем проверять скан дэйт")
        result_set = self.session.query(Pages.id, Pages.url).filter(
            or_(Pages.last_scan_date == None, Pages.last_scan_date + timedelta(days=1) < datetime.utcnow())).all()
        print(str(len(result_set)) + ' столько ссылок выбралось из базы')
        return result_set

    def get_query_for_parse(self):
        # получаем весь список ссылок параметров для парсера
        result_set = self.session.query(Wordpairs.keyword_1,
                                        Wordpairs.keyword_2,
                                        Wordpairs.distance,
                                        Wordpairs.person_id).all()
        return result_set

    def add_page_to_table(self, url_list, id_site):
        clear_urls = set(url_list)
        urls_from_base = []
        for url in self.session.query(Pages.url).all():
            urls_from_base.append(*url)
        for url in clear_urls:
            if url in urls_from_base:
                print(url + " уже есть в базе")
                pass
            else:
                print(url + " добавлено в сеесию")
                bullet = Pages(url=url, site_id=id_site,
                               found_date_time="{0:%Y-%m-%d %H:%M:%S}".format(
                                   datetime.utcnow()))
                self.session.add(bullet)
        self.session.commit()

    def add_rank_to_table(self, count, id_url, id_persons):
        # проверяем наличие результата
        if count:
            # проверяем наличие данных по этому запросу
            if self.session.query(PersonsPageRank).filter(
                            PersonsPageRank.page_id == id_url).filter(
                            PersonsPageRank.person_id == id_persons).all():
                # если уже есть данные, то обновляем поле rank(пока без
                # проверки на равенство с уже существующим)
                pass
            else:
                # если нету записи то добавляем
                bullet = PersonsPageRank(rank=count, page_id=id_url,
                                         person_id=id_persons)
                self.session.add(bullet)
                self.session.commit()
        # если нет результат то идем дальше
        else:
            pass

    def set_last_scan_date(self, id_url):
        scan_date = '{0:%Y-%m-%d %H:%M:%S}'.format(datetime.utcnow())
        self.session.query(Pages).filter_by(id=id_url).update(
            {'last_scan_date': scan_date})
        self.session.commit()
