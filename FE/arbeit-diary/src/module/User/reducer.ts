import { createReducer } from "typesafe-actions";
import { GET_USER } from "./actions";
import { UserAction, User } from "./types";

const initialState: User = [{ id: "1" }];

const Userinfo = createReducer<User, UserAction>(initialState, {
  [GET_USER]: (state, action) => state,
});

export default Userinfo;
