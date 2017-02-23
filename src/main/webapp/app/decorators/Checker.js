import React from 'react';

import NotFound from '../components/NotFound';
import Spinner from '../components/Spinner';

const checkExistence = (Component) =>
  class Checker extends React.Component {
    static propTypes = {
      checkCommunityExistence: React.PropTypes.func.isRequired,
      params: React.PropTypes.shape({
        community: React.PropTypes.string.isRequired
      }).isRequired,
      valid: React.PropTypes.bool.isRequired,
      notFound: React.PropTypes.bool.isRequired
    };

    componentDidMount() {
      this.props.checkCommunityExistence(this.props.params.community);
    }

    renderNotFound() {
      return (
        <NotFound/>
      );
    }

    renderComponent() {
      return (
        <Component {...this.props}/>
      );
    }

    renderSpinner() {
      return (
        <Spinner/>
      );
    }

    render() {
      let template;
      if (this.props.valid && !this.props.notFound) {
        template = this.renderComponent();
      } else if (!this.props.valid && this.props.notFound) {
        template = this.renderNotFound();
      } else if (!this.props.valid) {
        template = this.renderSpinner();
      }
      return (
        <div>
          {template}
        </div>
      );
    }
  };

export default checkExistence;
