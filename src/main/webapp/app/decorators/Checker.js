import React from 'react';

import NotFound from '../components/NotFound';

const checkExistence = (Component) => {
    return class extends React.Component {
        componentDidMount() {
            this.props.checkCommunityExistence(this.props.params.community);
        }

        render() {
            let template;
            if (this.props.valid && !this.props.notFound) {
                template = (
                    <Component {...this.props}/>
                )
            } else if (!this.props.valid && this.props.notFound) {
                template = (
                    <NotFound/>
                )
            } else if (!this.props.valid) {
                template = (
                    <div className="loading">
                        <i className="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
                    </div>
                )
            }
            return (
                <div>
                    {template}
                </div>
            )
        }
    }
};

export default checkExistence;