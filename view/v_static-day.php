<h1><?php echo $title ?></h1>
<!--<div class="left">
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
</div>-->
<div class="container jumbotron">
	<ul class="nav nav-tabs">
		<li><a href="static-all.php">Общая статистика</a></li>
		<li class="active"><a href="static-day.php">Ежедневная статистика</a></li>
		<li><a href="persons.php">Справочник личностей</a></li>
		<li><a href="wordpairs.php">Справочник ключевых слов</a></li>
		<li><a href="sites.php">Справочник сайтов</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active">
			<div>
				<div class="row firstrow">
					<div class="col-md-5">
						<div>
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
							<br>
							<br>
								<select class="form-control" name="persons">
									<option value="default">Выберите личность</option>
									<?php foreach ($all_persons as $person): ?>
									<option value="<?php echo $person->getId() ?>"><?php echo $person->getName(); ?></option>
									<?php endforeach ?>
								</select>	
							<br>
							<br>
								Период с <input class="form-control" type="date" name="start"> до <input class="form-control" type="date" name="end"><br><br>
								<button class="btn btn-primary" type="submit" name="submit">Применить</button>
							</form>
							<br>
							<?php if(isset($_POST['submit']) && !$error):?>
							<table class="col-md-9 table-striped table-bordered">
								<tr>
									<td colspan="2">Сайт: <?php echo $site_name ?></td>
								</tr>
								<tr>
									<td colspan="2">Личность: <?php echo $person_name ?></td>
								</tr>
								<tr>
									<th>Дата</th>
									<th>Статистика</th>
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
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
			