import React from 'react';

const User = ({ user }) => (
  <li>
    <a href={`/${(user.get('username'))}`}>{user.get('username')}</a>
  </li>
);

export default User;
