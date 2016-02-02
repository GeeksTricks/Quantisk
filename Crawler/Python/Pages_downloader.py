import urllib.request


# url = 'http://www.livejournal.com/abuse/policy.bml'
# html = urllib.request.urlopen(url).read()
# f = open('policy.html', 'w')
# f.write(str(html))


class Pages_downloader:

    def __init__(self, link, filename):

        self.link = link
        self.filename = filename

    def link_download(self):

        link_read = urllib.request.urlopen(self.link).read()
        f = open(self.filename, 'w')
        f.write(str(link_read))

'''

Для теста:
test_link_1 = 'http://www.livejournal.com/abuse/policy.bml'
Pages_downloader(test_link_1, 'test.html').link_download()

TODO:

1. разобраться с кодировкой вывода страницы.
2. Добавить скачку списка ссылок
3. добавить сохранение в отдельные файлы каждой ссылки
4. Каждому файлу генерировать название исходя из названия ссылки




'''

