import Downloader
import Parser
import db
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from datetime import datetime

engine = create_engine('# DB connection string', echo=False)
Session = sessionmaker(bind=engine)
session = Session()

def GetSite():
    # получаем весь список сайтов
    result_set = session.query(db.sites.id, db.sites.name).all():
    return result_set

def GetPersons():
    # получаем весь список Личностей
    result_set = session.query(db.Persons.id, db.Persons.name).all():
    return result_set

def GetNotScanUrls():
    # получаем весь список ссылок с пустой датой сканирвоания
    result_set = session.query(db.Pages.id, db.Pages.url).filter(db.Pages.last_scan_date == None).all():
    return result_set

def GetQueryForParse():
    # получаем весь список ссылок параметров для парсера
    result_set = session.query(db.Wordpairs.keyword_1,
                             db.Wordpairs.keyword_2, db.Wordpairs.distance,
                             db.Wordpairs.person_id).all():
    return result_set

# создаем экземпляры классов Загрузчика и Парсеров сайтмапа и роботс
parser_robots = Parser.RobotsParser()
parser_sitemap = Parser.SitemapParser()
downloader = Downloader.Downloader()

# получаем сайты из талицы Sites
sites = GetSite()

# Эта конструкция выжигает мне глаза((((((
# Она получает все сайты из таблицы sites
# За тем по каждому сайту парситься robots.txt 
# и вытаскиваються sitemap'ы
# Затем парситься каждый sitemap и полученный ссылки 
# отправляються в очеред на запись в таблицу Pages
#  Последним этапом идет запись в базу, 
# и потом для след сайта все повторяеться
for site in sites:
    sitemaps_urls = parser_robots.parse(site.name)
    for sitemap_url in sitemaps_urls:
        sitemap_file = downloader.download_sitemap(sitemap_url)
        urls=Parser.parse_sitemap(sitemap_file)
        for url in urls:
            bullet = db.Pages(url=url, site_id=site.id, found_date_time='{0:%Y-%m-%d %H:%M:%S}'.format(datetime.now()))
            session.add(bullet)
session.commit()

# Получаем список страниц из таблицы Pages которые еще не сканировали
# Получаем список параметров запроса для парсера
urls = GetNotScanUrls()
queryes = GetQueryForParse()

# Обходим полученные ссылки
# скачиваем страницу
# исчем на странице все возможные варианты запросов для парсера
# если на странице найдены значения, то они добавляються в таблицу
# Так по каждой странице, т.е мы по одной странице проходимся со всеми личностями
# и условиями парсинга, и берем следующую, даные о поиске заносим в таблицу PersonPageRank
for url in urls:
    html_file = Downloader.download_html(url.id, url.url)
    for query in queryes:
        count = Parser.html_parse(query.keyword_1, query.keyword_2, query.distance)
        if count:
            bullet = db.PushToPersonPageRank(rank = count, page_id = url.id, person_id = query.person_id)
            session.add(bullet)
session.commit()
    



