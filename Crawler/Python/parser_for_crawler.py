#!/usr/bin/python3

import re
# нужно сначала установить reppy
from reppy.cache import RobotsCache
import xml.etree.ElementTree as ET
# нужна библиотека "pip install beautifulsoup4" и к ней еще нужна lxml
from bs4 import BeautifulSoup

# TODO: добавить логирование операций и возможных ошибок


class RobotsParser():
    def parse(self, site):
        # Функция которая получает ссылку на sitemap
        robots = RobotsCache()
        sitemaps = robots.sitemaps(site)
        return sitemaps


class SitemapParser():
    def _gen_ns(self, tag):
        # Функция для получения пространства имен xml, нужно для поиска по нему
        if tag.startswith('{'):
            ns, tag = tag.split('}')
            return ns[1:]
        else:
            return ''

    def parse_sitemap(self, sitemapfile):
        # получаем дерево xml
        # получаем корневой(родительский) элемент
        # получаем пространство имен для этого xml
        # потом ищем нужный нам тег('loc')
        # вытаскиваем из него запись и печатаем или передаемкуда нибудь
        tree = ET.parse(sitemapfile)
        root = tree.getroot()
        namespaces = {'ns': self._gen_ns(root.tag)}
        urls = []
        sitemaps = []
        for child in root:
            url = child.find('ns:loc', namespaces=namespaces).text
            url_type = str(re.findall(r'.+\.(.{2,4})', url)[0])
            if url_type == 'xml':
                sitemaps.append(url)
            else:
                urls.append(url)
        return {'sitemap' : sitemaps, 'urls': urls}


class HtmlParser():
    def parse_html(self, html_file, keyword1, keyword2, distance):
        soup = BeautifulSoup(open(html_file))
        text = soup.get_text()
        count = 0
        # строка для задания дистанции
        reguldistance = '\w+\s' * distance
        # компилим регулярку для поиска по тексту
        searchregul = re.compile('{0}\s{1}{2}\s|{2}\s{1}{0}\s'.format(
                                            keyword1, reguldistance,
                                            keyword2), re.IGNORECASE)
        count = len(re.findall(searchregul, text))
        return count
