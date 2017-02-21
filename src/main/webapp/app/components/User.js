import React from 'react';
import { Map } from 'immutable';

const User = ({ user }) => (
  <li>
    <a href={`/${(user.get('username'))}`}>{user.get('username')}</a>
  </li>
);

User.propTypes = {
  user: React.PropTypes.instanceOf(Map).isRequired
};

export default User;
