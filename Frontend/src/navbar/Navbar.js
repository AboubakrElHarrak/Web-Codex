import React from 'react';
import {Link} from 'react-router-dom';
export default function Navbar(props) {
  return <nav className="navbar navbar-expand-md  navbar-dark bg-dark text-white ">
            <div className="container">
                <a className="navbar-brand fs-1 fw-bold" href='' onClick={props.mainRate}>CODEX</a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse " id="navbarSupportedContent" >
                    <ul className="navbar-nav ms-auto ">
                    {props.links.map((item, idx) =>{
                       return(
                           <li key={idx} className="nav-item" onClick={item.onClick} ><Link className="nav-link fs-3 fw-bolder text-md-start" to={item.url} >{item.title}</Link></li>
                       )
                    })}
                    </ul>  
                </div>
            </div>
         </nav>;
}
