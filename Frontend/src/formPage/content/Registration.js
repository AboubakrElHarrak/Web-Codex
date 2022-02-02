import React from 'react';
import './Form.css'
function Registration() {
const [loginCliked,setLoginCliked]=useState(true);
  
  function handleClick(e) {
    e.preventDefault();
    setRegisterCliked(!registerCliked);
    setLoginCliked(!loginCliked);
  }
  return <div>
      <h2>Registration</h2>
            <form>
                <input type="text1" name="field1" placeholder="Name" />
                <input type="email" name="field2" placeholder="Email" />
                <input type="password" name="field3" placeholder="password" />
                <input type="password" name="field4" placeholder="Confirm password" />
                <input type="text1" name="field5" placeholder="Your center of interest (sport, sience ..." />
                <input type="button" value="Register" />
                <div className="text">
                <h4>Already have an account? <a style={{color: "#0645f3",textDecoration: "none"}} href='' onClick={handleClick}> Login now</a></h4>
                </div>
            </form>
  </div>;
}

export default Registration;
