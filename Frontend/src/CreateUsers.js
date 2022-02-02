import React, { Component, useEffect, useState } from 'react';
import axios from 'axios'
function CreateUsers() {
    const [user, setUser]=useState({
        name:"",
        email:"",
        password:"",
        interest_center:""
    });
        function saveUser(e){
            e.preventDefault();
            console.log('user =>'+JSON.stringify(user));
            axios.post("http://localhost:9091/api/v1/users",user).then((res)=> console.log(res))}
    
       function nameHandler(e){
        setUser({...user,name:e.target.value});
        }
        function emailHandler(e){
            setUser({...user,email:e.target.value});
            }
        function mdpHandler(e){
            setUser({...user,password:e.target.value});
            }
        function ciHandler(e){
            setUser({...user,interest_center:e.target.value});
            }
       
    
  return <div>
      <form>
        <input type="text1" name="field1"  placeholder="Name" onChange={nameHandler} />
        <input type="email" name="field2"  placeholder="Email" onChange={emailHandler} />
        <input type="password" name="field3"  placeholder="password" onChange={mdpHandler} />
        <input type="password" name="field4" placeholder="Confirm password" />
        <input type="text1" name="field5"  placeholder="Your center of interest (sport, sience ..." onChange={ciHandler} />
        <input type="button" value="Register" onClick={saveUser} />
        <div className="text">
        <h4>Already have an account? <a style={{color: "#0645f3",textDecoration: "none"}} href='' > Login now</a></h4>
        </div>
    </form>
  </div>;
}

export default CreateUsers;
