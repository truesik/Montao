import * as React from 'react';
import { Link } from 'react-router';

export default class SearchResult extends React.Component{
    render(){
        const results = this.props.result;
        const searchResultTemplate = results.map( result => (
            <li key= {result.get('id')}>
                {result.get('title')}
            </li>
        ));
        return (
            <div></div>
        )
    }
}