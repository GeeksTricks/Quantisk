from data_layer import DataLayer

import downloader
import parser

# создаем экземпляры классов Загрузчика и Парсеров сайтмапа и роботс
parser_robots = parser.RobotsParser()
parser_sitemap = parser.SitemapParser()
parser_html = parser.HtmlParser()
download = downloader.Downloader()
data = DataLayer()

# Получаем список ссылок
def get_url_list(link_list):
    url_list = []
    for link in link_list:
        sitemap_file = download.download_html(link)
        urls = parser_sitemap.parse_sitemap(sitemap_file)
        url_list.extend(urls)
    return url_list

# Парсим html файл и добавляем в сессию ранк, если таковой есть
def get_runk_add_to_table(html_file, query_list, id_url):
    for query in query_list:
        count = parser_html.parse_html(html_file, query.keyword_1,
                                       query.keyword_2, query.distance)
        data.add_rank_to_table(count, id_url, query.person_id)

# получаем сайты из талицы Sites
sites = data.get_site()

for site in sites:
    sitemaps_urls = parser_robots.parse(site.name)
    url_list = get_url_list(sitemaps_urls)
    data.add_page_to_table(url_list, site.id)

# Отправляем полученные данные в базу
data.push_data_to_db()

# Получаем список страниц из таблицы Pages которые еще не сканировали
urls = data.get_not_scan_urls()
# Получаем список параметров запроса для парсера
queries = data.get_query_for_parse()

for url in urls:
    html_file = download.download_html(url.url)
    get_runk_add_to_table(html_file, queries, url.id)

# Отправляем полученные данные в базу
data.push_data_to_db()
    



