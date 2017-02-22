import React from 'react';

import setModal from '../../decorators/Modal';

import AddChannelFormContainer from '../../containers/AddChannelFormContainer';

@setModal('Add Channel')
export default class AddChannelDialog extends React.Component {
  render() {
    return (
      <AddChannelFormContainer communityTitle={this.props.communityTitle}
                               addChannel={this.props.addChannel}/>
    );
  }
}

AddChannelDialog.propTypes = {
  communityTitle: React.PropTypes.string.isRequired,
  addChannel: React.PropTypes.func.isRequired
};
