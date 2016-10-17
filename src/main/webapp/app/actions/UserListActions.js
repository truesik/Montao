import {GET_USERS} from "../constants/UserList";

export const getUsers = (users) => {
    return {
        type: GET_USERS,
        users
    }
};