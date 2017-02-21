import React from 'react';
import { Field, propTypes } from 'redux-form';

// You shall not pass (only letters, numbers and underscope)
const normalizeTitle = value => value.replace(/[\W]+/g, '');

const renderField = ({ input, name, label, type, readOnly, meta: { touched, error } }) => {
  return (
    <div className={!touched || !error ? 'form-group' : 'form-group has-error'}>
      <label htmlFor={name}>{label}</label>
      <input {...input}
             placeholder={label}
             type={type}
             className="form-control"
             id={name}
             readOnly={readOnly}/>
      {touched && (error && <small className="help-block">{error}</small>)}
    </div>
  );
};

export default class AddCommunityForm extends React.Component {
  componentDidMount() {
    const initialValues = {
      'founder': this.props.username,
      'visible': 'true'
    };
    this.props.initialize(initialValues);
  }

  render() {
    const { error, handleSubmit, submitting, addCommunity } = this.props;
    return (
      <form method="post" onSubmit={handleSubmit(addCommunity)}>
        <Field name="title"
               component={renderField}
               type="text"
               label="Title"
               normalize={normalizeTitle}/>
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
    );
  }
}

renderField.propTypes = {
  input: React.PropTypes.object.isRequired,
  name: React.PropTypes.string.isRequired,
  label: React.PropTypes.string.isRequired,
  type: React.PropTypes.string.isRequired,
  readOnly: React.PropTypes.bool,
  meta: React.PropTypes.shape({
    touched: React.PropTypes.bool.isRequired,
    error: React.PropTypes.string
  }).isRequired
};

AddCommunityForm.propTypes = {
  ...propTypes,
  username: React.PropTypes.string,
  addCommunity: React.PropTypes.func.isRequired
};
