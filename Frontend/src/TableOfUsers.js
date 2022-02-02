import React, { Component, useEffect, useState } from 'react';
import axios from 'axios'
function TableOfUsers() {
    const [user, setUser]=useState([]);
        useEffect(()=>{
            axios.get("http://localhost:9091/api/v1/users").then((res)=>{
                setUser(res.data);
                console.log(res.data);
            }).catch(err=>{
                console.log(err)
            })
        },[])
    
  return <div>
        <table class="table">
            <thead>
                <tr>
                <th scope="col">Id</th>
                <th scope="col">name</th>
                <th scope="col">email</th>
                <th scope="col">password</th>
                <th scope="col">interest_center</th>
                </tr>
            </thead>
            <tbody>
                
            {user.map(user=>
                    <tr>
                        <th scope="row">1</th>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>{user.password}</td>
                        <td>{user.interest_center}</td>
                    </tr>
                        )}
                
            </tbody>
        </table>
  </div>;
}

export default TableOfUsers;
