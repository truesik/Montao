import SearchResultList from '../components/SearchResultList/SearchResultList';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as searchActions from '../actions/SearchActions';

const mapStateToProps = (state) => {
  return {
    results: state.searchReducer.get('results').toArray()
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    search: bindActionCreators(searchActions.search, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SearchResultList);
