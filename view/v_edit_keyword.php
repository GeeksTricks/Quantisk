<h1><?php echo $title ?></h1>
<div class="left">
	<ul>
		<li><a href="static-all.php">Общая статистика</a></li>
		<li><a href="static-day.php">Ежедневная статистика</a></li>
		<li>Справочники
			<ul>
				<li><a href="persons.php">Личности</a></li>
				<li><a href="wordpairs.php">Ключевые слова</a></li>
				<li><a href="sites.php">Сайты</a></li>
			</ul>
		</li>
	</ul>
</div>
<div>
	<h3>Название</h3>
	<form type="multipart/form-data" method="POST">
		<h3>Keyword1</h3>
		<input type="text" value="<?php echo $keyword1 ?>" name="name1">
		<h3>Keyword2</h3>
		<input type="text" value="<?php echo $keyword2 ?>" name="name2">
		<h3>Distance</h3>
		<input type="text" value="<?php echo $distance ?>" name="distance">
		<br><br>
		<button type="submit" name="submit">Сохранить</button>
	</form>
</div>