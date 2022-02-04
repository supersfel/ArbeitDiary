//import { deprecated } from "typesafe-actions";

export const ADD_PROJECT = "User/ADD_PROJECT" as const;
export const TOGGLE_NAME = "User/TOGGLE_NAME" as const;
//export const getUser = deprecated.createStandardAction(GET_USER)();

export const addProject = (id: string) => ({
  type: ADD_PROJECT,
  payload: id,
});

export const toggleName = (name: string, projectId: string) => ({
  type: TOGGLE_NAME,
  name,
  projectId,
});
