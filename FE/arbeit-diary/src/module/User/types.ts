import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type UserAction = ActionType<typeof actions>;

export type UserListType = {
  done?: boolean;
  userName: string;
  userId: string;
};

export type projectType = {
  projectId: string;
  projectName: string;
  calendarId: string;
  userList: UserListType[];
};

export type Usertype = {
  id: string;
  projects: projectType[];
};

export type User = Usertype[];
