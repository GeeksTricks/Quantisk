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
	<form type="multipart/form-data" method="POST">
		<select name="sites">
			<option value="default">Выберите сайт</option>
			<?php foreach ($all_sites as $site): ?>
			<option value="<?php echo $site->getId() ?>"><?php echo $site->getName(); ?></option>
			<?php endforeach ?>
		</select>
		<button type="submit" name="submit">Применить</button>
	</form>
	<br>
	<?php if(isset($_POST['submit'])): ?>
		<table>
			<tr>
				<td colspan="2"><?php echo $site_name?></td>
			</tr>
			<tr>
				<td>Личность</td>
				<td>Статистика</td>
			</tr>			
			<?php foreach ($all_persons as $person): 
				 $person_id = $person->getId(); ?>
				<tr>
					<td><?php echo $person->getName(); ?></td>
					<?php foreach ($pids as $page):
					 	$rank = RankRepository::load($link, $page, $person_id); 
					 	$ranks[$page] = $rank->getRank();
					 	$count = array_sum($ranks); ?>
					 <?php endforeach ?>
					<td>
						<?php print_r($count); ?>				
					</td>
				</tr>
			<?php endforeach ?>
		</table>
	<?php endif ?>
</div>