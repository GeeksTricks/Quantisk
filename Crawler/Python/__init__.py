#!/usr/bin/python3

from data_layer import DataLayer

import downloader
import parser_for_crawler
import time

# создаем экземпляры классов Загрузчика и Парсеров сайтмапа и роботс
data = DataLayer()
download = downloader.Downloader()
parser_robots = parser_for_crawler.RobotsParser()
parser_sitemap = parser_for_crawler.SitemapParser()
parser_html = parser_for_crawler.HtmlParser()


def get_url_list(link_list):
    # Получаем список ссылок
    all_url_list = []
    for link in link_list:
        sitemap_file = download.download_sitemap(link)
        urls = parser_sitemap.parse_sitemap(sitemap_file)
        link_list.extend(urls['sitemap'])
        all_url_list.extend(urls['urls'])
    return all_url_list


def get_runk_add_to_table(html_file, query_list, id_url):
    # Парсим html файл и добавляем в сессию ранк, если таковой есть
    for query in query_list:
        count = parser_html.parse_html(html_file, query.keyword_1,
                                       query.keyword_2, query.distance)
        data.set_last_scan_date(id_url)
        data.add_rank_to_table(count, id_url, query.person_id)


def main():
    while True:
        # получаем сайты из талицы Sites
        sites = data.get_site()

        for site in sites:
            sitemaps_urls = parser_robots.parse(site.name)
            url_list = get_url_list(sitemaps_urls)
            data.add_page_to_table(url_list, site.id)

        # Получаем список страниц из таблицы Pages которые еще не сканировали
        urls = data.get_not_scan_urls()
        # Получаем список параметров запроса для парсера
        queries = data.get_query_for_parse()

        for url in urls:
            print(url)     # отладка
            html_file = download.download_html(url.url)
            get_runk_add_to_table(html_file, queries, url.id)

        time.sleep(400)

if __name__ == "__main__":
    main()
