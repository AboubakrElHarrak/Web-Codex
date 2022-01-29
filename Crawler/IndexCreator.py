import os
import json
from elasticsearch import Elasticsearch

def main():
    directory = "corpus"
    es = Elasticsearch(HOST="http://localhost", PORT=9200)
    es.indices.create(index="codex_articles", ignore=400)
    for idx, filename in enumerate(os.listdir(directory)):
        with open(os.path.join(directory,filename), encoding="utf8") as file:
            doc = json.load(file)
        es.index(index="codex_articles", doc_type="articles", id=idx, body=doc)
        print(f"{filename} inserted into index")
if __name__ == "__main__":
    main()