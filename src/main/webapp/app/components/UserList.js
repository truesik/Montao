import * as React from 'react';

import User from './User';

export default class UserList extends React.Component {
  componentDidMount() {
    const getUsers = this.props.getUsers;
    getUsers(this.props.communityTitle);
  }

  render() {
    const subscribers = this.props.subscribers;
    const userListTemplate = subscribers.map(subscriber => {
      return (
        <User key={subscriber.getIn(['user', 'id'])} user={subscriber.get('user')}/>
      );
    });
    return (
      <div>
        <ul className="nav nav-sidebar" id="userList">
          {userListTemplate}
        </ul>
      </div>
    );
  }
}
