import React from 'react';
import { Field, propTypes } from 'redux-form';

const renderField = ({ input, name, label, type, meta: { touched, error } }) => {
  return (
    <div className={!touched || !error ? 'form-group' : 'form-group has-error'}>
      <label htmlFor={name}>{label}</label>
      <input {...input} placeholder={label} type={type} className="form-control" id={name}/>
      {touched && (error && <small className="help-block">{error}</small>)}
    </div>
  );
};

const LogInForm = ({ error, handleSubmit, submitting, logIn }) => {
  return (
    <form method="post" onSubmit={handleSubmit(logIn)}>
      <Field name="username"
             component={renderField}
             type="text"
             label="Username"/>
      <Field name="password"
             component={renderField}
             type="password"
             label="Password"/>
      {error && <span className="text-danger">{error}</span>}
      <button type="submit"
              className="pull-right btn btn-primary"
              disabled={submitting}>
        Log In
      </button>
    </form>
  );
};

renderField.propTypes = {
  input: React.PropTypes.object.isRequired,
  name: React.PropTypes.string,
  label: React.PropTypes.string.isRequired,
  type: React.PropTypes.string.isRequired,
  meta: React.PropTypes.shape({
    touched: React.PropTypes.bool.isRequired,
    error: React.PropTypes.string
  }).isRequired
};

LogInForm.propTypes = {
  ...propTypes,
  logIn: React.PropTypes.func.isRequired
};

export default LogInForm;
