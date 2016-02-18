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
	<?php foreach ($all_sites as $site): ?>
		<tr>
			<td>
				<?php echo $site->getName(); 
				$site_id = $site->getId();
				?>
			</td>
			<form type="multipart/form-data" method="POST">
			<td><button name="edit">Редактировать</button></td>
			<td><button name="delete">Удалить</button></td>
			</form>
		</tr>
	<?php endforeach ?>

	<?php

if(isset($_POST['edit'])) {
	header("location: edit_site.php?id=".$site_id);
}

if(isset($_POST['delete'])) {
	if(SiteRepository::delete($link, $site_id)) {
		header("location: sites.php");
	}
}
	?>
</table>
<br>
<a href="new_site.php"><button>Добавить</button> </a>

</div>