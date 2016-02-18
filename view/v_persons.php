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
	<table>
			<tr>
				<td colspan="3">Наименование</td>
			</tr>
		<?php foreach ($all_persons as $key): ?>
			<tr>
				<td><?php echo $key->getName(); 
				$person_id = $key->getId();	?></td>
				<td><a href="edit_person.php?id=<?php echo $person_id?>"><button name="edit">Редактировать</button></a></td>	
				<td><a href="delete.php?id=<?php echo $person_id?>&name=person"><button name="delete">Удалить</button></a></td>			
			</tr>
		<?php endforeach ?>
	</table>
	<br>
	<a href="new_person.php"><button>Добавить</button> </a>
</div>
