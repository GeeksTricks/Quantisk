#!/usr/bin/env python

import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET

site_url = 'http://' +'www.livejournal.com'#нужно получить из БД
robots = RobotsCache()
sitemapUrl = robots.sitemaps(site_url)

def gen_ns(tag): 
    if tag.startswith('{'):
        ns, tag = tag.split('}')
        return ns[1:]
    else:
        return ''

def download_sitemap(link):
	file_name = 'www.livejournal.com' + '_' + str(re.findall(r'.*\/(.+\..{3,4})$', link)[0])
	try:
		with request.urlopen(link) as response, open(file_name, 'wb') as out_file:
			shutil.copyfileobj(response, out_file)
	except error.HTTPError:
		print('неполучилось скачать sitemap')
	return file_name

sitemapXml = download_sitemap(sitemapUrl[0])
tree = ET.parse(sitemapXml)
root = tree.getroot()
namespaces = {'ns': gen_ns(root.tag)}
URLs = [] #Временное хранилище для ссылок
for child in root:
	URLs.append(child.find('ns:loc', namespaces=namespaces).text)

'''
TODO:

+ Скачать robots.txt
+ найти и скачать Sitemap
+ Вытащить из Sitemap все ссылки
- Написать ORM класс дл я отправки ссылок в таблицу Pages
- Отправить ссылки в БД
- Написать функцию(класс) для скачивания страницы
'''





