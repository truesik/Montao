import React from 'react';

const checkExistence = (Component) => {
    return class extends React.Component {
        componentDidMount() {
            this.props.checkCommunityExistence(this.props.params.community);
        }

        render() {
            let communityTemplate;
            if (this.props.valid && !this.props.notFound) {
                communityTemplate = (
                    <Component {...this.props}/>
                )
            } else if (!this.props.valid && this.props.notFound) {
                communityTemplate = (
                    <NotFound/>
                )
            } else if (!this.props.valid) {
                communityTemplate = (
                    <div className="loading">
                        <i className="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
                    </div>
                )
            }
            return (
                <div>
                    {communityTemplate}
                </div>
            )
        }
    }
};

export default checkExistence;