<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Личности: Добавить";
$error = false;

if(isset($_POST['submit'])) {
	$name = trim($_POST['name']);

	if($name != "") {
		if(PersonRepository::add($link, $name)) {
			header("location: persons.php");
		}
	} else {
		$error = true;
	}
}

include('view/header.php');
include('view/v_new_person.php');
include('view/footer.php');