import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type CalenderAction = ActionType<typeof actions>;

type userType = {
  name: string;
  worktime: string;
};

export type dayIssueType = {
  name: string;
  time: string;
  text: string;
};

type dateType = {
  date: string;
  users: userType[];
  dayIssues: dayIssueType[];
};

export type CalenderType = {
  calendarId: string;
  dates: dateType[];
};

export type CalendersType = CalenderType[];
