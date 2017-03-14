import React from 'react';
import { Map } from 'immutable';

import User from '../User';

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

UserList.propTypes = {
  getUsers: React.PropTypes.func.isRequired,
  communityTitle: React.PropTypes.string.isRequired,
  subscribers: React.PropTypes.arrayOf(Map)
};
