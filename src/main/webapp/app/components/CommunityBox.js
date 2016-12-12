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
        const communities = this.props.communities;
        const join = this.props.join;
        const leave = this.props.leave;
        const communitiesTemplate = communities.map((community) => {
            return (
                <Community key={community.get('id')} community={community} action={community.get('subscribed') ? leave : join}/>
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