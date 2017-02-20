import { reduxForm } from 'redux-form';

import LogInForm from '../components/LogInForm';

const validate = (values) => {
  const errors = {};
  if (!values.username) {
    errors.username = 'Required';
  }
  if (!values.password) {
    errors.password = 'Required';
  }
  return errors;
};

export default reduxForm({
  form: 'logInForm',
  validate
})(LogInForm);
