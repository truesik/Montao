import React from 'react';
import { Link } from 'react-router';

export default class SearchResultList extends React.Component{
    render(){
        const results = this.props.result;
        if (results.length == 0){
            return (
                <div>
                    <string>Ничего не найдено.</string>
                </div>
            )
        }
        else {
            const searchResultListTemplate = results.map(result => (
                <li key={result.get('id')} className="list-group-item">
                    {result.get('title')}
                </li>
            ));
            return (
                <div>
                    <ul className="nav nav-sidebar list-group" id="searchResultList">
                        {searchResultListTemplate}
                    </ul>
                </div>
            )
        }
    }
}
