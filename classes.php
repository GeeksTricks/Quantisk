<?php
class Person
	{
	private $id;
	private $name;

	function __construct($id, $name) {
		$this->id = $id;
		$this->name = $name; 
	}

	public function getName() {
		return $this->name;
	}

	public function getId() {
		return $this->id;
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

	public function getName() {
		return $this->name;
	}

	public function getId() {
		return $this->id;
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

	public function getRank() {
		return $this->rank;
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

	public function getId() {
		return $this->id;
	}	
};

class Wordpair
{
	private $id;
	private $keyword1;
	private $keyword2;
	private $distance;
	private $person;

	function __construct($id, $keyword1, $keyword2, $distance, $person) {
		$this->id = $id;
		$this->keyword1 = $keyword1;
		$this->keyword2 = $keyword2;
		$this->distance = $distance;
		$this->person = $person;
	}

	public function getId() {
		return $this->id;
	}	

	public function getKeyword1() {
		return $this->keyword1;
	}

	public function getKeyword2() {
		return $this->keyword2;
	}	

	public function getDistance() {
		return $this->distance;
	}		
}