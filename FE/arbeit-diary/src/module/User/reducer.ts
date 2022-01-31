import { createReducer } from "typesafe-actions";
import { GET_USER, ADD_ID } from "./actions";
import { UserAction, User } from "./types";

const initialState: User = [{ id: "1" }];

const Userinfo = createReducer<User, UserAction>(initialState, {
  [GET_USER]: (state, action) => state,
  [ADD_ID]: (state, action) =>
    state.map((user) => ({ ...user, id: action.payload })),
});

export default Userinfo;
