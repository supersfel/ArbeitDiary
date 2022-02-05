import { combineReducers } from "redux";
import Userinfo from "./User";
import ScheduleInfo from "./Shedule/reducer";

const rootReducer = combineReducers({
  Userinfo,
  ScheduleInfo,
});

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;
