import { combineReducers } from "redux";
import Userinfo from "./User";

const rootReducer = combineReducers({
  Userinfo,
});

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;
