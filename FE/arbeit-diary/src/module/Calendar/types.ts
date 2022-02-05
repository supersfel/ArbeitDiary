import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type CalenderAction = ActionType<typeof actions>;

type userType = {
  name: string;
  worktime: string;
};

type dateType = {
  date: string;
  users: userType[];
};

export type CalenderType = {
  calendarId: string;
  dates: dateType[];
};

export type CalendersType = CalenderType[];
