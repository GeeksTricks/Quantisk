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
				<td><a href="edit_site.php?id=<?php echo $site_id?>"><button name="edit">Редактировать</button></a></td>	
				<td><a href="delete.php?id=<?php echo $site_id?>&name=site"><button name="delete">Удалить</button></a></td>	
			</tr>
		<?php endforeach ?>
	</table>
	<br>
	<a href="new_site.php"><button>Добавить</button> </a>
</div>