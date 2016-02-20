<?php
class PersonRepository
{
	public static function loadAll($link) {
		$sql = 'SELECT * FROM `Persons` ';
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
		$sql = "SELECT * FROM `Persons` WHERE `id` = $person_id";
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
		$sql = "INSERT INTO `Persons` (`name`) VALUES ('%s')";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function edit($link, $person_id, $name) {
		$sql = "UPDATE `Persons` SET `name` = '%s' WHERE `id` = $person_id";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function delete($link, $person_id) {
		$sql = "DELETE FROM `Persons` WHERE `id` = $person_id";
		$result = mysqli_query($link, $sql);
		return true;
	}
};

class SiteRepository
{
	public static function loadAll($link) {
		$sql = 'SELECT * FROM `Sites` ';
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
		$sql = "SELECT * FROM `Sites` WHERE `id` = $site_id";
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
		$sql = "INSERT INTO `Sites` (`name`) VALUES ('%s')";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function edit($link, $site_id, $name) {
		$sql = "UPDATE `Sites` SET `name` = '%s' WHERE `id` = $site_id";
		$query = sprintf($sql, sql_escape($link, $name));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function delete($link, $site_id) {
		$sql = "DELETE FROM `Sites` WHERE `id` = $site_id";
		$result = mysqli_query($link, $sql);
		return true;
	}
};

class RankRepository
{
	public static function loadAll($link) {
		$sql = 'SELECT * FROM `PersonsPageRank` ';
		$result = mysqli_query($link, $sql);
		$ranks = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$rank = $row['rank'];
			$page = $row['page_id'];
			$person = $row['person_id'];
			$obj = new Rank($rank, $page, $person);
			$ranks[] = $obj;				
		}
		return $ranks;
	}

	public static function load($link, $page_id, $person_id) {
		$sql = "SELECT * FROM `PersonsPageRank` WHERE `page_id` = $page_id AND `person_id` = $person_id";
		$result = mysqli_query($link, $sql);
		if ($row = mysqli_fetch_assoc($result)) {
			$rank = $row['rank'];
			$page = $row['page_id'];
			$person = $row['person_id'];
			return new Rank($rank, $page, $person);
		}
		return new Rank(0, $page_id, $person_id);
	}

// получить кол-во упоминаний (rank) за определенный период ($start-$end) выбранной личности на определенном сайте
	public static function loadByPeriod($link, $person_id, $site_id, $start, $end) {
		$sql = "SELECT SUM(PersonsPageRank.rank) as rank, DATE_FORMAT(Pages.found_date_time,'%d-%m-%Y') as found_date_time
				FROM PersonsPageRank JOIN Pages ON PersonsPageRank.page_id = Pages.id 
				WHERE PersonsPageRank.person_id = $person_id 
					AND Pages.site_id = $site_id 
					AND Pages.found_date_time BETWEEN $start AND $end
				GROUP BY DATE_FORMAT(Pages.found_date_time,'%d-%m-%Y')";
		$result = mysqli_query($link, $sql);
		$period = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$rank = $row['rank'];
			$foundDateTime = $row['found_date_time'];
			$period[$foundDateTime] = $rank;				
		}
		return $period;			
	}

};

class PageRepository
{
	public static function loadAll($link) {
		$sql = 'SELECT * FROM `Pages` ';
		$result = mysqli_query($link, $sql);
		$pages = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$url = $row['url'];
			$siteID = $row['site_id'];
			$foundDateTime = $row['found_date_time'];
			$lastScanDate = $row['last_scan_date'];								
			$page = new Page($id, $url, $siteID, $foundDateTime, $lastScanDate);
			$pages[] = $page;				
		}
		return $pages;			
	}

	public static function load($link, $page_id) {
		$sql = "SELECT * FROM `Pages` WHERE `id` = $page_id";
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
		$sql = "SELECT * FROM `Pages` WHERE `site_id` = $site_id";
		$result = mysqli_query($link, $sql);
		$site_page = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$url = $row['url'];
			$siteID = $row['site_id'];
			$foundDateTime = $row['found_date_time'];
			$lastScanDate = $row['last_scan_date'];								
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
		$sql = 'SELECT * FROM `Wordpairs` ';
		$result = mysqli_query($link, $sql);
		$pairs = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword_1'];
			$keyword2 = $row['keyword_2'];
			$distance = $row['distance'];
			$person = $row['person_id'];
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);
			$pairs[] = $wordpair;
		}
		return $pairs;
	}

	public static function load($link, $keyword_id) {
		$sql = "SELECT * FROM `Wordpairs` WHERE `id` = $keyword_id";
		$result = mysqli_query($link, $sql);
		if ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword_1'];
			$keyword2 = $row['keyword_2'];
			$distance = $row['distance'];
			$person = $row['person_id'];								
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);
			return $wordpair;
		}
		return new Wordpair($id, 0, 0, 0, $person);			
	}	

	public static function loadByPerson($link, $person) {
		$sql = "SELECT * FROM `Wordpairs` WHERE `person_id` = $person";
		$result = mysqli_query($link, $sql);
		$pair_person = array();
		while ($row = mysqli_fetch_assoc($result)) {
			$id = $row['id'];
			$keyword1 = $row['keyword_1'];
			$keyword2 = $row['keyword_2'];
			$distance = $row['distance'];
			$person = $row['person_id'];
			$wordpair = new Wordpair($id, $keyword1, $keyword2, $distance, $person);
			$pair_person[] = $wordpair;
		}
		return $pair_person;			
	}

	public static function add($link, $person_id, $keyword1, $keyword2, $distance) {
		$keyword1 = trim($keyword1);
		$keyword2 = trim($keyword2);
		$distance = (int)$distance;		
		if ($keyword1 == '' && $keyword2 == '') {
			return false;
		}
		$sql = "INSERT INTO `Wordpairs` (`keyword_1`, `keyword_2`, `person_id`, `distance`) VALUES ('%s', '%s', $person_id, $distance) " ;
		$query = sprintf($sql, sql_escape($link, $keyword1), sql_escape($link, $keyword2));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function edit($link, $keyword1, $keyword2, $keyword_id, $distance) {
		$distance = (int)$distance;
		$sql = "UPDATE `Wordpairs` SET `keyword_1` = '%s', `keyword_2` = '%s', `distance` = $distance WHERE `id` = $keyword_id";
		$query = sprintf($sql, sql_escape($link, $keyword1), sql_escape($link, $keyword2));
		$result = mysqli_query($link, $query);
		return true;
	}

	public static function delete($link, $keyword_id) {
		$sql = "DELETE FROM `Wordpairs` WHERE `id` = $keyword_id";
		$result = mysqli_query($link, $sql);
		return true;
	}
};