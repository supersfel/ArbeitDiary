import { deprecated } from "typesafe-actions";

export const GET_USER = "User/GET_USER";
export const ADD_ID = "User/ADD_ID" as const;
export const ADD_PROJECT = "User/ADD_PROJECT" as const;

export const getUser = deprecated.createStandardAction(GET_USER)();

export const addProject = (id: string) => ({
  type: ADD_PROJECT,
  payload: id,
});

export const addId = (Name: string) => ({
  type: ADD_ID,
  payload: Name,
});
