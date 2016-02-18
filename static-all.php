<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Общая статистика";
$pids = [];
$ranks = [];

$all_sites = SiteRepository::loadAll($link);

$all_persons = PersonRepository::loadAll($link);

if(isset($_POST['submit'])) {
	$site_id =  $_POST['sites'];
	$site_page = PageRepository::selectAllBySiteID($link, $site_id); //массив объектов страниц

	foreach ($site_page as $ids) {
		$pid= $ids->getId();
		$pids[] = $pid;
	}

	$site_id = $_POST['sites'];
	$site = SiteRepository::load($link, $site_id);
	$site_name = $site->getName();
}

include('view/header.php');
include('view/v_static-all.php');
include('view/footer.php');