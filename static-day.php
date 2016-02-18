<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Ежедневная статистика";

$all_sites = SiteRepository::loadAll($link);
$all_persons = PersonRepository::loadAll($link);

if(isset($_POST['submit'])) {
	$person_id = $_POST['persons'];
	$site_id = $_POST['sites'];
	$start = $_POST['start'];
	$start = " '$start' ";
	$end = $_POST['end'];
	$end = " '$end' ";

	$person = PersonRepository::load($link, $person_id);
	$person_name = $person->getName();

	$site = SiteRepository::load($link, $site_id);
	$site_name = $site->getName();
	
	$period = RankRepository::loadByPeriod($link, $person_id, $site_id, $start, $end);
}

include('view/header.php');
include('view/v_static-day.php');
include('view/footer.php');