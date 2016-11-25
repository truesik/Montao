import React from "react";
import {Field} from "redux-form";

const renderField = ({input, name, label, type, readOnly, meta: {touched, error}}) => {
    return (
        <div className={!touched || !error ? "form-group" : "form-group has-error"}>
            <label htmlFor={name}>{label}</label>
            <input {...input}
                   placeholder={label}
                   type={type}
                   className="form-control"
                   id={name}
                   readOnly={readOnly}/>
            {touched && (error && <small className="help-block">{error}</small>)}
        </div>
    )
};

export default class AddCommunityForm extends React.Component {
    componentDidMount() {
        const initialValues = {
            'founder': this.props.username,
            'visible': "true"
        };
        this.props.initialize(initialValues);
    }

    render() {
        const {error, handleSubmit, pristine, reset, submitting, addCommunity} = this.props;
        return (
            <form method="post" onSubmit={handleSubmit(addCommunity)}>
                <Field name="title"
                       component={renderField}
                       type="text"
                       label="Title"/>
                <Field name="description"
                       component={renderField}
                       type="text"
                       label="Description"/>
                <Field name="founder"
                       component={renderField}
                       type="text"
                       label="Founder"
                       readOnly/>
                <div className="radio">
                    <label>
                        <Field name="visible"
                               component="input"
                               type="radio"
                               value="true"/>
                        Public
                    </label>
                </div>
                <div className="radio">
                    <label>
                        <Field name="visible"
                               component="input"
                               type="radio"
                               value="false"/>
                        Private
                    </label>
                </div>
                {error && <span className="text-danger">{error}</span>}
                <button type="submit" className="pull-right btn btn-primary" disabled={submitting}>Create</button>
            </form>
        )
    }
};