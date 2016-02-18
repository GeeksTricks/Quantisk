<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Ежедневная статистика";

$all_sites = SiteRepository::loadAll($link);
$all_persons = PersonRepository::loadAll($link);

include('view/header.php');
include('view/v_static-day.php');
include('view/footer.php');