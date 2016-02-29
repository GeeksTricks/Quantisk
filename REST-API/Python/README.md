Задеплоено тут: https://api-quantisk.rhcloud.com/

Просит basic http авторизацию. (Потестить можно с логином 'user1' и паролем 'qwerty1').

Например https://user1:qwerty1@api-quantisk.rhcloud.com/v1/persons/

Примеры запросов:

* http://api-quantisk.rhcloud.com/v1/dailyrank/?site_id=1&person_id=&start_date=2016-02-01&end_date=2016-02-11
* http://api-quantisk.rhcloud.com/v1/totalrank/1/
* http://api-quantisk.rhcloud.com/v1/wordpairs/
* http://api-quantisk.rhcloud.com/v1/persons/
* http://api-quantisk.rhcloud.com/v1/persons/6/


###Веб-сервис поддерживает следующие вызовы:

Все запросы принимают и возвращают данные в формате JSON.

1. /v1/persons/

	GET: Возвращает список личностей, для которых производится поиск ключевых слов.
	Возвращает массив:

		[
			{
				"id": integer,
				"name": string
			}
		]

	POST: Добавляет в список новую личность.
	Принимает:

		{ 
			"name": string 
		}

	Возвращает объект созданной личности.

2. /v1/persons/id/

	GET: Получение объекта личности с заданным ID.
	Возвращает:

		{
			"id": integer,
			"name": string
		}

	PUT: Изменение личности с заданным ID.
	Принимает:

		{
			"id": integer,
			"name": string
		}

	Возвращает измененный объект личности.

	DELETE: Удаление объекта личности с заданным ID.

	Возвращает:
		204

3. /v1/wordpairs/

	GET: Возвращает список пар ключевых слов, соответствующих каждой личности и расстояние между ними.
	Возвращает:

		[
			{
				"id": integer,
				"keyword1": string,
				"keyword2": string,
				"distance": integer,
				"person_id": integer
			}
		]

	POST: Добавляет в список новую пару ключевых слов и расстояние.
	Принимает:

		{
			"keyword1": string,
			"keyword2": string,
			"distance": integer,
			"person_id": integer
		}

	Возвращает объект новой пары слов.

4. /v1/wordpairs/id/

	GET: Возвращает объект пары ключевых слов с заданным ID.
	Возвращает:

		{
			id:	integer
			keyword1:string
			keyword2:string
			distance:integer
			person_id:integer
		}

	PUT: Изменение пары ключевых слов.
	Принимает:

		{
			id:	integer
			keyword1:string
			keyword2:string
			distance:integer
		}

	Возвращает измененный объект пары ключевых слов с заданным ID.

	DELETE: Удаление пары ключевых слов с заданным ID.

	Возвращает:
		204

5. /v1/persons/person_id/wordpairs/
	
	GET: Возвращает список пар ключевых слов для конкретной личности.

	Возвращает:
		
		[ 
			{
				"id": integer,
				"keyword1": string,
				"keyword2": string,
				"distance": integer,
				"person_id": integer
			}
		]

    POST: Добавляет для личности с ID person_id новую пару ключевых слов.

    Принимает:

	    {
			"keyword1": string,
			"keyword2": string,
			"distance": integer
		}

	Возвращает объект новой пары слов.

6. /v1/totalrank/site_id/

    GET: Возвращает статистику за все время для конкретного сайта.

	Возвращает:

		[
			{	
		        "rate": integer,
		        "person_id": integer,
		        "site_id": integer
			}
		]

7. /v1/dailyrank/?person_id=person_id&site_id=site_id&start_date=start_date&end_date=end_date

    GET: Возвращает статистику по дням для конкретной личности на конкретном сайте в промежутке между двумя датами.

    Принимает аргументы в урле. Даты ожидаются в ISO-формате без таймзоны (например: 2005-08-09T18:31:42).

	Возвращает:

		[
			{
				"rank": integer,
				"day": string,
				"site_id": integer,
				"person_id": integer
			}
		]

8. /v1/sites/ 

	GET: Список названий сайтов для анализа на упоминания.

	Возвращает:

		[
			{
				"id": integer,
				"name": string
			}
		]

	POST: Добавление нового сайта в список.

	Принимает:

		{
			"name": string
		}

	Возвращает новый объект сайта.

9. /v1/sites/id/

	GET: Возвращает сайт с заданным ID.

	Возвращает:

		{
			"id": integer,
			"name": string
		}

	PUT: Изменение сайта с заданным ID.

	Принимает:

		{ 
			"name": string
		}

	Возвращает измененный объект сайта.

	DELETE: Удаление сайта с заданным ID.

	Возвращает:
		204

10. /v1/users/

	GET: Список пользователей.

	Возвращает:

		[
			{
				"id": integer,
				"login": string
			}
		]

	POST: Добавление нового пользователя в список.

	Принимает:

		{
			"login": string,
			"password": string
		}

	Возвращает новый объект пользователя.

11. /v1/users/id/

	GET: Возвращает пользователя с заданным ID.

	Возвращает:

		{
			"id": integer,
			"login": string
		}

	PUT: Изменение пользователя с заданным ID.

	Принимает:

		{
			"login":string
		}

	Возвращает измененный объект пользователя.

	DELETE: Удаление пользователя с заданным ID.

	Возвращает:
		204