#!/usr/bin/env python

import re
from urllib import request
from urllib import robotparser
import xml.etree.ElementTree as ET


# Скачивание robots.txt
print('Скачиваем файл robots.txt...')
site_url = 'http://' + 'www.livejournal.com'
robots_url = site_url + '/robots.txt'
download_robots = request.urlretrieve(robots_url, 'robots.txt')
print('Файл robots.txt скачен!')