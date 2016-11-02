import * as React from "react";
import ChannelListContainer from '../containers/ChannelListContainer'
import UserListContainer from '../containers/UserListContainer'
import AddChannelForm from './AddChannelForm'
import ReactDOM from 'react-dom';

var sampleModalId = 'sample-modal-container';
export default class SideBar extends React.Component {
    constructor(props) {
        super(props);
        // this.handleShowModal = this.handleShowModal.bind(this);
        // this.handleHideModal = this.handleHideModal.bind(this);
    }

    handleShowModal() {
        var addChannelForm = React.cloneElement(<AddChannelForm prop={'hello'}/>);
        var addChannelFormContainer = document.createElement('div');
        addChannelFormContainer.id = sampleModalId;
        document.body.appendChild(addChannelFormContainer);
        ReactDOM.render(
            addChannelForm,
            addChannelFormContainer,
            () => {
                var modalObj = $('#' + sampleModalId + '>.modal');
                modalObj.modal('show');
                modalObj.on('hidden.bs.modal', ::this.handleHideModal);
            })
    }

    handleHideModal() {
        $('#' + sampleModalId).remove();
        // ReactDOM.unmountComponentAtNode(ReactDOM.findDOMNode(this.refs.addChannelForm));
    }

    render() {
        return (
            <div>
                <div>
                    <span>Channels </span>
                    <a className="glyphicon glyphicon-plus" onClick={::this.handleShowModal}></a>
                    <ChannelListContainer/>
                </div>
                <div>
                    <span>Users</span>
                    <UserListContainer/>
                </div>
            </div>
        )
    }
}