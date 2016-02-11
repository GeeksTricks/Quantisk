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
	private $page;
	private $person;	

	function __construct($rank, $page, $person) {
		$this->rank = $rank;
		$this->page = $page;
		$this->person = $person;
	}
};

class Page
	{
	private $id;
	private $url;
	private $site_id;
	private $found_date;
	private $scan_date;

	function __construct($id, $url, $site_id, $found_date, $scan_date) {
		$this->id = $id;
		$this->url = $url;
		$this->site_id = $site_id;
		$this->found_date = $found_date;
		$this->scan_date = $scan_date;
	}
};

class PersonRepository
	{
		public static function loadAll($link) {
			$sql = 'SELECT `id`, `name` FROM `persons` ';
			$result = mysqli_query($link, $sql);

			$persons = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$name = $row['name'];
				$person = new Person($id, $name);
				$persons[$id] = $person;
			}

			return $persons;		
		}

		public static function load($link, $person_id) {
			$sql = "SELECT `id`, `name` FROM `persons` WHERE `id` = $person_id";
			$result = mysqli_query($link, $sql);

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$name = $row['name'];
				$person = new Person($id, $name);
			}

			return $person;	
		}

	};

class SiteRepository
	{
		public static function loadAll($link) {
			$sql = 'SELECT `id`, `name` FROM `sites` ';
			$result = mysqli_query($link, $sql);

			$sites = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$name = $row['name'];
				$site = new Site($id, $name);
				$sites[$id] = $site;
			}

			return $sites;
		}

		public static function load($link, $site_id) {
			$sql = "SELECT `id`, `name` FROM `sites` WHERE `id` = $site_id";
			$result = mysqli_query($link, $sql);

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$name = $row['name'];
				$site = new Site($id, $name);
			}

			return $site;	
		}
};

class RankRepository
	{
		public static function loadAll($link) {
			$sql = 'SELECT `rank`, `pageID`, `personID` FROM `personpagerank` ';
			$result = mysqli_query($link, $sql);

			$ranks = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$rank = $row['rank'];
				$page = $row['pageID'];
				$person = $row['personID'];
				$obj = new Rank($rank, $page, $person);
				$ranks[] = $obj;				
			}

			return $ranks;
		}

		public static function load($link, $page_id, $person_id) {
		// return new Rank(9, new Person(15, 'DiCaprio'), new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50'));
			$sql = "SELECT `rank`, `pageID`, `personID` FROM `personpagerank` WHERE `pageID` = $page_id AND `personID` = $person_id";
			$result = mysqli_query($link, $sql);

			while ($row = mysqli_fetch_assoc($result)) {
				$rank = $row['rank'];
				$page = $row['pageID'];
				$person = $row['personID'];
				$obj = new Rank($rank, $page, $person);
			}

			return $obj;
		}

	// получить кол-во упоминаний (rank) за определенный период ($start-$end) выбранной личности на определенном сайте
		public static function loadByPeriod($link, $person_id, $site_id, $start, $end) {
			// $dateRank = ['20.05.2015' => 9,
			// 				'20.06.2016' => 15];
			// return dateRank;
			$sql = "SELECT personpagerank.rank, pages.foundDateTime FROM personpagerank JOIN pages ON personpagerank.pageID = pages.id WHERE personpagerank.personID = $person_id AND pages.siteID = $site_id AND pages.foundDateTime BETWEEN $start AND $end";
			$result = mysqli_query($link, $sql);

			$period = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$rank = $row['rank'];
				$foundDateTime = $row['foundDateTime'];
				$period[$foundDateTime] = $rank;				
			}

			return $period;			
		}
};

class PageRepository
	{
		public static function loadAll($link) {
			// $pages = [1 => new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50')];
			// return pages;
			$sql = 'SELECT `id`, `url`, `siteID`, `foundDateTime`, `lastScanDate` FROM `pages` ';
			$result = mysqli_query($link, $sql);

			$pages = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$url = $row['url'];
				$siteID = $row['siteID'];
				$foundDateTime = $row['foundDateTime'];
				$lastScanDate = $row['lastScanDate'];								
				$page = new Page($id, $url, $siteID, $foundDateTime, $lastScanDate);
				$pages[] = $page;				
			}

			return $pages;			
		}

		public static function load($link, $page_id) {
			// return new Page(1, 'lifejournal.ru/article', new Site(1, 'lifejournal.ru'), '20.05.2015 19:48', '21.05.2015 21:50');
			$sql = "SELECT `id`, `url`, `siteID`, `foundDateTime`, `lastScanDate` FROM `pages` WHERE `id` = $page_id";
			$result = mysqli_query($link, $sql);

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$url = $row['url'];
				$siteID = $row['siteID'];
				$foundDateTime = $row['foundDateTime'];
				$lastScanDate = $row['lastScanDate'];								
				$page = new Page($id, $url, $siteID, $foundDateTime, $lastScanDate);
			}

			return $page;			
		}

		// получить ID страниц одного сайта
		public static function selectAllBySiteID($link, $site_id) {
			// $pages = [1, 2, 3];
			// return pages;
			$sql = "SELECT * FROM `pages` WHERE `siteID` = $site_id";
			$result = mysqli_query($link, $sql);

			$site_page = array();

			while ($row = mysqli_fetch_assoc($result)) {
				$id = $row['id'];
				$url = $row['url'];
				$siteID = $row['siteID'];
				$foundDateTime = $row['foundDateTime'];
				$lastScanDate = $row['lastScanDate'];								
				$page = new Page($id, $url, $siteID, $foundDateTime, $lastScanDate);
				$site_page[] = $page;
			}

			return $site_page;				
		}
};