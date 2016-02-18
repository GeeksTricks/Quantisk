<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Ключевые слова: Добавить";
$person_id=$_GET['id'];

if(isset($_POST['submit'])) {
	$name1 = $_POST['name1'];
	$name2 = $_POST['name2'];
	$distance = $_POST['distance'];	

	if(WordpairRepository::add($link, $person_id, $name1, $name2, $distance)) {
		header("location: wordpairs.php");
	}
}

include('view/header.php');
include('view/v_new_keyword.php');
include('view/footer.php');