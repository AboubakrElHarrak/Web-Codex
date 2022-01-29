import os
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

def main():
    opts = Options()
    opts.add_argument("--headless")

    chrome_driver = os.getcwd() +"\\chromedriver.exe"
    driver = webdriver.Chrome(options=opts,executable_path=chrome_driver)

    url = "https://www.wikiwand.com/en/Conway%27s_Game_of_Life"
    driver.get(url)
    page = driver.page_source

    soup = BeautifulSoup(page,"lxml")
    content = soup.find("div",class_="full-content-wrapper")
    sections = content.find_all("section", class_="article_content ng-scope")
    for section in sections :
        for child in section.children :
            if child.name == "h2" or child.name == "h3":
                print(child.find("span").text)
            elif child.name == "p":
                print(child.text+"\n")
            elif child.name == "div" :
                if "class" in child.attrs and "image_wrapper" in child.attrs["class"]:
                    img = child.find("div",class_="img_container").find("img")
                    caption = child.find("div",class_="caption").text
                    print("Image link : " + img.get("src")+ "\n" + "Caption: "+ caption)
            elif child.name == "ul":
                elems = child.find_all("li")
                for elem in elems:
                    print(elem.text + "\n")
            elif child.name == "ol":
                elems = child.find_all("li")
                for idx, elem in enumerate(elems):
                   print(f"{idx+1} - {elem.text}"+"\n")                        
        print("\n")
if __name__ == "__main__":
    main()