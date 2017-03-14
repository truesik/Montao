import React from 'react';
import { Field, propTypes } from 'redux-form';

// You shall not pass (only letters, numbers and underscope)
const normalizeUsername = value => value.replace(/[\W]+/g, '');

const renderField = ({ input, name, label, type, meta: { touched, error } }) => {
  return (
    <div className={!touched || !error ? 'form-group' : 'form-group has-error'}>
      <label htmlFor={name}>{label}</label>
      <input {...input}
             placeholder={label}
             type={type}
             className="form-control"
             id={name}/>
      {touched && (error && <small className="help-block">{error}</small>)}
    </div>
  );
};

const SignUpForm = ({ error, handleSubmit, submitting, signUp }) => {
  return (
    <form method="post" onSubmit={handleSubmit(signUp)}>
      <Field name="username"
             component={renderField}
             type="text"
             label="Username"
             normalize={normalizeUsername}/>
      <Field name="email"
             component={renderField}
             type="email"
             label="Email"/>
      <Field name="password"
             component={renderField}
             type="password"
             label="Password"/>
      <Field name="confirmPassword"
             component={renderField}
             type="password"
             label="Confirm Password"/>
      {error && <span className="text-danger">{error}</span>}
      <button type="submit" className="pull-right btn btn-primary" disabled={submitting}>Register</button>
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

SignUpForm.propTypes = {
  ...propTypes,
  signUp: React.PropTypes.func.isRequired
};

export default SignUpForm;
