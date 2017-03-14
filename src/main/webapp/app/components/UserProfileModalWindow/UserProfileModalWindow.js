import React, {PropTypes, Component} from 'react';

export class UserProfileModalWindow extends Component{ 

    
    render(){
        const props = this.props;
        return (
            <div className="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 className="modal-title" id="myModalLabel">Account settings</h4>
                        </div>
                        <div className="modal-body">
                            <ul>
                                <li><a href="" onclick="">Change Name</a> </li>
                                <li><a href="" onclick="">Change Avatar</a></li>
                                <li><a href="" onclick="">Change Password</a> </li>
                            </ul>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-default" data-dismiss="modal">Закрыть</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
