import React from 'react';

const Users = ({ posts, loading }) => {
  if (loading) {
    return <div class="spinner-border" role="status">
                <span class="visually-hidden">Loading...</span>
            </div>;
  }
  return (
    <>
      {posts.map(post => (
          <tbody><tr>
            <td>{post.firstname}</td>
            <td>{post.lastname}</td>
            <td>{post.birthday}</td>
            <td>{post.username}</td>
            <td>{post.email}</td>
            <td>{post.role}</td>
            <td>{post.active ? "active":"non active"}</td>
            <td><a href='' style={{textDecoration: "none",color:"red"}}>Bannir</a></td>
            </tr></tbody>
      ))}
    </>
  );
};

export default Users;