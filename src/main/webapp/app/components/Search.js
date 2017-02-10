import React from "react";

export default class Search extends React.Component {
    render() {
        return (
            <form className="navbar-form" role="search">
                <div className="input-group">
                    <input type="text" className="form-control" placeholder="Search" name="q"/>
                    <div className="input-group-btn">
                        <button className="btn btn-default" type="submit">
                            <i className="glyphicon glyphicon-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        )
    }
}