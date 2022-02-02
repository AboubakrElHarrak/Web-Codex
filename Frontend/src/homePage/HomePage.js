import React,{ useState } from 'react';
import Footer from '../footer/Footer';
import Navbar from '../navbar/Navbar';
import {useNavigate,Link} from 'react-router-dom';
import BarreRech from './BarreRech/BarreRech';

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
  let text ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet tristique tortor. Nulla congue sodales nunc ac tempor. Etiam velit sapien, rutrum egestas lacinia vel, vulputate id risus. Nulla eget elementum tellus. Maecenas eget sapien auctor, gravida purus dictum, aliquam nulla. Praesent molestie vitae urna sed elementum. Mauris in mauris accumsan, tincidunt felis nec, varius lacus. Donec magna lacus, interdum in nulla sit amet, fringilla porttitor erat. Ut nec felis quis massa porttitor suscipit. Nulla varius tincidunt orci, a dictum leo ornare sed. Interdum et malesuada fames ac ante ipsum primis in faucibus.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet tristique tortor. Nulla congue sodales nunc ac tempor. Etiam velit sapien, rutrum egestas lacinia vel, vulputate id risus. Nulla eget elementum tellus. Maecenas eget sapien auctor, gravida purus dictum, aliquam nulla. Praesent molestie vitae urna sed elementum. Mauris in mauris accumsan, tincidunt felis nec, varius lacus. Donec magna lacus, interdum in nulla sit amet, fringilla porttitor erat. Ut nec felis quis massa porttitor suscipit. Nulla varius tincidunt orci, a dictum leo ornare sed. Interdum et malesuada fames ac ante ipsum primis in faucibus..";
  const [readMoreCliked, setReadMoreCliked]=useState(true);
  function handleClick(e) {
    e.preventDefault();
    setReadMoreCliked(!readMoreCliked);
  }
    const MenuItems = [
        {
            title:'Log in',
            url: '/form',
        }
    ]
  const numbers = [1, 2, 3, 4, 5];
  return <div>
           <Navbar links={MenuItems} />
               <section className='bg-dark ' >

                   <div className='container text-center text-md-left py-5'  >
                   {readMoreCliked 
                         ? (<div><BarreRech /> 
                            {numbers.map((number) =>
                                  <div className="card mb-3 my-4">
                                      <div class="card-body">
                                          <h5 className="card-title fs-3 fw-bold">Title</h5>
                                          <p className="card-text text-start">{text.slice(0, 330)}...<a style={{color: "#0645f3",textDecoration: "none"}} className=' px-2 ' href='' onClick={handleClick}>read more</a></p>
                                          <p className="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                                      </div>
                                  </div>)}</div>)
                                       
                          : <div className="card mb-3 my-4" style={{height:'517px'}}>
                              <div class="card-body">
                                <h5 className="card-title fs-3 fw-bold">Title</h5>
                                <p className="card-text text-start">{text}</p>
                                <p className="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                              </div>
                            </div>
                       }   
                    </div>
                </section> 
           <Footer/>
        </div>;
}
