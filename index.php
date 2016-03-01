<?php
$title = "Главная страница";

include('view/header.php');
include('view/index.php');
include('view/footer.php');

// ТЕСТИРОВАНИЕ КЛАССОВ

// $all_persons = PersonRepository::loadAll($link);
// $person = PersonRepository::load($link);

// print_r($all_persons);
// print_r($person);

// $all_sites = SiteRepository::loadAll($link);
// $site = SiteRepository::load($link);

// print_r($all_sites);
// print_r($site);

// $all_ranks = RankRepository::loadAll($link);
// $one_rank = RankRepository::load($link, 4, 2);
// $start = " '2016-01-01' ";
// $end = " '2016-02-10' ";
// $period = RankRepository::loadByPeriod($link, 1, 1, $start, $end);

// print_r($all_ranks);
// print_r($one_rank);
// print_r($period);

// $all_pages = PageRepository::loadAll($link);
// $one_page = PageRepository::load($link, 4);
// $site_page = PageRepository::selectAllBySiteID($link, 5);

// print_r($all_pages);
// print_r($one_page);
// print_r($site_page);

// $wordpairs = WordpairRepository::loadAll($link);
// $pair_person = WordpairRepository::loadByPerson($link, 2);

// print_r($wordpairs);
// print_r($pair_person);

// $person_add = PersonRepository::add($link, 'J.Depp');
// $site_add = SiteRepository::add($link, 'gazeta.ru');
// $word_add = WordpairRepository::add($link, 'Медведеву');
// print_r($word_add);