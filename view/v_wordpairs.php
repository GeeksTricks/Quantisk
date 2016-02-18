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
		<select name="persons">
			<option value="default">Выберите личность</option>
			<?php foreach ($all_persons as $person): ?>
			<option value="<?php echo $person->getId() ?>"><?php echo $person->getName(); ?></option>
			<?php endforeach ?>
		</select>
		<button type="submit" name="submit">Применить</button>
	</form>

	<?php if(isset($_POST['submit'])):?>
	<table>
		<tr>
			<td colspan="4"><?php echo $one_person ?></td>
		</tr>
		<tr>
			<td>Keyword1</td>
			<td>Keyword2</td>
			<td>Distance</td>
			<td colspan="2"></td>
		</tr>
		<?php foreach ($pair_person as $key): 
			$keyword_id = $key->getId();?>
			<tr>
				<td><?php echo $key->getKeyword1(); ?></td>
				<td><?php echo $key->getKeyword2(); ?></td>	
				<td><?php echo $key->getDistance(); ?></td>
				<td><a href="edit_keyword.php?id=<?php echo $keyword_id?>"><button name="edit">Редактировать</button></a></td>					
				<td><a href="delete.php?id=<?php echo $keyword_id?>&name=keyword"><button name="delete">Удалить</button></a></td>			
			</tr>
		<?php endforeach ?>
	</table>
	
	<p><a href="new_keyword.php?id=<?php echo $_POST['persons'] ?>"><button>Добавить</button></a></p>
	<?php endif ?>
</div>