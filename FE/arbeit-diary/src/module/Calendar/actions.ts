import {
  addDetailPropsType,
  removeDetailPropsType,
  toggleDetailPropsType,
} from "./types";

export const TEST = "Calender/TEST" as const;
export const ADD_DATE = "Calendar/ADD_DATE" as const;
export const ADD_DETAIL = "Calendar/ADD_DETAIL" as const;
export const REMOVE_DETAIL = "Calendar/REMOVE_DETAIL" as const;
export const TOGGLE_DETAIL = "Calendar/TOGGLE_DETAIL" as const;
//export const getUser = deprecated.createStandardAction(GET_USER)();

export const onTest = (id: string) => ({
  type: TEST,
  payload: id,
});

export const addDate = (date: string) => ({
  type: ADD_DATE,
  payload: date,
});

export const addDetail = ({ date, text, name, time }: addDetailPropsType) => ({
  type: ADD_DETAIL,
  payload: {
    date,
    text,
    name,
    time,
  },
});

export const removeDetail = ({ name, text }: removeDetailPropsType) => ({
  type: REMOVE_DETAIL,
  payload: {
    name,
    text,
  },
});

export const toggleDetail = ({ date, index, name }: toggleDetailPropsType) => ({
  type: TOGGLE_DETAIL,
  payload: {
    date,
    index,
    name,
  },
});
