<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Сайты::Добавить";

if(isset($_POST['submit'])) {
	$name = $_POST['name'];
	if(SiteRepository::add($link, $name)) {
		header("location: sites.php");
	}
}


include('view/header.php');
include('view/v_new_site.php');
include('view/footer.php');