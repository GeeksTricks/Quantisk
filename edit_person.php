<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Личности::Редактирование";
$person_id = $_GET['id'];

$person = PersonRepository::load($link, $person_id);
$person_name = $person->getName();

if(isset($_POST['submit'])) {
	$person_name = $_POST['name'];
	if(PersonRepository::edit($link, $person_id, $person_name)) {
		header("location: persons.php");
	}
}


include('view/header.php');
include('view/v_edit_person.php');
include('view/footer.php');