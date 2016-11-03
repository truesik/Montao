import React from "react";

const Community = ({community}) => {
    return (
        <div className="col-lg-3">
            <div className="panel panel-default">
                <div className="panel-body">
                    <q>{community.description}</q>
                </div>
                <div className="panel-footer">
                    <i className="material-icons">group</i>
                    <a href="#">{community.title}</a>
                    <a className="btn btn-primary"
                       style={{float: 'right'}}>
                        {community.subscribed ? "Leave" : "Join"}
                    </a>
                </div>
            </div>
        </div>
    )
};

export default Community;