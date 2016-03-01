<h1><?php echo $title ?></h1>
<!--<div class="left">
	<ul>
		<li><a href="static-all.php"><button class="btn btn-primary">Общая статистика</button></a></li>
		<li><a href="static-day.php">Ежедневная статистика</a></li>
		<li>Справочники
			<ul>
				<li><a href="persons.php">Личности</a></li>
				<li><a href="wordpairs.php">Ключевые слова</a></li>
				<li><a href="sites.php">Сайты</a></li>
			</ul>
		</li>
	</ul>
</div>-->
<div class="container jumbotron">
	<ul class="nav nav-tabs">
		<li class="active"><a href="static-all.php">Общая статистика</a></li>
		<li><a href="static-day.php">Ежедневная статистика</a></li>
		<li><a href="persons.php">Справочник личностей</a></li>
		<li><a href="wordpairs.php">Справочник ключевых слов</a></li>
		<li><a href="sites.php">Справочник сайтов</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active">
			<div>
				<div class="row firstrow">
					<div class="col-md-5">
						<?php if($error): ?>
							<span class="error">Заполните все поля!</span>
						<?php endif?>	
						<form type="multipart/form-data" method="POST">
							<select class="form-control" name="sites">
								<option value="default">Выберите сайт</option>
								<?php foreach ($all_sites as $site): ?>
								<option value="<?php echo $site->getId() ?>"><?php echo $site->getName(); ?></option>
								<?php endforeach ?>
							</select>
							<button class="btn btn-primary" type="submit" name="submit">Применить</button>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<br>
						<?php if(isset($_POST['submit']) && !$error): ?>
							<table class="col-md-9 table-striped table-bordered">
								<tr>
									<td colspan="2"><?php echo $site_name?></td>
								</tr>
								<tr>
									<th>Личность</th>
									<th>Статистика</th>
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
				</div>
			</div>
		</div>
	</div>
</div>