<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Сайты::Редактирование";
$site_id = $_GET['id'];

$site = SiteRepository::load($link, $site_id);
$site_name = $site->getName();

if(isset($_POST['submit'])) {
	$site_name = $_POST['name'];
	echo $site_name;
	if(SiteRepository::edit($link, $site_id, $site_name)) {
		header("location: sites.php");
	}
}


include('view/header.php');
include('view/v_edit_site.php');
include('view/footer.php');