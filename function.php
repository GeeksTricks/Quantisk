<?php
function getDbConnect() {
	static $link;

	// настройки подключения к бд
	$hostname = 'localhost';
	$username = 'root';
	$password = '';

	// только одно соединение с бд
	if ($link === null) {
		$link = mysqli_connect($hostname, $username, $password); // подключаемся к БД
		mysqli_query($link, 'SET NAMES utf8');
		mysqli_set_charset($link, 'utf8');
		$db_selected = mysqli_select_db($link, 'probation'); //выбор базы
	return $link;
	}
}

function sql_escape($link, $param) {
	return mysqli_escape_string($link, $param); // экранирование переменных
}