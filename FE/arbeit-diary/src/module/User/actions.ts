import { deprecated } from "typesafe-actions";

export const GET_USER = "User/GET_USER" as const;
export const ADD_ID = "User/ADD_ID" as const;

export const getUser = deprecated.createStandardAction(GET_USER)();

export const addId = (id: string) => ({
  type: ADD_ID,
  payload: id,
});
