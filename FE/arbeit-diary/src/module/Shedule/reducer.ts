import { createReducer } from "typesafe-actions";
import { ScheduleAction, SchedulesType } from "./types";
import { TEST } from "./actions";

const initialState: SchedulesType = [
  {
    id: "1",
  },
];

const SheduleInfo = createReducer<SchedulesType, ScheduleAction>(initialState, {
  [TEST]: (state, action) =>
    state.map((shedule) => ({ ...shedule, id: action.payload })),
});

export default SheduleInfo;
