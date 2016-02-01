import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET

class GetSitemap():
    def __init__(self, link):
        self.link = link
        robots = RobotsCache()
        self.sitemapUrl = robots.sitemaps(self.link)

class GetUrlFromSitemap():
    def __init__(self, sitemap):
        self.sitemap = sitemap
    def gen_ns(tag):
        if tag.startswith('{'):
            ns, tag = tag.split('}')
            return ns[1:]
        else:
            return ''
    def download_sitemap():
        file_name = str(re.findall(r'.*\/(.+\..{3,4})$', self.sitemap)[0])
        try:
            with request.urlopen(self.sitemap) as response, open(file_name, 'wb') as out_file:
                shutil.copyfileobj(response, out_file)
        except error.HTTPError:
            print('неполучилось скачать sitemap')
        return file_name
    def parse_sitemap():
        sitemapxml = download_sitemap(self.sitemap)
        tree = ET.parse(sitemapxml)
        root = tree.getroot(namespaces = {'ns': gen_ns(root.tag)})
        for child in root:
            print(child.find('ns:loc', namespaces=namespaces).text)


'''
TODO:

+ найти и скачать Sitemap
+ Вытащить из Sitemap все ссылки
- Написать ORM класс дл я отправки ссылок в таблицу Pages
- Отправить ссылки в БД
- Написать функцию(класс) для скачивания страницы
'''
