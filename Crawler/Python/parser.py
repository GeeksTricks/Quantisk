#!/usr/bin/env python

import re
# нужно сначала установить reppy
from reppy.cache import RobotsCache
import xml.etree.ElementTree as ET
# нужна библиотека "pip install beautifulsoup4" и к ней еще нужна lxml
from bs4 import BeautifulSoup

# TODO: а в идеале всетаки надо было использовать GRAB
# по Роадмапу у нас есть цели асинхронного парсинга, так что пока надо плотно
# присмтриваться к этому делу, есть еще библиотека scrappy
# насколько я понял она тоже для разбора сайтов, тоже можно посмотреть.
# с версии 3 надо будет использовать ее

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
        for child in root:
            urls.append(child.find('ns:loc', namespaces=namespaces).text)
        return urls

class HtmlParser():
    def parse_html(self, html_file, keyword1, keyword2, distance):
        soup = BeautifulSoup(open(html_file))
        text = soup.get_text()
        count = 0
        # строка для задания дистанции
        reguldistance = r'\w+\s' * distance
        # компилим регулярку для поиска по тексту
        searchregul = re.compile(r'''{0}\s{1}{2}|{2}\s{1}{0}'''
                                  .format(keyword1, reguldistance,
                                          keyword2), re.IGNORECASE)
        count = len(re.findall(searchregul, text))
        return count