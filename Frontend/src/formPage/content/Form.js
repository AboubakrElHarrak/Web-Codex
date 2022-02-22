import React,{useState} from 'react';
import {useNavigate,Link} from 'react-router-dom';
import './Form.css'

export default function Form() {
  const navigate = useNavigate();
  const [registerCliked,setRegisterCliked]=useState(false);
  const [loginCliked,setLoginCliked]=useState(true);
  
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");
  const [username, setUsername] = useState("");
  const [dob, setDob] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [submitted, setSubmitted] = useState(false);
  const [error, setError] = useState([false,-1]);
  const [errorMsg, setErrorMsg] = useState("");

  const handleFirstname = e => {
    setFirstname(e.target.value);
    setSubmitted(false);
  }
  const handleLastname = e => {
    setLastname(e.target.value);
    setSubmitted(false);
  }
  const handleUsername = e => {
    setUsername(e.target.value);
    setSubmitted(false);
  }
  const handleDob = e => {
    let date = e.target.value.split('-');
    setDob(date[2] + "-" + date[1] + "-" + date[0]);
    setSubmitted(false);
  }
  const handleEmail = e => {
    setEmail(e.target.value);
    setSubmitted(false);
  }
  const handlePassword = e => {
    setPassword(e.target.value);
    setSubmitted(false);
  }
  const handleSubmit = e => {
    e.preventDefault();
    if(firstname === "" || lastname === "" || username === "" || dob === "" || email === "" || password === "")
    {
      setErrorMsg("Please enter all fields");
      setError([true, 0]);
    }
    else if(new Date() - Date.parse(dob) < 4.1e+11)
    {
      setErrorMsg("You must be at least be 13 to register in the CODEX");
      setError([true, 3]);
    }
    else if(!/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(email))
    {
      setErrorMsg("Email address must be valid");
      setError([true, 2]);
    }
    else if(!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,}$/.test(password))
    {
      setErrorMsg(<div>
        Your password needs to :
        <ul>
          <li> include both lower and upper case characters </li>
          <li> include at least one number and special character </li>
          <li> be at least 8 characters long </li>
        </ul></div>);
      setError([true, 1]);
    }
    else if(password.length < 8)
    {
      setErrorMsg("Password must contain at least 8 characters")
      setError([true, 1]);
    }
    else if(password === username && password !== "")
    {
      setErrorMsg("Password must be different from username")
      setError([true, 1]);
    }
    
    else
    {
      setError([false, -1]);
      register(firstname, lastname, username, dob, email, password);  
    }
  }
  const register = async (firstname, lastname, username, dob, email, password) => {
    const response = await fetch("http://localhost:8080/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      },
      body: JSON.stringify({
        "firstname": firstname,
        "lastname": lastname,
        "username": username,
        "email": email,
        "password": password,
        "birthday": dob
      })
    });

    const data = await response.text();
    if(data !== "Success")
    {
      let msg = JSON.parse(data)["message"];
      setErrorMsg(msg.substring(17,msg.indexOf("for key '") - 2) + " is already used")
      setError([true, 0]);
    }
    else
    {
      setSubmitted(true);
    }
  }

  const handleLogin = e => {
    e.preventDefault();
    if(username === "" || password === "")
    {
      setErrorMsg("Please enter all fields");
      setError([true, 0]);
    }
    else
    {
      setError([false, -1]);
      login(username, password).then(() => {
        navigate("/");
        window.location.reload();
      });
    }
  }
  
  const login = async (username, password) => {
    const urlencoded = new URLSearchParams();
    urlencoded.append("username", username);
    urlencoded.append("password",password);

    const response = await fetch("http://localhost:8080/login",{
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: urlencoded,
      redirect: "follow"
    });
    if(response.ok)
    {
      const data = await response.json();
      localStorage.setItem("user", JSON.stringify(data));
    }
    else
    {
      setErrorMsg("Incorrect username or password");
      setError([true, 0]);
    }
  }

  function handleClick(e) {
    e.preventDefault();
    setFirstname("");
    setLastname("");
    setUsername("");
    setDob("");
    setEmail("");
    setPassword("");
    setSubmitted(false);
    setError([false,-1]);
    setErrorMsg(false);
    setRegisterCliked(!registerCliked);
    setLoginCliked(!loginCliked);
  }
  return <div className="form-style-8">
    {loginCliked
        ? (<div>
              <h2>Login to your account</h2>
              <form>
                {error[0] && error[1] === 0 ? <div style={{color:"red"}}>{errorMsg}</div> : null}
                <input onChange={handleUsername} type="text1" placeholder="username" />
                <input onChange={handlePassword} type="password" placeholder="password" />
                <button onClick={handleLogin}>Login</button>
                <div className="text">
                  <h4>You don't have an account? <a style={{color: "#0645f3",textDecoration: "none"}} href='' onClick={handleClick}> Register</a></h4>
                </div>
              </form>
            </div>)
        : (<div>
              <h2>Registration</h2>
              <form>
                  {error[0] && error[1] === 0 ? <div style={{color:"red"}}>{errorMsg}</div> : null}
                  <input onChange={handleFirstname} type="text1" placeholder="Firstname" />
                  <input onChange={handleLastname} type="text1" placeholder="Lastname" />
                  <input onChange={handleUsername} type="text1" placeholder="Username" />
                  <input onChange={handleDob} type="date" />
                  {error[0] && error[1] === 3 ? <div style={{color:"red"}}>{errorMsg}</div> : null} 
                  <input onChange={handleEmail} type="email" placeholder="Email" />
                  {error[0] && error[1] === 2 ? <div style={{color:"red"}}>{errorMsg}</div> : null} 
                  <input onChange={handlePassword} type="password" placeholder="password" />
                  {error[0] && error[1] === 1 ? <div style={{color:"red"}}>{errorMsg}</div> : null}
                  <button onClick={handleSubmit}>Register</button>
                  {submitted ? <div style={{color: "green"}}>A verification link has been sent to your email address</div> : null }
                  <div className="text">
                    <h4>Already have an account? <a style={{color: "#0645f3",textDecoration: "none"}} href='' onClick={handleClick}> Login now</a></h4>
                  </div>
                </form>
            </div>) 
      }   
      </div>;
}
