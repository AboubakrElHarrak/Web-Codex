import React from "react";
import "./Popup.css"
import Form from "../../formPage/content/Form";
 
const Popup = props => {
  return (
    <div className="popup-box">
      <div className="box_">
        <span className="close-icon" onClick={props.handleClose}>x</span>
        {props.content}
        <p>Connect to your account to add a comment</p>
        <a type="button" style={{marginLeft: 10}} className="btn btn-info" href="/form" >Log-in</a>
      </div>
    </div>
  );
};
 
export default Popup;