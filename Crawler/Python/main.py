#!/usr/bin/env python

import re
from urllib import request
from urllib import robotparser
import xml.etree.ElementTree as ET

site_url = 'http://' +'www.livejournal.com'
robotparser = robotparser.RobotFileParser()
robotparser.set_url(site_url + '/robots.txt')
robotparser.read()

'''
TODO:

- Скачать robots.txt
- найти и скачать Sitemap
- Вытащить из Sitemap все ссылки
- Написать ORM класс дл я отправки ссылок в таблицу Pages
- Отправить ссылки в БД
- Написать функцию(класс) для скачивания страницы
'''





