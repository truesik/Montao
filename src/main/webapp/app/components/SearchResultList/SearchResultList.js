import React from 'react';
import { Map } from 'immutable';
import { Link } from 'react-router';

export default class SearchResultList extends React.Component {
  componentDidMount() {
    const q = this.props.location.query.q;
    if (q) {
      this.props.search(q);
    }
  }

  componentWillReceiveProps(nextProps) {
    // Если текст запроса изменился, то вызываем новый поиск
    if (this.props.location.query.q !== nextProps.location.query.q) {
      this.props.search(nextProps.location.query.q);
    }
  }

  render() {
    const results = this.props.results;
    if (results.length == 0) {
      return (
        <div style={ { marginTop: '100px' } }>
          <string>Ничего не найдено.</string>
        </div>
      );
    }
    else {
      const searchResultListTemplate = results.map(result => (
        <li key={result.get('id')} className="list-group-item">
          <Link to={`/community/${result.get('title')}`}>{result.get('title')}</Link>
        </li>
      ));
      return (
        <div style={{ marginTop: '100px' }}>
          <ul className="list-group" id="searchResultList">
            {searchResultListTemplate}
          </ul>
        </div>
      );
    }
  }
}

SearchResultList.propTypes = {
  results: React.PropTypes.arrayOf(Map),
  location: React.PropTypes.shape({
    query: React.PropTypes.shape({
      q: React.PropTypes.string
    }).isRequired
  }).isRequired,
  search: React.PropTypes.func.isRequired
};
