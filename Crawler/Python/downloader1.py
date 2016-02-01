#!/usr/bin/env python

import re
from urllib import request
from urllib import error
import shutil
from reppy.cache import RobotsCache #нужно сначала установить reppy
import xml.etree.ElementTree as ET


'''
TODO:

+ Скачать robots.txt
+ найти и скачать Sitemap
+ Вытащить из Sitemap все ссылки
- Написать ORM класс дл я отправки ссылок в таблицу Pages
- Отправить ссылки в БД
- Написать функцию(класс) для скачивания страницы
'''





