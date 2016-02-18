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
	<tr><td>Наименование</td></tr>
	<?php foreach ($all_persons as $key): ?>
		<tr>
			<td><?php echo $key->getName(); 
			$person_id = $key->getId();
			?></td>
			<form type="multipart/form-data" method="POST">
				<td><button name="edit">Редактировать</button></td>
				<td><button name="delete">Удалить</button></td>
			</form>
		</tr>
	<?php endforeach ?>
	<?php 
if(isset($_POST['edit'])) {
	header("location: edit_person.php?id=".$person_id);
}

if(isset($_POST['delete'])) {
	if(PersonRepository::delete($link, $person_id)) {
		header("location: persons.php");
	}
}
?>
</table>
<br>
<a href="new_person.php"><button>Добавить</button> </a>
</div>
