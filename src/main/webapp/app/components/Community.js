import React from "react";
import {Link} from "react-router";
import {Map} from 'immutable';

const Community = ({community, action}) => {
    return (
        <div className="col-lg-3 col-md-4 col-sm-6 col-xs-12">
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

Community.propTypes = {
    community: React.PropTypes.instanceOf(Map).isRequired,
    action: React.PropTypes.func.isRequired
};

export default Community;