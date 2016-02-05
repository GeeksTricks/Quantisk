# -*- coding: utf-8 -*-

from __future__ import unicode_literals

from django.db import models

class Person(models.Model):
	second_name = models.CharField(max_length=180, db_index=True, unique=True, blank=False, default='', verbose_name="Фамилия")
		
	class Meta:
		verbose_name = 'Личность'
		verbose_name_plural = 'Личности'

	def __unicode__(self):

		return '{}'.format(self.second_name)