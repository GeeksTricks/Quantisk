<h1><?php echo $title ?></h1>
<!-- <div class="left">
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
</div> -->
<div class="container jumbotron">
	<ul class="nav nav-tabs">
		<li><a href="static-all.php">Общая статистика</a></li>
		<li><a href="static-day.php">Ежедневная статистика</a></li>
		<li><a href="persons.php">Справочник личностей</a></li>
		<li><a href="wordpairs.php">Справочник ключевых слов</a></li>
		<li class="active"><a href="sites.php">Справочник сайтов</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active">
			<div>
				<div class="row firstrow">
					<div class="col-md-5">
						<div>
							<div class="row">
								<table class="col-md-9 table-striped table-bordered">
									<tr><th>Наименование</th></tr>
									<?php foreach ($all_sites as $site): ?>
										<tr>
											<td>
												<?php echo $site->getName(); 
												$site_id = $site->getId();
												?>
											</td>
											<td><a href="edit_site.php?id=<?php echo $site_id?>"><button class="btn btn-warning btn-sm" name="edit">Редактировать</button></a></td>	
											<td><a href="delete.php?id=<?php echo $site_id?>&name=site"><button class="btn btn-danger btn-sm" name="delete">Удалить</button></a></td>	
										</tr>
									<?php endforeach ?>
								</table>
							</div>
							<div class="row">
								<a href="new_site.php"><button class="btn btn-success btn-sm">Добавить</button> </a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>														