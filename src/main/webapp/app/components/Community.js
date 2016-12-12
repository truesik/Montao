import React from "react";
import {Link} from "react-router";

const Community = ({community, action}) => {
    return (
        <div className="col-lg-3 col-md-3 col-sm-6 col-xs-12">
            <div className="panel panel-default">
                <div className="panel-body">
                    <q>{community.get('description')}</q>
                </div>
                <div className="panel-footer">
                    <i className="material-icons">group</i>
                    <Link to={`/community/${community.get('title')}`}>{community.get('title')}</Link>
                    <a className="btn btn-primary pull-right"
                       onClick={() => action(community.get('title'))}>
                        {community.get('subscribed') ? "Leave" : "Join"}
                    </a>
                </div>
            </div>
        </div>
    )
};

export default Community;