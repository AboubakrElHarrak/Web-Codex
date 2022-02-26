import React, { useState, useEffect } from 'react';
import './Ad_sidebar.css';
import Articles_manage from './sb_components/Articles_manage';
import Comments_manage from './sb_components/Comments_manage';
import {useNavigate,Link} from 'react-router-dom';
import Table_user from './sb_components/Table_user';
function Ad_sidebar() {
    let navigate=useNavigate();
    var espace;
    const [element, setElement] = useState("users_manage");
    function handleUsersClick(e){
        e.preventDefault();
        setElement("users_manage");
    }
    function handleComtsClick(e){
        e.preventDefault();
        setElement("cmnts_manage");
    }
    function handleArticlesClick(e){
        e.preventDefault();
        setElement("artcls_manage");
    }
    if (element == "users_manage") {
        espace = <Table_user/>;
      } else {
        espace = <Comments_manage/>;
      }
    return (
        <div>
            <div
            class="offcanvas offcanvas-start sidebar-nav bg-dark"
            tabindex="-1"
            id="sidebar"
            >
                    <div class="offcanvas-body py-5">
                        <nav class="navbar-dark">
                        <ul class="navbar-nav">
                            <li>
                            <div class="text-muted small fw-bold text-uppercase px-3 mb-3">
                                Pages
                            </div>
                            </li>
                            <li >
                            <a  href='' onClick={()=>{navigate('/');}}  class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-book-fill"></i></span>
                                <span >Home</span>
                            </a>
                            </li>
                            <li> 
                            <a href='' onClick={()=>{navigate('/form');}}  class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-book-fill"></i></span>
                                <span>Sign up</span>
                            </a>
                            </li>
                            <li> 
                            <a href="" onClick={()=>{navigate('/dash');}} class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-book-fill"></i></span>
                                <span>Admin</span>
                            </a>
                            </li>
                            <li class="my-4"><hr class="dropdown-divider bg-light" /></li>
                            <li>
                            <div class="text-muted small fw-bold text-uppercase px-3 mb-3">
                                Actions
                            </div>
                            </li>
                            <li>
                            <a href="#" onClick={handleUsersClick} class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-graph-up"></i></span>
                                <span>Users_manage</span>
                            </a>
                            </li>
                            <li>
                            <a href="#" onClick={handleComtsClick} class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-graph-up"></i></span>
                                <span>Comments_manage</span>
                            </a>
                            </li>
                            <li>
                            <a href="#" onClick={handleArticlesClick} class="nav-link px-3 active">
                                <span class="me-2"><i class="bi bi-graph-up"></i></span>
                                <span>Articles_manage</span>
                            </a>
                            </li>
                        </ul>
                        </nav>
                    </div>
            </div>
            <div>
                {espace}
            </div>
        </div>
  )
}

export default Ad_sidebar