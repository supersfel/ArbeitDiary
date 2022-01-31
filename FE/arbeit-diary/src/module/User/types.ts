import { ActionType } from "typesafe-actions";
import * as actions from "./actions";

export type UserAction = ActionType<typeof actions>;

export type Usertype = {
  id: string;
};

export type User = Usertype[];
