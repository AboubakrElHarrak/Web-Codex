import React from 'react'
import {useNavigate,Link} from 'react-router-dom';
function Ad_navbar() {
  let navigate=useNavigate();
  return (
    <div >
        <nav className="navbar navbar-light bg-dark ">
                  <a className="navbar-brand fs-5 fw-bold text-white" href='' onClick={()=>{navigate('/');}} style={{paddingLeft:"29px"}} >CODEX ADMIN</a>
        </nav>
    </div>
  )
}

export default Ad_navbar


