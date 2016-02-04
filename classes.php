<?php

class Person
	{
	private $id;
	private $name;

	function __construct($id, $name) {
		$this->id = $id;
		$this->name = $name; 
	}
};

class Site
	{
	private $id;
	private $name;

	function __construct($id, $name) {
		$this->id = $id;
		$this->name = $name; 
	}
};

class Rank
	{
	private $rank;
	private $person;
	private $page;

	function __construct($rank, $person, $page) {
		$this->rank = $rank;
		$this->person = $person;
		$this->page = $page;
	}
};

class Page
	{
	private $id;
	private $url;
	private $site;
	private $found_date;
	private $scan_date;

	function __construct($id, $url, $site, $found_date, $scan_date) {
		$this->id = $id;
		$this->url = $url;
		$this->site = $site;
		$this->found_date = $found_date;
		$this->scan_date = $scan_date;
	}
};

class PersonRepository
	{
	public function loadAll(){
		var persons = [15 => new Person(15, 'DiCaprio'),
						22 => new Person(22, 'Damon')];
		return persons;
	}

	public function load($PersonID) {
		return new Person(15, 'DiCaprio');
	};

};

class SiteRepository
	{
	public function loadAll(){
		var sites = [1 => new Site(1, 'lifejournal.ru'),
						2 => new Site(2, 'lenta.ru')];
		return sites;
	}

	public function load($SiteID) {
		return new Site(1, 'lifejournal.ru', );
	};
};

class RankRepository
	{
	public function loadAll() {
		var rank = [new Rank(9, new Person(15, 'DiCaprio'), new Site(1, 'lifejournal.ru')), new Rank(5, new Person(22, 'Damon'), new Site(2, 'lenta.ru'))];
		return rank;
	}

	public function load($Person_ID, $Page_ID) {
		return new Rank(9, new Person(15, 'DiCaprio'), new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50'));
	};

	// получить кол-во упоминаний (rank) за определенный период ($start-$end) выбранной личности на определенном сайте
	public function loadByPeriod($siteid, $personid, $start, $end) {
		var dateRank = ['20.05.2015' => 9,
						'20.06.2016' => 15];
		return dateRank;
	};
};

class PageRepository
	{
	public function loadAll() {
		var pages = [1 => new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50')];
		return pages;
	};

	public function load($PageID) {
		return new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50');
	};

	// получить ID страниц одного сайта
	public function selectAllBySiteID($SiteID) {
		var pages = [1, 2, 3];
		return pages;
	}
};