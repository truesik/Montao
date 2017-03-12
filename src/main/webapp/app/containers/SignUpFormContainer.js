import { reduxForm, SubmissionError } from 'redux-form';

import { getCookie } from '../utils/cookie';

import SignUpForm from '../components/SignUpForm';

const validate = (values) => {
  const errors = {};
  if (!values.username) {
    errors.username = 'Require';
  } else if (values.username.length < 4) {
    errors.username = 'Please enter at least 4 characters';
  }
  if (!values.email) {
    errors.email = 'Require';
  } else if (!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(values.email)) {
    errors.email = 'Please enter a valid email address';
  }
  if (!values.password) {
    errors.password = 'Required';
  } else if (values.password.length < 8) {
    errors.password = 'Please enter at least 8 characters';
  }
  if (!values.confirmPassword) {
    errors.confirmPassword = 'Require';
  } else if (values.confirmPassword != values.password) {
    errors.confirmPassword = 'These passwords don\'t match';
  }
  return errors;
};

const asyncValidate = (values, dispatch, props, currentField) => {
  let oldErrors = props.asyncErrors || {};
  if (currentField === 'username') {
    return checkUsername(values, oldErrors, currentField);
  } else if (currentField === 'email') {
    return checkEmail(values, oldErrors, currentField);
  } else {
    // Check on submit until props.asyncErrors not empty
    return new Promise(resolve => resolve(oldErrors));
  }
};

const checkUsername = (values, oldErrors, currentField) => {
  const headers = new Headers();
  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
  headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
  const request = new Request('/api/users/check_username', {
    method: 'POST',
    body: `username=${values.username}`,
    headers: headers,
    credentials: 'same-origin'
  });
  return new Promise((resolve, reject) => {
    fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        } else {
          return response.text();
        }
      })
      .then(data => {
        if (data === 'false') {
          let newError = { username: 'This username already exist' };
          const combinedErrors = combineErrors(newError, oldErrors, currentField);
          reject(combinedErrors);
        } else {
          resolve(oldErrors);
        }
      })
      .catch(() => SubmissionError({ _error: 'Server failure!' }));
  });
};

const checkEmail = (values, oldErrors, currentField) => {
  const headers = new Headers();
  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
  headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
  const request = new Request('/api/users/check_email', {
    method: 'POST',
    body: `email=${values.email}`,
    headers: headers,
    credentials: 'same-origin'
  });
  return new Promise((resolve, reject) => {
    fetch(request)
      .then(response => {
        if (response.status != 200) {
          const error = new Error(response.statusText);
          error.response = response;
          throw error;
        } else {
          return response.text();
        }
      })
      .then(data => {
        if (data === 'false') {
          let newError = { email: 'This email already exist' };
          let combinedErrors = combineErrors(newError, oldErrors, currentField);
          reject(combinedErrors);
        } else {
          resolve(oldErrors);
        }
      })
      .catch(() => SubmissionError({ _error: 'Server failure!' }));
  });
};

const combineErrors = (newErrors, oldErrors, currentField) => {
  let oE = oldErrors;
  delete oE[currentField];
  return Object.assign(oE, newErrors);
};

export default reduxForm({
  form: 'signUpForm',
  validate,
  asyncValidate,
  asyncBlurFields: ['username', 'email']
})(SignUpForm);
