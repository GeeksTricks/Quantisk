#!/usr/bin/env python

import logging
from logging.handlers import RotatingFileHandler

logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
handler = RotatingFileHandler('sys.log', encoding='utf8',\
                               maxBytes=100000, backupCount=1)
handler.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(filename)s[LINE:%(lineno)d]# '\
                            + '%(levelname)-8s [%(asctime)s]  %(message)s')
handler.setFormatter(formatter)
logger.addHandler(handler)
