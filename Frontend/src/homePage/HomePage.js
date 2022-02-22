import React,{ useState, useEffect } from 'react';
import Footer from '../footer/Footer';
import Navbar from '../navbar/Navbar';
import {useNavigate,Link} from 'react-router-dom';
import BarreRech from './BarreRech/BarreRech';
import { parse } from 'json-in-order';
import back from './keyboard-key.png';
import './HomePage.css';

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
  const { search } = window.location;
  const query = new URLSearchParams(search).get("search");
  const [articles, setArticles] = useState([]);
  const [readMoreCliked, setReadMoreCliked]=useState(false);
  const [currentArticle, setCurrentArticle] = useState(null);
  const [currentUser, setCurrentUser] = useState(undefined);
  const handleClick = (idx) => e => {
    e.preventDefault();
    setReadMoreCliked(!readMoreCliked);
    setCurrentArticle(articles[idx]);
    window.scrollTo(0,0);
  }

  const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
  }
  const logout = () => {
    localStorage.removeItem("user");
    window.location.reload();
  }

  const onBackClick = () => {
    setReadMoreCliked(false);
  }
  
  const MenuItems = !currentUser ? [
      {
          title: "Login",
          url: "/form",
          onClick: null
      }
  ] : [
    {
      title: "Logout", 
      url: "/",
      onClick: logout
    }
  ];

  useEffect(() => {
    const user = getCurrentUser();
    if(user)
    {
      setCurrentUser(user);
    }
    const getArticles = async () => {
      const articlesFromServer = await fetchArticles();
      setArticles(articlesFromServer);
    }  
    getArticles();
  },[]);

  const fetchArticles = async () => {
    // NOTE (KARIM) : When the recommendation system will be implemented we will have to take into account the fact that we are logged in
    // NOTE (KARIM) : The token refresh functionnality needs to be implemented when we start using endpoints that require authentication
    const endpoint = query === null ? "http://localhost:8080/articles" : `http://localhost:8080/articles?search_query=${query}`;
    const response = await fetch(endpoint);
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
                              return(article.get("content").length !== 0 
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
                                <div className="upper-part">
                                  <img className="back-button" alt="" src={back} onClick={onBackClick}/>
                                  <h1 className="card-title fs-3 fw-bold">{currentArticle.get("title")}</h1>
                                </div>
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
                                            {/image[0-9]+/.test(entry[0]) ? <><img src={entry[1].get("url")} alt={entry[1].get("caption")} /> <figcaption>{entry[1].get("caption")}</figcaption></> : null}
                                            </>
                                          )
                                        })
                                      }
                                    </div>);
                                  })
                                }
                                <p className="card-text"><small className="text-muted">Source : <a target="_blank" rel="noreferrer" href={currentArticle.get("url")}>{currentArticle.get("url")}</a></small></p>
                              </div>
                            </div>
                       }   
                    </div>
                </section> 
           <Footer/>
        </div>;
}
