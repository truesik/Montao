import React from 'react';
import ReactDOM from 'react-dom';

const setModal = (Component) => {
    return class extends React.Component {
        componentDidMount() {
            const node = ReactDOM.findDOMNode(this);
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
            const node = ReactDOM.findDOMNode(this);
            $(node).modal('show');
        }

        hideModal() {
            const node = ReactDOM.findDOMNode(this);
            $(node).modal('hide');
        }

        render() {
            return (
                <Component {...this.props}/>
            )
        }
    }
};

export default setModal;