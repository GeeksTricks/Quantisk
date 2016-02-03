#!/usr/bin/env python

import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET

# TODO: добавить логирование операций и возможных ошибок

class RobotsParser():
    def __init__(self):
        self.sitemap = ''

    def parse(self, site):
        # Функция которая получает ссылку на sitemap
        robots = RobotsCache()
        self.sitemap = robots.sitemaps(site)

class SitemapParser():
    def __init__(self):
        self.urls = []

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
        for child in root:
            self.urls.append(child.find('ns:loc', namespaces=namespaces).text)

class HtmlParser():
    def __init__(self):
        pass
        



