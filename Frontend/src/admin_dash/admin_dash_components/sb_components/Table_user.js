import React, { useState, useEffect } from 'react';
import Users from './Tu_components/Users';
import axios from 'axios';

const Table_user = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(8);

  useEffect(() => {
    const fetchPosts = async () => {
      setLoading(true);
      const res = await axios.get('http://localhost:8080/users');
      setPosts(res.data);
      setLoading(false);
    };

    fetchPosts();
  }, []);
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(posts.length / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  // Get current posts
  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);
  
  // Change page
  const paginate = pageNumber => setCurrentPage(pageNumber);
  function handleNextbtn(e){
    e.preventDefault();
    if(currentPage < posts.length / postsPerPage ){
        setCurrentPage(currentPage+1);
    }
  }
  function handlePrevsbtn(e){
    e.preventDefault();
    if(currentPage  > 1 ){
    setCurrentPage(currentPage-1);
    }
    
  }
  
  return (
    <div className='container mt-5'>
      <h1 class="fs-5 fw-5" style={{margin:"30px 0 0 680px"}}>Manage users</h1>
    <div class="row justify-content-center py-5" style={{margin:" 0 -95px 0 155px"}}>
            <div class="col-md-10 ">
                <div class="card">
                    <div class="card-header">
                      <span><i class="bi bi-table me-2 "></i></span> All users
                    </div>
                    <div class="card-body">       
                    <div class="table-responsive">
                            <table class="table table-striped data-table">
                                <thead class="thead-dark">
                                    <tr>
                                    <th scope="col">Firstname</th>
                                    <th scope="col">Lastname</th>
                                    <th scope="col">birthday</th>
                                    <th scope="col">username</th>
                                    <th scope="col">email</th>
                                    <th scope="col">role</th>
                                    <th scope="col">active</th>
                                    <th scope="col">Action</th>
                                    </tr>
                                </thead>
                              <Users posts={currentPosts} loading={loading} />
                            </table>
                            <nav aria-label="Page navigation example">
                                <ul class="pagination">
                                <li class="page-item"><a class="page-link" onClick={handlePrevsbtn} href="#">Previous</a></li>
                                <li class="page-item"><a class="page-link" onClick={handleNextbtn} href="#">Next</a></li>
                                </ul>
                            </nav>
                        </div>
                      </div>
                    </div>
                </div>
            </div>
    </div>
  );
};

export default Table_user;