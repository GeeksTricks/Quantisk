<?php
function getDbConnect() {
	static $link;

	// настройки подключения к бд
	$hostname = '56c6b4a92d527192b800006a-geekstriks.rhcloud.com:37321';
	$username = 'apiuser';
	$password = 'api_probation2016';

	// только одно соединение с бд
	if ($link === null) {
		$link = mysqli_connect($hostname, $username, $password); // подключаемся к БД
		mysqli_query($link, 'SET NAMES utf8');
		mysqli_set_charset($link, 'utf8');
		$db_selected = mysqli_select_db($link, 'db'); //выбор базы

	return $link;
	}
}

function sql_escape($link, $param) {
	return mysqli_escape_string($link, $param); // экранирование переменных
}