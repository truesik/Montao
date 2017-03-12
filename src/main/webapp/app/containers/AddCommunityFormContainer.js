import { reduxForm } from 'redux-form';

import { getCookie } from '../utils/cookie';

import AddCommunityForm from '../components/AddCommunityForm';

const validate = (values) => {
  const errors = {};
  if (!values.title) {
    errors.title = 'Require';
  } else if (values.title.length < 4) {
    errors.title = 'Please enter at least 4 characters';
  }
  if (!values.founder) {
    errors.founder = 'Require';
  }
  return errors;
};

const asyncValidate = (values) => {
  const headers = new Headers();
  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
  headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
  const request = new Request('/api/communities/check_title', {
    method: 'POST',
    body: `communityTitle=${values.title}`,
    headers: headers,
    credentials: 'same-origin'
  });
  return fetch(request)
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
        throw { title: 'This title already exists' };
      }
    });
};

export default reduxForm({
  form: 'addCommunityForm',
  validate,
  asyncValidate,
  asyncBlurFields: ['title']
})(AddCommunityForm);
