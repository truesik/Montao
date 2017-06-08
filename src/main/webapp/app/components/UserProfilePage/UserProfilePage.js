import React, { Component, PropTypes } from 'react';
import Icon from './Icon';
import UserProfileSettings from '../UserProfileSettings';
import UserProfileFavChannels from '../UserProfileFavChannels';
import './UserProfilePage.css';
import image from './1.jpg';

export class UserProfilePage extends Component {

  static propTypes = {
    profile: PropTypes.object,
  };

  static defaultProps = {
    profile: {
      first_name: 'unknown',
      last_name: 'unknown',
      image: './1.jpg'
    }
  };

  constructor(props){
    super(props);
    this.state = {
      showAccountOptions: false,
      showNotificationOptions: false, 
      showFavOptions: false
    };
    this.accountHandler = this.accountHandler.bind(this);
    this.notifHandler = this.notifHandler.bind(this);
    this.channelsHandler = this.channelsHandler.bind(this);
  }

  accountHandler(e) {
    e.preventDefault();
    this.setState({
      showAccountOptions: !(this.state.showAccountOptions)
    });
  }

  notifHandler(e) {
    e.preventDefault();
    this.setState({
      showNotificationOptions: !(this.state.showNotificationOptions)
    });
  }

  channelsHandler(e) {
    e.preventDefault();
    this.setState({
      showFavOptions: !(this.state.showFavOptions)
    });
  }

  SettingsRender() {
    const { showAccountOptions } = this.state;
    if(showAccountOptions == true){
      return (
          <UserProfileSettings/>
      );
    }
  }
  
  NotifRender(){
    const { showNotificationOptions } = this.state;
    if( showNotificationOptions == true){
      return (
          <div></div>
      );
    }
  }

  FavChannelsRender() {
    const { showFavOptions } = this.state;
    if(showFavOptions == true){
      return (
          <UserProfileFavChannels/>
      );
    }
  }


  render() {
    const { profile } = this.props;
    return (
      <div className="user-profile-page">
        <nav className="navbar navbar-inverse">
          <div className="container-fluid">
            <div className="navbar-header">
              <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span className="sr-only">Toggle navigation</span>
              </button>
              <a className="navbar-brand" href="/">Montao</a>
            </div>
            <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul className="nav navbar-nav navbar-right">
                <li><a href="/logout">Log out</a></li>
              </ul>
            </div>
          </div>
        </nav>
        <div className="Profile">
          <div className="Profile__container">
            <div className="Profile__user">
              <div className="Profile__user-photo" onClick={this.onClickHandler}>
                <img src={image} />
                <Icon className="ProfileCard__edit-icon Profile__user-photo-icon" />
                <input type="file" className="Profile__user-hiddenElement" ref="photo" />
              </div>
              <div className="Profile__user-name">
                {`${profile.first_name || ''} ${profile.last_name || ''}`}
              </div>
              <h1>User profile</h1><br />
              <ul className="nav navbar-nav navbar-left">
                <img src={image}></img>
                <li><a href="" onClick={this.accountHandler}>Account settigs</a></li>
                <li><a href="" onClick={this.notifHandler}>Notification</a></li>
                <li><a href="" onClick={this.channelsHandler}>Favorite channels</a></li>
              </ul>
                <div className="Profile__options">
                  <br/><br/>
                  <hr />
                  {this.SettingsRender() || this.NotifRender() || this.FavChannelsRender()}
                </div>
            </div>
          </div>
        </div>
      </div>

    );
  }
}

export default UserProfilePage;