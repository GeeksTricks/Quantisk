<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$id = $_GET['id'];
$param = $_GET['name'];

switch ($param) {
	case 'site':
		if(SiteRepository::delete($link, $id)) {
			header("location: sites.php");
		}
		break;

	case 'keyword':
		if(WordpairRepository::delete($link, $id)) {
			header("location: wordpairs.php");
		}
		break;

	case 'person':
		if(PersonRepository::delete($link, $id)) {
			header("location: persons.php");
		}
		break;
	
	default:
		header("location: index.php");
}






