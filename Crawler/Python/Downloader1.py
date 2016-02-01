#!/usr/bin/env python
from urllib import request
from urllib import error
import shutil
import re

# TODO: добавить логирование операций и возможных ошибок

class Downloader():
	def __init__(self):
		self.filename = ''

	def download(self, link):
        page = str(re.findall(r'.*\/(.+\..{2,4})$', link)[0])
		try:
            with request.urlopen(link) as response,\
                 open(page, 'wb') as out_file:
                shutil.copyfileobj(response, out_file)
            self.filename = page
        except error.HTTPError:
            print('Неполучилось скачать sitemap.')







