import time
import re
import ssl
from urllib.parse import urlparse
from urllib.request import urlopen

from bs4 import BeautifulSoup
from WikipediaPage import WikipediaPage

class WikipediaCrawler:
    def __init__(self, directory, max_depth, driver):
        self.wiki_page_link_pattern = re.compile("/wiki/[\w]+$")
        self.category_link_pattern = re.compile("/wiki/Category:[\w]+$")

        self.driver = driver
        self.ssl_context = ssl.create_default_context()
        self.max_depth = max_depth
        self.store_after_parsing = True
        self.directory = directory
        self.crawled_pages = set()
        self.valid_origins = ["https://wikiwand.com/en", "https://en.wikipedia.org"]

    def register_page(self, url):
        if not url in self.crawled_pages:
            return True
        else:
            return False
    
    def download_page(self, url):
        try:
            if self.register_page(url):
                print("Downloading page:", url)
                self.driver.get(self.valid_origins[0]+"/"+url.split("/")[-1])
                time.sleep(3)
                return (urlopen(url, context=self.ssl_context).read().decode("utf-8"), self.driver.page_source)
        except Exception as ex :
            print("Failed to download page", ex)
        return None
    
    def parse_category(self, url, depth):
        page_content = self.download_page(url)
        
        if page_content is None:
            return []
        
        base_url = "{uri.scheme}://{uri.netloc}".format(uri=urlparse(url))
        soup = BeautifulSoup(page_content, "lxml")

        pages = []
        links = list(filter(lambda x: x.get("href") is not None, soup.find_all('a')))
        for link in links :
            url = link.get("href")
            pages.extend(self.crawl(base_url + url, depth + 1))
        
        return pages
    
    def parse_page(self, url, depth = 0):
        print("Parsing page: ", url)
        page_content = self.download_page(url)
        if page_content is None:
            return []
        
        wikipedia_soup = BeautifulSoup(page_content[0], "lxml")
        wikiwand_soup = BeautifulSoup(page_content[1], "lxml")
        pages = []

        page = WikipediaPage(url)

        #extract wikipedia links from wikipedia page
        links = wikipedia_soup.find_all('a')
        for link in links :
            link_url = link.get("href")
            if link_url is not None:
                if self.wiki_page_link_pattern.match(link_url):
                    base_url = "{uri.scheme}://{uri.netloc}".format(uri=urlparse(url))
                    page.links.append(base_url + link_url)
                    pages.extend(self.crawl(base_url + link_url, depth + 1))
        # extract content from wikiwand page
        content = wikiwand_soup.find("div",class_="full-content-wrapper")
        sections = content.find_all("section", class_="article_content ng-scope")
        current_section = {}
        for section in sections :
            subsection_count = 0
            paragraph_count = 0
            ul_count = 0
            ol_count = 0
            image_count = 0
            for child in section.children :
                if child.name == "h2":
                    current_section["title"] = child.find("span").text
                elif child.name == "h3":
                    subsection_count += 1
                    current_section[f"subtitle{subsection_count}"] = child.find("span").text
                elif child.name == "p" and child.text != "\n":
                    paragraph_count += 1
                    txt = re.sub(r"\[(\d+|\w+)\]","",child.text)
                    current_section[f"paragraph{paragraph_count}"] = txt
                elif child.name == "div" :
                    image_count += 1
                    image = {}
                    if "class" in child.attrs and "image_wrapper" in child.attrs["class"]:
                        image["url"] = child.find("div",class_="img_container").find("img").get("src")
                        image["caption"] = child.find("div",class_="caption").text
                        current_section[f"image{image_count}"] = image
                elif child.name == "ul":
                    ul_count += 1
                    elems = child.find_all("li")
                    ul = ""
                    for elem in elems:
                        ul += elem.text + "\n"
                    current_section[f"ul{ul_count}"] = ul
                elif child.name == "ol":
                    ol_count += 1
                    elems = child.find_all("li")
                    ol = ""
                    for idx, elem in enumerate(elems):
                        ol += f"{idx+1} - {elem.text}"+"\n"
                    current_section[f"ol{ol_count}"] = ol
            page.content.append(current_section)
            current_section = {}
                        
        page.content = list(filter(lambda x: x != {} , page.content))
        # extract table of content and title from wikipedia page
        toc_element = wikipedia_soup.find(id="toc")
        if toc_element is not None:
            page.table_of_contents = list(filter(lambda x: x!="", toc_element.text.split("\n")[1:]))
        
        page.title = wikipedia_soup.find(id="firstHeading").text

        # store the page in a json file
        if self.store_after_parsing:
            page.store(self.directory)
        pages.append(page)
        return pages
    
    def crawl(self, initial_link, depth = 0):
        if depth <= self.max_depth:
           base_url = "{uri.scheme}://{uri.netloc}".format(uri=urlparse(initial_link))
           if base_url in self.valid_origins:
                if self.category_link_pattern.match(initial_link[len(base_url):]):
                   return self.parse_category(initial_link, depth)
                elif self.wiki_page_link_pattern.match(initial_link[len(base_url):]):
                    return self.parse_page(initial_link, depth)
        return []
