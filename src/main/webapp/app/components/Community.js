import * as React from "react";

import checkExistence from '../decorators/Checker';

@checkExistence
export default class Community extends React.Component {
    render() {
        return (
            <div className="container" style={{marginTop: 100 + 'px'}}>
                <div className="row">
                    <div className="col-md-8">
                        <div className="panel panel-default">
                            <div className="panel-heading">Information</div>
                            <div className="panel-body">
                                <dl className="dl-horizontal">
                                    <dt>Title</dt>
                                    <dd>{this.props.community.get('title')}</dd>
                                    <dt>Description</dt>
                                    <dd>{this.props.community.get('description')}</dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                    <div className="offset-md-8 col-md-4">
                        <div className="panel panel-default">
                            <div className="panel-heading">Members</div>
                            <div className="panel-body">

                            </div>
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <div className="panel panel-default">
                            <div className="panel-heading">Latest posts</div>
                            <div className="panel-body">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}