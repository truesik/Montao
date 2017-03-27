import { reduxForm } from 'redux-form';

import { getCookie } from '../utils/cookie';

import AddChannelForm from '../components/AddChannelForm';

const validate = (values) => {
  const errors = {};
  if (!values.title) {
    errors.title = 'Require';
  } else if (values.title.length < 4) {
    errors.title = 'Please enter at least 4 characters';
  }
  if (!values.communityTitle) {
    errors.communityTitle = 'Require';
  }
  return errors;
};

const asyncValidate = (values) => {
  const headers = new Headers();
  headers.append('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
  headers.append('X-XSRF-TOKEN', getCookie('XSRF-TOKEN'));
  const data = `channelTitle=${values.title}&communityTitle=${values.community}`;
  const request = new Request('/api/channels/check_title', {
    method: 'POST',
    body: data,
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
  form: 'addChannelForm',
  validate,
  asyncValidate,
  asyncBlurFields: ['title']
})(AddChannelForm);
