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
	<?php if($error): ?>
		<span class="error">Заполните все поля!</span>
	<?php endif?>
	<form type="multipart/form-data" method="POST">
		<select name="sites">
			<option value="default">Выберите сайт</option>
			<?php foreach ($all_sites as $site): ?>
			<option value="<?php echo $site->getId() ?>"><?php echo $site->getName(); ?></option>
			<?php endforeach ?>
		</select>
	<br>
	<br>
		<select name="persons">
			<option value="default">Выберите личность</option>
			<?php foreach ($all_persons as $person): ?>
			<option value="<?php echo $person->getId() ?>"><?php echo $person->getName(); ?></option>
			<?php endforeach ?>
		</select>	
	<br>
	<br>
		Период с <input type="date" name="start"> до <input type="date" name="end"><br><br>
		<button type="submit" name="submit">Применить</button>
	</form>
	<br>
	<?php if(isset($_POST['submit']) && !$error):?>
	<table>
		<tr>
			<td colspan="2">Сайт: <?php echo $site_name ?></td>
		</tr>
		<tr>
			<td colspan="2">Личность: <?php echo $person_name ?></td>
		</tr>
		<tr>
			<td>Дата</td>
			<td>Статистика</td>
		</tr>		
		<?php foreach($period as $key => $value): 
				$key = substr($key, 0, 10);
				$date = explode('-', $key);
				$date = array_reverse($date);
				$date = implode('.', $date);
				 ?>
						<tr>
							<td>
								<?php echo $date; ?>
							</td>
							<td>
								<?php echo $value; ?>
							</td>
						</tr>
				<?php endforeach ?>
		<?php endif ?>
	</table>
</div>