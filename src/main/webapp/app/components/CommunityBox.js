import React from "react";
import Community from "./Community"

export default class CommunitiesBox extends React.Component {
    componentDidMount() {
        this.props.getCommunities(0);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthorized !== nextProps.isAuthorized) {
            this.props.getCommunities(0);
        }
    }

    render() {
        var communities = this.props.communities;
        var join = this.props.join;
        var leave = this.props.leave;
        var communitiesTemplate = communities.map((community) => {
            return (
                <Community key={community.id} community={community} action={community.subscribed ? leave : join}/>
            )
        });

        return (
            <div className="container-fluid" style={{marginTop: 100 + 'px'}}>
                <div className="row" id="communities">
                    {communitiesTemplate}
                </div>
            </div>
        )
    }
}