import React from "react";
import ReactDOM from "react-dom";

import LogInFormContainer from "../containers/LogInFormContainer";

export default class LogInForm extends React.Component {
    componentDidMount() {
        let node = ReactDOM.findDOMNode(this);
        $(node).on('hidden.bs.modal', () => {
            if (this.props.isShown) {
                this.props.hide();
            }
        })
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isShown !== nextProps.isShown) {
            if (nextProps.isShown) {
                ::this.showModal();
            } else {
                ::this.hideModal();
            }
        }
    }

    showModal() {
        let node = ReactDOM.findDOMNode(this);
        $(node).modal('show');
    }

    hideModal() {
        let node = ReactDOM.findDOMNode(this);
        $(node).modal('hide');
    }

    render() {
        return (
            <div className="modal fade"
                 tabIndex="-1"
                 role="dialog"
                 aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content" style={{overflow: 'auto', paddingBottom: '15px'}}>
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
        )
    }
}