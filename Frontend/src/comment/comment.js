import { parse } from "json-in-order";
import React, { useState } from "react";
import { Navigate } from "react-router-dom";
import './comment.css'
import Popup from "./Popup/Popup";

export default function Comment(args) {

    const [comments, setComments] = useState();
    const [Connected, setConnected] = useState(true);
    const [isEmpty, setIsEmpty] = useState(false);
    const [commentNb, setCommentNb] = useState(5);
    const [likes_, setLikes_] = useState(0);
    const [likedCommentid, setlikedCommentid] = useState();

    const [commentsLikes, setCommentsLikes] = useState([]);

    const fetchComments = async (nb) => {
        const endpoint = "http://localhost:8080/articles/"+args.articleTitle+"/get-comments/"+nb;
        const response = await fetch(endpoint);
        const data = await response.text();
        const comments = parse(data);
        return comments;
    }

    const handleClick = async () => {
      if (args.currentUser_) {
        setConnected(true);
        const comment_content = document.getElementById("comment_content").value;
        if (comment_content !== "") {
          setIsEmpty(false);
          const endpoint_ = "http://localhost:8080/api/add-comment/"+args.articleTitle;
          const response = await fetch(endpoint_, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer " + args.currentUser_["access_token"]
            },
            body: JSON.stringify(
              {
                "content":  comment_content
              }
            )
          });      
          const data = await response.text();
          if (data.includes("The Token has expired")) {
            refreshToken();
          }
        }
        else {
          setIsEmpty(true);
        }
      }
      else {
        setConnected(false);
      }
    }

    const togglePopup  = () => {
      setConnected(true);
    }
    const handleLike = (e) => {
      setLikes_(1);
      var id = e.currentTarget.value;
        for (var comment_id of commentsLikes_) {
          if (comment_id.id == id) {
            comment_id.likes++;
          }
        }
        setCommentsLikes(commentsLikes_);
      console.log(id, likedCommentid);
      fetch("http://localhost:8080/like-comment/"+id, {
        method: "POST"
      });
    }
    const handleDislike = (e) => {
      var id = e.currentTarget.value;
      console.log(id);
      fetch("http://localhost:8080/dislike-comment/"+id, {
        method: "POST"
      });
    }
  const refreshToken = async () => {
    const response = await fetch("http://localhost:8080/jwtTokenRefresh",{
      method: "GET",
      headers: {
        "Authorization": "Bearer " + args.currentUser_["refresh_token"] 
      },
      redirect: "follow"
    });
    if(response.ok)
    {
      const data = await response.json();
      localStorage.removeItem("user");
      localStorage.setItem("user", JSON.stringify(data));
    }
    else
    {
      Navigate("/form");
      window.location.reload();
    }
  }

  const showMoreComments = () => {
    setCommentNb(commentNb+5);
    fetchComments(commentNb).then((value) => {setComments(value)});
  }

    if (!comments) {
        fetchComments(5).then((value) => {setComments(value)});
    }

    var commentList = [];
    var commentsLikes_ = [];
    if (comments) {
      for (var comment_ of comments) {
        commentsLikes_.push({"id":comment_.get("commentId"), "likes":comment_.get("likes")});
      }
    }
    if (comments) {
        for (var comment_ of comments) {
          const style_ = {
            display: "table-cell",
            marginLeft : 10,
          }
          const date = comment_.get("post_date").split("T");
          const time = date[1].split(".");
            const element = <div className="panel">
            <div className="panel-body">
            <div className="media-block">
              <a className="media-left" href="#"><img className="img-circle img-sm" alt="Profile Picture" src="https://cdn-icons-png.flaticon.com/512/149/149071.png"/></a>
              <div className="media-body">
                <div className="mar-btm" style={{marginLeft : 10}}>
                  <a href="#" style={{display: "table-cell"}} className="btn-link text-semibold media-heading box-inline">{comment_.get("user").get("username")}</a>
                  <p style={style_} className="text-muted text-sm"><i style={{marginLeft : 10}} className="fa fa-clock-o"></i> {date[0]} {time[0]}</p>
                </div>
                <p style={{display: "flex", margin: 10}}>{comment_.get("content")}</p>
                <div className="pad-ver">
                  <div style={{display : "table-row"}} className="btn-group">
                    <button style={{display : "table-cell"}} className="btn btn-sm btn-default btn-hover-danger" value={comment_.get("commentId")} onClick={(e) => handleLike(e)}><i className="fa fa-thumbs-up"></i></button>
                    <p style={{marginLeft: 0, display : "table-cell"}}>{commentsLikes_.filter(js => js.id == comment_.get("commentId")).map(js => js.likes)}</p>
                    <button style={{display : "table-cell"}} className="btn btn-sm btn-default btn-hover-danger" value={comment_.get("commentId")} onClick={(e) => handleDislike(e)}><i className="fa fa-thumbs-down"></i></button>
                    <p style={{marginLeft: 5, display : "table-cell"}}>{comment_.get("dislikes")}</p>
                  
                  {/*<a style={{display : "table-cell"}} className="btn btn-sm btn-default btn-hover-primary">Reply</a>*/}
                  </div>
                </div>
                <hr/>
        
              </div>
            </div>
        
          </div>
        </div>
        ;
        commentList = [...commentList, element];
        }
    } 
    

    return (
        <div className="container bootdey">
        <div className="col-md-12 bootstrap snippets">
        <div className="panel">
        <div className="panel-body">
            <textarea id="comment_content" className="form-control" rows="3" placeholder="Add a comment..."></textarea>
            {isEmpty && <label style={{color: "#da292e"}} className='col-sm-2 col-form-label'>Comment can't be empty</label>}
            <div className="mar-top clearfix">
            <button className="btn btn-sm btn-primary pull-right" onClick={handleClick}><i style={{marginRight :10}} className="fa fa-paper-plane"></i>Comment</button>
            </div>
        </div>
        </div>

        {/*List of comments*/}
        <div>
          {commentList}
          <a onClick={showMoreComments} type="button" style={{margin: 10, textDecoration: "underline"}}>Show more comments</a>
        </div>
        
        </div>
                <div id="dialog_">
                  {!Connected &&
                  <Popup
                  handleClose={togglePopup} />
                  }
                </div>
        </div>
        
    );
}