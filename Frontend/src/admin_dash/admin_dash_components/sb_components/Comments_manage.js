import React, { useState, useEffect } from 'react'
import axios from 'axios';
function Comments_manage() {
  const [comments, setcomments] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [commentsPerPage] = useState(1);

  useEffect(() => {
    const fetchcomments = async () => {
      
      const res = await axios.get('http://localhost:8080/comments');
      setcomments(res.data);
    };

    fetchcomments();
  }, []);
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(comments.length / commentsPerPage); i++) {
    pageNumbers.push(i);
  }
  // Get current comments
  const indexOfLastPost = currentPage * commentsPerPage;
  const indexOfFirstPost = indexOfLastPost - commentsPerPage;
  const currentcomments = comments.slice(indexOfFirstPost, indexOfLastPost);

  // Change page
  const paginate = pageNumber => setCurrentPage(pageNumber);
  function handleNextbtn(e){
    e.preventDefault();
    if(currentPage < comments.length / commentsPerPage ){
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
      <h1 class="fs-5 fw-5" style={{margin:"30px 0 0 680px"}}>Manage Comments</h1>
      <div class="row justify-content-center py-5" style={{margin:" 0 -95px 0 155px"}}>
              <div class="col-md-10 ">
                
                {currentcomments.map(comment => (<div class="card" style={{marginBottom:"10px"}}>
                      <h5 class="card-header" >Comment id : {comment.id}</h5>
                      <div class="card-body">
                        <h5 class="card-title">Article id :{comment.user_id}</h5>
                        <p class="card-text">Comment content : {comment.content}</p>
                        <p className="card-text"><small class="text-muted">Post date : {comment.post_date}</small></p>
                        <p><a href='' style={{textDecoration: "none",color:"red"}}>Delete this comment</a></p>
                      </div></div>
                  ))}
                <nav aria-label="Page navigation example">
                      <ul class="pagination">
                      <li class="page-item"><a class="page-link" onClick={handlePrevsbtn}  href="#">Previous</a></li>
                      <li class="page-item"><a class="page-link" onClick={handleNextbtn} href="#">Next</a></li>
                      </ul>
                  </nav>
              </div>
        </div>
    </div>
  )
}

export default Comments_manage