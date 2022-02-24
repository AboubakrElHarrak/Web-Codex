import { parse } from "json-in-order";
import React, { useState } from "react";
import './comment.css'

export default function Comment(args) {

    const [comments, setComments] = useState();

    const fetchComments = async () => {
        const endpoint = "http://localhost:8080/articles/"+args.articleTitle+"/get-comments";
        const response = await fetch(endpoint);
        const data = await response.text();
        const comments = parse(data);
        return comments;
    }

    const handleClick = () => {
      const comment_content = document.getElementById("comment_content").value;
      const endpoint_ = "http://localhost:8080/api/add-comment/"+args.articleTitle;
      fetch(endpoint_, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(
          {
            "userId": 1,
            "content":  comment_content
          }
        )
    });
      console.log();
    }

    if (!comments) {
        fetchComments().then((value) => {setComments(value)});
    }

    var commentList = [];
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
                  <div style={{display : "table-cell"}} className="btn-group">
                    <a className="btn btn-sm btn-default btn-hover-success" href="#"><i className="fa fa-thumbs-up"></i></a>
                    <a className="btn btn-sm btn-default btn-hover-danger" href="#"><i className="fa fa-thumbs-down"></i></a>
                  </div>
                  <a style={{display : "table-cell"}} className="btn btn-sm btn-default btn-hover-primary" href="#">Reply</a>
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
            <div className="mar-top clearfix">
            <button className="btn btn-sm btn-primary pull-right" onClick={handleClick}><i style={{marginRight :10}} className="fa fa-paper-plane"></i>Comment</button>
            </div>
        </div>
        </div>

        {/*List of comments*/}
        <div>
          {commentList}
        </div>
        
        </div>
        </div>
    );
}