import React from "react";
import ReactDOM from "react-dom";

export default class AddCommunityForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isVisible: true
        }
    }
    componentDidMount() {
        var node = ReactDOM.findDOMNode(this);
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
        var node = ReactDOM.findDOMNode(this);
        $(node).modal('show');
    }

    hideModal() {
        var node = ReactDOM.findDOMNode(this);
        $(node).modal('hide');
    }

    handlePrivacyChange(isVisible) {
        this.setState({
            isVisible: isVisible
        })
    }

    handleSubmit() {
        let title = ReactDOM.findDOMNode(this.refs.title).value;
        let description = ReactDOM.findDOMNode(this.refs.description).value;
        let founder = ReactDOM.findDOMNode(this.refs.founder).value;
        let visible = this.state.isVisible;
        let community = {
            title: title,
            description: description,
            founder: founder,
            visible: visible
        };
        this.props.addCommunity(community);
    }

    render() {
        return (
            <div className="modal fade"
                 tabIndex="-1"
                 role="dialog"
                 aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                                <span className="sr-only">Close</span>
                            </button>
                            <h4 className="modal-title" id="myModalLabel">Log In</h4>
                        </div>
                        <div className="modal-body">
                            <form method="post">
                                <div className="form-group">
                                    <label htmlFor="title">Title</label>
                                    <input type="text"
                                           className="form-control"
                                           name="username"
                                           placeholder="Title"
                                           ref="title"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="description">Description</label>
                                    <input type="text"
                                           className="form-control"
                                           name="description"
                                           placeholder="Description"
                                           ref="description"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="founder">Founder</label>
                                    <input type="text"
                                           className="form-control"
                                           name="founder"
                                           placeholder="Founder"
                                           ref="founder"
                                           value={this.props.username}
                                           readOnly/>
                                </div>
                                <div className="radio">
                                    <label>
                                        <input type="radio"
                                               name="visible"
                                               checked={this.state.isVisible}
                                               onChange={() => ::this.handlePrivacyChange(true)}/>
                                        Public
                                    </label>
                                </div>
                                <div className="radio">
                                    <label>
                                        <input type="radio"
                                               name="visible"
                                               checked={!this.state.isVisible}
                                               onChange={() => ::this.handlePrivacyChange(false)}/>
                                        Private
                                    </label>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit"
                                    className="btn btn-primary"
                                    onClick={() => ::this.handleSubmit()}>
                                Log In
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}