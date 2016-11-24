import {reduxForm} from "redux-form";

import AddCommunityForm from "../components/AddCommunityForm";

const validate = (values) => {
    const errors = {};
    if (!values.title) {
        errors.title = 'Require';
    }
    if (!values.founder) {
        errors.founder = 'Require';
    }
    return errors;
};

export default reduxForm({
    form: 'addCommunityForm',
    validate
})(AddCommunityForm)