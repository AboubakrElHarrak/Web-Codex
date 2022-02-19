import React,{ useState, useEffect } from 'react';
import Footer from '../footer/Footer';
import Navbar from '../navbar/Navbar';
import {useNavigate,Link} from 'react-router-dom';
import BarreRech from './BarreRech/BarreRech';
import { parse } from 'json-in-order';

const ReadMore = ({ children }) => {
    const text = children;
    const [isReadMore, setIsReadMore] = useState(true);
    let navigate=useNavigate();
    return (
      <p >
        {isReadMore ? text.slice(0, 330) : text}
        ...<a style={{color: "#0645f3",textDecoration: "none"}} className=' px-2 ' href='' onClick={()=>{navigate('/form');}}>
          read more
        </a>
      </p>
    );
  };
  
export default function HomePage() {
  const [articles, setArticles] = useState([]);
  const [readMoreCliked, setReadMoreCliked]=useState(false);
  const [currentArticle, setCurrentArticle] = useState(null);
  const handleClick = (idx) => e => {
    e.preventDefault();
    setReadMoreCliked(!readMoreCliked);
    setCurrentArticle(articles[idx]);
  }
  const MenuItems = [
      {
          title:'Log in',
          url: '/form',
      }
  ];

  useEffect(() => {
    const getArticles = async () => {
      const articlesFromServer = await fetchArticles();
      setArticles(articlesFromServer);
    }  
    getArticles();
  },[]);

  const fetchArticles = async () => {
    const response = await fetch("http://localhost:8080/articles");
    const data = await response.text();
    const arr = parse(data);
    const articles = arr.map( article => {
      return parse(article);
    });
    return articles;
  }
  if(articles.length === 0)
  {
    return <div>Articles Loading ...</div>
  }
  
  return <div>
           <Navbar links={MenuItems} />
               <section className='bg-dark ' >

                   <div className='container text-center text-md-left py-5'  >
                   {!readMoreCliked 
                         ? 
                         <div>
                            <BarreRech /> 
                            {articles.map((article, idx) => {
                              return(article.get("content").length != 0 
                              ?
                              <div key={idx} className="card mb-3 my-4">
                                <div className="card-body">
                                  <h1 className="card-title fs-3 fw-bold">{article.get("title")}</h1>
                                  <p className="card-text text-start">{article.get("content")[0].get("paragraph1")}...<a style={{color: "#0645f3",textDecoration: "none"}} className=' px-2 ' href='' onClick={handleClick(idx)}>read more</a></p>
                                  <p className="card-text"><small className="text-muted">Source : {article.get("url")}</small></p>
                                </div>
                              </div>
                              :
                              null);
                          })}
                          </div>             
                          : 
                          <div className="card mb-3 my-4">
                              <div className="card-body">
                                <h1 className="card-title fs-3 fw-bold">{currentArticle.get("title")}</h1>
                                {
                                  currentArticle.get("content").map((section, idx) => {
                                    return (
                                    <div key={idx}>
                                      {section.has("title") ? <h3 className="card-text text-start">{section.get("title")}</h3>: null}
                                      {
                                        Array.from(section.entries()).map((entry,idx) => {
                                          return (
                                            <>
                                            {/paragraph[0-9]+/.test(entry[0]) ? <p key={idx} className="card-text text-start">{entry[1]}</p> : null}
                                            {/subtitle[0-9]+/.test(entry[0]) ? <h4 className="card-text text-start">{entry[1]}</h4> : null}
                                            {/ul[0-9]+/.test(entry[0]) ? <ul className="card-text text-start">{entry[1].split("\n").map( e => {return(e !== "" ? <li>{e}</li> : null)} )}</ul> : null}
                                            {/ol[0-9]+/.test(entry[0]) ? <ol className="card-text text-start">{entry[1].split("\n").map( e => {return(e !== "" ? <li>{e}</li> : null)} )}</ol> : null}
                                            {/image[0-9]+/.test(entry[0]) ? <><img src={entry[1].get("url")}/> <figcaption>{entry[1].get("caption")}</figcaption></> : null}
                                            </>
                                          )
                                        })
                                      }
                                    </div>);
                                  })
                                }
                                <p className="card-text"><small className="text-muted">Source : <a target="_blank" href={currentArticle.get("url")}>{currentArticle.get("url")}</a></small></p>
                              </div>
                            </div>
                       }   
                    </div>
                </section> 
           <Footer/>
        </div>;
}
