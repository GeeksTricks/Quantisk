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
		<li class="active"><a href="wordpairs.php">Справочник ключевых слов</a></li>
		<li><a href="sites.php">Справочник сайтов</a></li>
	</ul>

	<div class="tab-content">
		<div class="tab-pane active">
			<div>
				<div class="row firstrow">
					<div class="col-md-5">
						<div>
							<div>
								<?php if($error): ?>
									<span class="error">Заполните все поля!</span>
								<?php endif?>	
								<form type="multipart/form-data" method="POST">
									<select class="form-control" name="persons">
										<option value="default">Выберите личность</option>
										<?php foreach ($all_persons as $person): ?>
										<option value="<?php echo $person->getId() ?>"><?php echo $person->getName(); ?></option>
										<?php endforeach ?>
									</select>
									<button class="btn btn-primary" type="submit" name="submit">Применить</button>
								</form>

								<?php if(isset($_POST['submit']) && !$error):?>
								<table>
									<tr>
										<th colspan="4"><?php echo $one_person ?></th>
									</tr>
									<tr>
										<th>Keyword1</th>
										<th>Keyword2</th>
										<th>Distance</th>
										<td colspan="2"></td>
									</tr>
									<?php foreach ($pair_person as $key): 
										$keyword_id = $key->getId();?>
										<tr>
											<td><?php echo $key->getKeyword1(); ?></td>
											<td><?php echo $key->getKeyword2(); ?></td>	
											<td><?php echo $key->getDistance(); ?></td>
											<td><a href="edit_keyword.php?id=<?php echo $keyword_id?>"><button class="btn btn-warning btn-sm" name="edit">Редактировать</button></a></td>					
											<td><a href="delete.php?id=<?php echo $keyword_id?>&name=keyword"><button  class="btn btn-danger btn-sm" name="delete">Удалить</button></a></td>			
										</tr>
									<?php endforeach ?>
								</table>
								
								<p><a href="new_keyword.php?id=<?php echo $_POST['persons'] ?>"><button class="btn btn-primary btn-sm">Добавить</button></a></p>
								<?php endif ?>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>