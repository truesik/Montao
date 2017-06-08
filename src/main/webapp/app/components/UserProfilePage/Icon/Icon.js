import './Icon.css';
import React, { PropTypes } from 'react';

class Icon extends React.Component {
  static propTypes = {
    type: PropTypes.string.isRequired
  };

  static defaultProps = {
    type: ''
  };

  render() {
    const { type, ...props } = this.props;
    return (
      <span className="icon_main" type={type} {...props}/>
    );
  }
}

export default Icon;
