import url_graber
import downloader
import crawl_parser
from repo import SitesRepo
from repo import PageRepo
from repo import WordpairsRepo
from repo import Repo
import time
import os

page_parser = crawl_parser.HtmlParser()
url_handler = url_graber.SitemapGruber()
download = downloader.Downloader()
sites = SitesRepo()
pages = PageRepo()
wordpairs = WordpairsRepo()
base_repo = Repo()


def main():
    os.chdir(r'/tmp')
    while True:
        site_tuple = sites.get_all()
        for site in site_tuple:
            url_list = url_handler.get_url_from_sitemap(site.name)
            pages.add_url(url_list, site.id)

        url_tuple_list = pages.get_not_scan_urls()
        query_tuple_list = wordpairs.get_query()

        for url_tuple in url_tuple_list:
            page_id = url_tuple.id
            file = download.download_file(url_tuple.url)
            print(url_tuple.url)
            if file:
                pages.add_last_scan_date(page_id)
                page_parser.parse_html(file, query_tuple_list, url_tuple.id)
                os.remove(file)
            else:
                print('нескачалось(((')
                pass

        time.sleep(43200)


if __name__ == "__main__":
    main()
