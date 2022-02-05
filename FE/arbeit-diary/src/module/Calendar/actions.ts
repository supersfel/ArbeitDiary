export const TEST = "Calender/TEST" as const;
export const ADD_DATE = "Calendar/ADD_DATE" as const;

//export const getUser = deprecated.createStandardAction(GET_USER)();

export const onTest = (id: string) => ({
  type: TEST,
  payload: id,
});

export const addDate = (date: string) => ({
  type: ADD_DATE,
  payload: date,
});
