import React from 'react';
import { Map } from 'immutable';

import setModal from '../../decorators/Modal';

@setModal('User List', true)
export default class UserListDialog extends React.Component {
  render() {
    const usersTemplate = this.props.subscribers.map(subscriber => (
      <a className="list-group-item" key={subscriber.getIn(['user', 'id'])}>
        {subscriber.getIn(['user', 'username'])}</a>
    ));
    return (
      <div className="list-group">
        {usersTemplate}
      </div>
    );
  }
}

UserListDialog.propTypes = {
  subscribers: React.PropTypes.arrayOf(Map)
};
