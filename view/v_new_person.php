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
		<li class="active"><a href="persons.php">Справочник личностей</a></li>
		<li><a href="wordpairs.php">Справочник ключевых слов</a></li>
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
									<h3>Название</h3>		
									<input class="form-control" type="text" name="name">
									<button class="btn btn-primary" type="submit" name="submit">Сохранить</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>