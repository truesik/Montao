import React from 'react';
import { browserHistory } from 'react-router';

export default class SearchBar extends React.Component {
  handleSearchSubmit = (event) => {
    if (this.searchInput.value.trim().length > 0) {
      browserHistory.push({
        pathname: '/search',
        query: { q: this.searchInput.value }
      });
      this.searchInput.value = '';
    }
    event.preventDefault();
  };

  render() {
    return (
      <form className="navbar-form" onSubmit={this.handleSearchSubmit}>
        <div className="input-group">
          <input type="text" className="form-control" placeholder="Search" ref={node => this.searchInput = node}/>
          <div className="input-group-btn">
            <button className="btn btn-default" type="submit">
              <i className="glyphicon glyphicon-search"></i>
            </button>
          </div>
        </div>
      </form>
    );
  }
}
