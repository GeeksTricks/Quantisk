<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Справочник: Личности";

$all_persons = PersonRepository::loadAll($link);

include('view/header.php');
include('view/v_persons.php');
include('view/footer.php');