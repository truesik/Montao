import React from 'react';

import SignUpFormContainer from '../containers/SignUpFormContainer';
import setModal from '../decorators/Modal';

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
