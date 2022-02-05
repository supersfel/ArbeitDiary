import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type ScheduleAction = ActionType<typeof actions>;

export type ScheduleType = {
  id: string;
};

export type SchedulesType = ScheduleType[];
