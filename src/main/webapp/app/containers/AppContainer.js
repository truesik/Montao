import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as userActions from '../actions/UserActions';

import App from '../components/App';

const mapStateToProps = (state) => {
  return {
    isAuthorized: state.usersReducer.get('isAuthorized')
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    checkAuthorization: bindActionCreators(userActions.checkAuthorization, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
