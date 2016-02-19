<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Справочник: Ключевые слова";
$error = false;

$all_wordpairs = WordpairRepository::loadAll($link);
$all_persons = PersonRepository::loadAll($link);
$pair_person = '';

if(isset($_POST['submit'])) {
	$person_id = $_POST['persons'];
	if($person_id != 0) {
		$person = PersonRepository::load($link, $person_id);
		$one_person = $person->getName();
		$pair_person = WordpairRepository::loadByPerson($link, $person_id);
	} else {
		$error = true;
	}
} 

include('view/header.php');
include('view/v_wordpairs.php');
include('view/footer.php');