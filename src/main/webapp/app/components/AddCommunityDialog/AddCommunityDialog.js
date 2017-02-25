import React from 'react';

import setModal from '../../decorators/Modal';

import AddCommunityFormContainer from '../../containers/AddCommunityFormContainer';

@setModal('Add Community')
export default class AddCommunityDialog extends React.Component {
  render() {
    return (
      <AddCommunityFormContainer username={this.props.username}
                                 addCommunity={this.props.addCommunity}/>
    );
  }
}

AddCommunityDialog.propTypes = {
  username: React.PropTypes.string,
  addCommunity: React.PropTypes.func.isRequired
};
