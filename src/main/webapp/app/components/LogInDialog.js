import React from 'react';

import setModal from '../decorators/Modal';

import LogInFormContainer from '../containers/LogInFormContainer';

@setModal
export default class LogInDialog extends React.Component {
  render() {
    return (
      <div className="modal fade"
           tabIndex="-1"
           role="dialog"
           aria-labelledby="myModalLabel"
           aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content" style={{ overflow: 'auto', paddingBottom: '15px' }}>
            <div className="modal-header">
              <button type="button" className="close" data-dismiss="modal">
                <span aria-hidden="true">&times;</span>
                <span className="sr-only">Close</span>
              </button>
              <h4 className="modal-title" id="myModalLabel">Log In</h4>
            </div>
            <div className="modal-body">
              <LogInFormContainer logIn={this.props.logIn}/>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
