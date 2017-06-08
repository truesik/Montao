import React, { Component } from 'react';
import './UserProfileSettings.css';

export class UserProfileSettings extends Component{ 

    
  render(){
    return (
            <div>
            <div className="UserProfileSettings__main">
            <ul className="nav navbar-nav navbar-left">
                <li><a href="" onClick="">Change Name</a> </li>
                <li><a href="" onClick="">Change Avatar</a></li>
                <li><a href="" onClick="">Change Password</a> </li>
            </ul>
            </div>
            <div>
                <button type="button" className="btn btn-default">Закрыть</button>
            </div>
        </div>
    );
  }
}

export default UserProfileSettings;
