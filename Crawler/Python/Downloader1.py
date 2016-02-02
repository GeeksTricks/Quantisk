#!/usr/bin/env python
from urllib import request
from urllib import error
import shutil
import re

# TODO: добавить логирование операций и возможных ошибок

class Downloader():
    def __init__(self):
        self.sitemap = ''
        self.html_file = ''

    def download_sitemap(self, link):
        sitemap = str(re.findall(r'.*\/(.+\..{2,4})$', link)[0])
        try:
            with request.urlopen(link) as response,\
                 open(sitemap, 'wb') as out_file:
                shutil.copyfileobj(response, out_file)
            self.sitemap = sitemap
        except error.HTTPError:
            print('Неполучилось скачать sitemap.')
            
    def download_html(self, id, link):
        # ID мы должны получить из базы
        # используем для имени файла
        html_file = id + str(re.findall(r'.*\/.+(\..{2,4})$', link)[0])
        try:
            with request.urlopen(link) as response,\
                 open(html_file, 'wb') as out_file:
                shutil.copyfileobj(response, out_file)
            self.html_file = html_file
        except error.HTTPError:
            print('Неполучилось скачать html.')







