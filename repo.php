<?php
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

	public static function add($link, $name) {
		$name = trim($name);

		if ($name == '') {
			return false;
		}

		$sql = "INSERT INTO `persons` (`name`) VALUES ('%s')";
		$query = sprintf($sql, sql_escape($link, $name));

		$result = mysqli_query($link, $query);

		return true;
	}

	public static function edit($link, $person_id, $name) {
		$sql = "UPDATE `persons` SET `name` = '%s' WHERE `id` = $person_id";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);

		return true;
	}

	public static function delete($link, $person_id) {
		$sql = "DELETE FROM `persons` WHERE `id` = $person_id";
		$result = mysqli_query($link, $sql);
		return true;
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

	public static function add($link, $name) {
		$name = trim($name);

		if ($name == '') {
			return false;
		}

		$sql = "INSERT INTO `sites` (`name`) VALUES ('%s')";
		$query = sprintf($sql, sql_escape($link, $name));

		$result = mysqli_query($link, $query);

		return true;
	}

	public static function edit($link, $site_id, $name) {
		$sql = "UPDATE `sites` SET `name` = '%s' WHERE `id` = $site_id";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);

		return true;
	}

	public static function delete($link, $site_id) {
		$sql = "DELETE FROM `sites` WHERE `id` = $site_id";
		$result = mysqli_query($link, $sql);
		return true;
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
		$sql = "SELECT `rank`, `pageID`, `personID` FROM `personpagerank` WHERE `pageID` = $page_id AND `personID` = $person_id";
		$result = mysqli_query($link, $sql);

		if ($row = mysqli_fetch_assoc($result)) {
			$rank = $row['rank'];
			$page = $row['pageID'];
			$person = $row['personID'];
			return new Rank($rank, $page, $person);
		}

		return new Rank(0, $page_id, $person_id);
	}

// получить кол-во упоминаний (rank) за определенный период ($start-$end) выбранной личности на определенном сайте
	public static function loadByPeriod($link, $person_id, $site_id, $start, $end) {
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

		if(empty($site_page)) {
			$page = new Page(0, 0, $site_id, 0, 0);
			$site_page[] = $page;
		}

		return $site_page;				
	}

};

class WordpairRepository
{
	public static function loadAll($link) {
		$sql = 'SELECT * FROM `wordpairs` ';
		$result = mysqli_query($link, $sql);

		$pairs = array();

		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword1'];
			$keyword2 = $row['keyword2'];
			$distance = $row['distance'];
			$person = $row['personID'];
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);
			$pairs[] = $wordpair;
		}

		return $pairs;
	}

	public static function load($link, $keyword_id) {
		$sql = "SELECT * FROM `wordpairs` WHERE `id` = $keyword_id";
		$result = mysqli_query($link, $sql);

		if ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword1'];
			$keyword2 = $row['keyword2'];
			$distance = $row['distance'];
			$person = $row['personID'];								
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);

			return $wordpair;
		}

		return new Wordpair($id, 0, 0, 0, $person);			
	}	

	public static function loadByPerson($link, $person) {
		$sql = "SELECT * FROM `wordpairs` WHERE `personID` = $person";
		$result = mysqli_query($link, $sql);

		$pair_person = array();

		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword1'];
			$keyword2 = $row['keyword2'];
			$distance = $row['distance'];
			$person = $row['personID'];
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);
			$pair_person[] = $wordpair;
		}

		return $pair_person;			
	}

	public static function add($link, $person_id, $keyword1, $keyword2) {
		$keyword1 = trim($keyword1);
		$keyword2 = trim($keyword2);		

		if ($keyword1 == '' && $keyword2 == '') {
			return false;
		}

		$sql = "INSERT INTO `wordpairs` (`keyword1`, `keyword2`, `personID`) VALUES ('%s', '%s', $person_id) " ;
		$query = sprintf($sql, sql_escape($link, $keyword1), sql_escape($link, $keyword2));

		$result = mysqli_query($link, $query);

		return true;
	}

	public static function edit($link, $keyword1, $keyword2, $keyword_id) {
		$sql = "UPDATE `wordpairs` SET `keyword1` = '%s', `keyword2` = '%s' WHERE `id` = $keyword_id";
		$query = sprintf($sql, sql_escape($link, $keyword1), sql_escape($link, $keyword2));
		$result = mysqli_query($link, $query);

		return true;
	}

	public static function delete($link, $keyword_id) {
		$sql = "DELETE FROM `wordpairs` WHERE `id` = $keyword_id";
		$result = mysqli_query($link, $sql);
		return true;
	}
};