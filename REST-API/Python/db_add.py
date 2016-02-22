from quantisk_api.repositories import rank_repo, page_repo, site_repo, wordpair_repo, person_repo, user_repo
import dateutil.parser as dp


user_repo.add('admin@example.com', 'qwerty')

for i in range(1, 11):
    person = person_repo.add('персона' + str(i))
    for j in range(10):
        wordpair_repo.add('word' + str(j), 'fff' + str(j), 2, person.id)

site = site_repo.add('livejournal.com')

for i in range(1, 11):
    for j in range(1, 11):
        page_repo.add(
            '/posts/' + str(i) + str(j),
            site.id,
            dp.parse('2016-02-{0:02d}T{1:02d}:55:33+03:00'.format(i, j)),
            dp.parse('2016-02-{0:02d}T{1:02d}:55:33'.format(i, j)),
        )

for i in range(1, 51):
    rank_repo.add_rank(1, page_id=i, person_id=1)
for i in range(51, 101):
    rank_repo.add_rank(1, page_id=i, person_id=2)
for i in range(1, 101):
    rank_repo.add_rank(1, page_id=i, person_id=3)
for i in range(30, 70):
    rank_repo.add_rank(2, page_id=i, person_id=4)
