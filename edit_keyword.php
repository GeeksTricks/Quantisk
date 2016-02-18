<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Личности::Редактирование";
$keyword_id = $_GET['id'];

$keyword = WordpairRepository::load($link, $keyword_id);
$keyword1 = $keyword->getKeyword1();
$keyword2 = $keyword->getKeyword2();

if(isset($_POST['submit'])) {
	$keyword1 = $_POST['name1'];
	$keyword2 = $_POST['name2'];	
	if(WordpairRepository::edit($link, $keyword1, $keyword2, $keyword_id)) {
		header("location: wordpairs.php");
	}
}


include('view/header.php');
include('view/v_edit_keyword.php');
include('view/footer.php');