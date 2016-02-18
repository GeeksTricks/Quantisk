<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Личности::Добавить";

if(isset($_POST['submit'])) {
	$name = $_POST['name'];
	if(PersonRepository::add($link, $name)) {
		header("location: persons.php");
	}
}


include('view/header.php');
include('view/v_new_person.php');
include('view/footer.php');