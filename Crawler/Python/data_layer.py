from db import Sites
from db import Wordpairs
from db import Pages
from db import Persons
from db import PersonsPageRank
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import datetime


class DataLayer():
    def __init__(self):
        self.engine = create_engine(
            'mysql+pymysql://Crawler:probation2016@178.218.115.116:64004'
            '/GeeksTricks?charset=utf8',
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
        result_set = self.session.query(Pages.id, Pages.url).filter(
            Pages.last_scan_date == None).all()
        return result_set

    def get_query_for_parse(self):
        # получаем весь список ссылок параметров для парсера
        result_set = self.session.query(Wordpairs.keyword_1,
                                        Wordpairs.keyword_2,
                                        Wordpairs.distance,
                                        Wordpairs.person_id).all()
        return result_set

    def add_page_to_table(self, url_list, id_site):
        for url in url_list:
            q = self.session.query(Pages).filter(url == Pages.url)
            if self.session.query(Pages).filter(q.exists()).count():
                print(url + ' уже есть')
            else:
                bullet = Pages(url=url, site_id=id_site,
                               found_date_time="{0:%Y-%m-%d %H:%M:%S}".format(
                                   datetime.now()))
                self.session.add(bullet)
                self.session.commit()


    def add_rank_to_table(self, count, id_url, id_persons):
        if count:
            bullet = PersonsPageRank(rank=count, page_id=id_url,
                                     person_id=id_persons)
            self.session.add(bullet)
            self.session.commit()

    def set_last_scan_date(self, id_url):
        scan_date = '{0:%Y-%m-%d %H:%M:%S}'.format(datetime.now())
        self.session.query(Pages).filter_by(id=id_url).update(
            {'last_scan_date': scan_date})
