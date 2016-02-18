<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$keyword_id = $_GET['id'];

	if(WordpairRepository::delete($link, $keyword_id)) {
		header("location: wordpairs.php");
	} 