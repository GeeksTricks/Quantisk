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
		/*if(!$db_selected) { 			// если базы нет				  
			mysqli_query($link, 'CREATE DATABASE db_kiosk'); // то создать такую базу
			mysqli_query($link, 'ALTER DATABASE `db_kiosk` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci'); // обновление базы в нужной кодировке
			$db_selected = mysqli_select_db($link, 'db_kiosk'); 
			db_edition($link);
			db_magazine($link);
			db_users($link);
			} */

	return $link;
	}
}

function sql_escape($link, $param) {
	return mysqli_escape_string($link, $param); // экранирование переменных
}