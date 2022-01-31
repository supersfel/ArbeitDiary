import { deprecated } from "typesafe-actions";

export const GET_USER = "User/GET_USER";

//export const getUser = deprecated.createStandardAction(GET_USER)();

export const getUser = () => ({
  type: GET_USER,
});
