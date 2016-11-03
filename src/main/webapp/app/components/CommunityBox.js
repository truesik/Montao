import React from "react";
import Community from "./Community"

export default class CommunitiesBox extends React.Component {
    componentDidMount() {
        var getCommunities = this.props.getCommunities;
        getCommunities(0);
    }

    render() {
        var communities = this.props.communities;
        var communitiesTemplate = communities.map((community) => {
            return (
                <Community key={community.id} community={community}/>
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