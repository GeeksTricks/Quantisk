import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET

'''TODO:
    + найти и скачать Sitemap
    + Вытащить из Sitemap все ссылки
    - Написать ORM класс дл я отправки ссылок в таблицу Pages
    - Отправить ссылки в БД
    - Написать функцию(класс) для скачивания страницы'''

class GetUrlFromSitemap():
    def __init__(self, link):
        self.link = link
    def get_sitemap_url(self, url): # Получаем ссылку на sitemap с использование reppy
        robots = RobotsCache()
        sitemapUrl = robots.sitemaps(url)[0]
        return sitemapUrl
    def gen_ns(self, tag): # Функция для получения пространства имен xml, нужно для поиска по нему
        if tag.startswith('{'):
            ns, tag = tag.split('}')
            return ns[1:]
        else:
            return ''
    def sitemap_download(self):
        #скачиваем карту сайта(в случае неудачи выводим сообщение)
        sitemapurl = self.get_sitemap_url(self.link)
        sitemap = str(re.findall(r'.*\/(.+\..{3,4})$', sitemapurl)[0])
        try:
            with request.urlopen(sitemapurl) as response, open(sitemap, 'wb') as out_file:
                shutil.copyfileobj(response, out_file)
            return sitemap
        except error.HTTPError:
            print('Неполучилось скачать sitemap.')
    def parse_sitemap(self):
        #получаем дерево xml
        #получаем корневой(родительский) элемент
        #получаем пространство имен для этого xml
        #потом ищем нужный нам тег('loc')
        #вытаскиваем из него запись и печатаем ее в стандартный вывод или передаемкуда нибудь
        sitemap = self.sitemap_download()
        tree = ET.parse(sitemap)
        root = tree.getroot()
        namespaces = {'ns': self.gen_ns(root.tag)}
        for child in root:
            print(child.find('ns:loc', namespaces=namespaces).text)

'''кусок для проверки работы.

m = GetUrlFromSitemap('http://www.livejournal.ru')
m.parse_sitemap
'''



