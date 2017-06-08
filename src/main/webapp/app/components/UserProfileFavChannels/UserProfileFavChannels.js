import React, { Component } from 'react';
//import './UserProfileFavChannels.css';

const channelsList =["Java", "JavaScript", "SQL"];

export class UserProfileFavChannels extends Component{ 

    
  render(){
    return (
            <div>
            <div className="UserProfileSettings__main">
            <ul className="nav navbar-nav navbar-left">
                <li><a href="" onClick="">{channelsList[0]}</a> </li>
                <li><a href="" onClick="">{channelsList[1]}</a></li>
                <li><a href="" onClick="">{channelsList[2]}</a> </li>
            </ul>
            </div>
            <div>
                <button type="button" className="btn btn-default">Закрыть</button>
            </div>
        </div>
    );
  }
}

export default UserProfileFavChannels;
