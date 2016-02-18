<?php
require_once('function.php');
require('classes.php');
require('repo.php');

$link = getDbConnect();
$title = "Справочник: Сайты";

$all_sites = SiteRepository::loadAll($link);

include('view/header.php');
include('view/v_sites.php');
include('view/footer.php');