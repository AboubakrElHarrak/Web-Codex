import React from 'react';
import './Form.css'
function Singin({state}) {
  return <div>
       <h2>Login to your account</h2>
        <form>
            <input type="email" name="field2" placeholder="Email" />
            <input type="password" name="field3" placeholder="password" />
            <input type="button" value="Login" />
            <div className="text">
                <h4>You don't have an account? <a style={{color: "#0645f3",textDecoration: "none"}} href='' onClick={handleClick}> Register</a></h4>
            </div>
        </form>
  </div>;
}

export default Singin;
