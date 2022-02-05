export const TEST = "Schedule/TEST" as const;
//export const getUser = deprecated.createStandardAction(GET_USER)();

export const onTest = (id: string) => ({
  type: TEST,
  payload: id,
});
