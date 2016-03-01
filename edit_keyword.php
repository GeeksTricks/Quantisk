<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Ключевые слова: Редактирование";
$keyword_id = $_GET['id'];

$keyword = WordpairRepository::load($link, $keyword_id);
$keyword1 = $keyword->getKeyword1();
$keyword2 = $keyword->getKeyword2();
$distance = $keyword->getDistance();

if(isset($_POST['submit'])) {
	$keyword1 = $_POST['name1'];
	$keyword2 = $_POST['name2'];	
	$distance = $_POST['distance'];	
	
	if(WordpairRepository::edit($link, $keyword1, $keyword2, $keyword_id, $distance)) {
		header("location: wordpairs.php");
	}
}

include('view/header.php');
include('view/v_edit_keyword.php');
include('view/footer.php');