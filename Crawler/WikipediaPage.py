import json

class WikipediaPage :
    def __init__(self, url):        
        self.url = url
        self.title = []
        self.table_of_contents = []
        self.content = []
        self.links = []
    
    def store(self, directory):
        file_name = self.title.replace(" ","_")
        with open(directory + "/" + file_name + ".json", "w", encoding="utf-8") as file:
            fields = self.__dict__ 
            json.dump(fields, file, indent=4, ensure_ascii=False)

def extract_wiki_page(url):
    return url.split("/")[-1]
