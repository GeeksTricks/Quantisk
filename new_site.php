<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Сайты: Добавить";
$error = false;

if(isset($_POST['submit'])) {
	$name = trim($_POST['name']);

	if($name != '') {
		if(SiteRepository::add($link, $name)) {
			header("location: sites.php");
		}
	} else {
		$error = true;
	}
}

include('view/header.php');
include('view/v_new_site.php');
include('view/footer.php');