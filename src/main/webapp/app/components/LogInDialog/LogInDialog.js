import React from 'react';

import setModal from '../../decorators/Modal';

import LogInFormContainer from '../../containers/LogInFormContainer';

@setModal('Log In')
export default class LogInDialog extends React.Component {
  render() {
    return (
      <LogInFormContainer logIn={this.props.logIn}/>
    );
  }
}

LogInDialog.propTypes = {
  logIn: React.PropTypes.func.isRequired
};
