import React from 'react';
import $ from 'jquery';

const setModal = (dialogTitle = '', isSmall = false) => Component =>
  class Modal extends React.Component {
    static propTypes = {
      isShown: React.PropTypes.bool.isRequired,
      hide: React.PropTypes.func.isRequired
    };

    componentDidMount() {
      $(this.node).on('hidden.bs.modal', () => {
        if (this.props.isShown) {
          this.props.hide();
        }
      });
    }

    componentWillReceiveProps(nextProps) {
      if (this.props.isShown !== nextProps.isShown) {
        if (nextProps.isShown) {
          this.showModal();
        } else {
          this.hideModal();
        }
      }
    }

    componentWillUnmount() {
      this.hideModal();
    }

    showModal = () => {
      $(this.node).modal('show');
    };

    hideModal = () => {
      $(this.node).modal('hide');
    };

    render() {
      return (
        <div ref={node => this.node = node}
             className={`modal fade ${isSmall ? 'bs-example-modal-sm' : ''}`}
             id="myModal"
             tabIndex="-1"
             role="dialog"
             aria-labelledby="myModalLabel"
             aria-hidden="true">
          <div className={`modal-dialog ${isSmall ? 'modal-sm' : ''}`}>
            <div className="modal-content" style={{ overflow: 'auto', paddingBottom: '15px' }}>
              <div className="modal-header">
                <button type="button" className="close" data-dismiss="modal">
                  <span aria-hidden="true">&times;</span>
                  <span className="sr-only">Close</span>
                </button>
                <h4 className="modal-title" id="myModalLabel">{dialogTitle}</h4>
              </div>
              <div className="modal-body">
                <Component {...this.props}/>
              </div>
            </div>
          </div>
        </div>
      );
    }
  };

export default setModal;
