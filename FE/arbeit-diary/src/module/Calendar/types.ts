import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type CalenderAction = ActionType<typeof actions>;

type userType = {
  name: string;
  worktime: string;
};

export type addDetailPropsType = {
  date: string;
  text: string;
  name: string;
  time: string;
  userId: string;
};

export type removeDetailPropsType = {
  name: string;
  text: string;
};

export type toggleDetailPropsType = {
  date: string;
  index: number;
  name: string;
};

export type addScheduleUserPropsType = {
  date: string;
  name: string;
};

/* Object Type */

export type dayIssueType = {
  name: string;
  time: string;
  text: string;
  userId: string;
};

type dateType = {
  date: string;
  dateId?: string;
  dayId?: string;
  users: userType[];
  dayIssues: dayIssueType[];
};

type fixedtime = {
  dayId: string;
  worktime: string;
};

type userListType = {
  name: string;
  userId: string;
  fixedtimes: fixedtime[];
};

export type CalenderType = {
  calendarId: string;
  projectId?: string;
  projectName?: string;

  userList: userListType[];
  dates: dateType[];
};

export type CalendersType = CalenderType[];
