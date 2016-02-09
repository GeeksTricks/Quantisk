#!/usr/bin/env python

import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET
from bs4 import BeautifulSoup

# нужна библиотека "pip install beautifulsoup4" и к ней еще нужна lxml, с их помощью парситься html;
# TODO: а в идеале всетаки надо было использовать GRAB
# по Роадмапу у нас есть цели асинхронного парсинга, так что пока надо плотно присамтриваться к этому делу
# есть еще библиотека scrappy насколько я понял она тоже для разбора сайтов, тоже можно посмотреть.
# с версии 3 надо будет использовать ее

# TODO: добавить логирование операций и возможных ошибок

class RobotsParser():
    def parse(self, site):
        # Функция которая получает ссылку на sitemap
        robots = RobotsCache()
        sitemaps = robots.sitemaps(site)
        return sitemaps

class SitemapParser():
    def gen_ns(self, tag):
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
        namespaces = {'ns': self.gen_ns(root.tag)}
        urls = []
        for child in root:
            urls.append(child.find('ns:loc', namespaces=namespaces).text)
        return urls

class HtmlParser():
    def parse_html(keyword1, keyword2, distance, html_file):
        pass