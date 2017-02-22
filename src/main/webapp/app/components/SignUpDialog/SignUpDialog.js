import React from 'react';

import setModal from '../../decorators/Modal';

import SignUpFormContainer from '../../containers/SignUpFormContainer';

@setModal('Sign Up')
export default class SignUpDialog extends React.Component {
  render() {
    return (
      <SignUpFormContainer signUp={this.props.signUp}/>
    );
  }
}

SignUpDialog.propTypes = {
  signUp: React.PropTypes.func.isRequired
};
