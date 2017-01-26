import React from 'react';

import setModal from '../decorators/Modal';

@setModal
export default class UserListDialog extends React.Component {
    render() {
        const usersTemplate = this.props.subscribers.map(subscriber => (
            <a className="list-group-item" key={subscriber.getIn(['user', 'id'])}>
                {subscriber.getIn(['user', 'username'])}</a>
        ));
        return (
            <div className="modal fade bs-example-modal-sm" id="myModal" tabIndex="-1" role="dialog"
                 aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog modal-sm">
                    <div className="modal-content" style={{overflow: 'auto', paddingBottom: '15px'}}>
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span className="sr-only">Close</span>
                            </button>
                            <h4 className="modal-title" id="myModalLabel">User List</h4>
                        </div>
                        <div className="list-group">
                            {usersTemplate}
                        </div>
                    </div>
                </div>
            </div>
        )
    }
};