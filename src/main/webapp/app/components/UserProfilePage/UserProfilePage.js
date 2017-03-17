import React from 'react';
// import Icon from '../Icon';
// import './UserProfilePage.scss';

export class UserProfilePage extends React.Component {
  /*
   static propTypes = {
   profile: PropTypes.object,
   toggleUserProfileDialog: PropTypes.func
   };

   static defaultProps = {
   profile: {}
   };
   */
  accountHandler() {

  }

  notifHandler() {

  }

  clickHandler() {
    // this.props.toggleUserProfileDialog(true);
  }

  channelsHandler() {

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

        <div className="Profile__container">
          <div className="Profile__user">
            <div className="Profile__user-photo" onClick={this.onClickHandler}>
              <img src={profile.image}/>
              {/*<Icon className="ProfileCard__edit-icon Profile__user-photo-icon"/>*/}
              <input type="file" className="Profile__user-hiddenElement" ref="photo"/>
            </div>
            <div className="Profile__user-name">
              {`${profile.first_name || ''} ${profile.last_name || ''}`}
            </div>
            <h1>User profile</h1><br />
            <ul className="nav navbar-nav navbar-left">
              <li><a href="" onClick="this.accountHandler">Account settigs</a></li>
              <li><a href="" onClick="this.notifHandler">Notification</a></li>
              <li><a href="" onClick="this.channelsHandler">Favorite channels</a></li>
            </ul>
            <br />
          </div>
        </div>
      </div>
    );
  }
}

export default UserProfilePage;
