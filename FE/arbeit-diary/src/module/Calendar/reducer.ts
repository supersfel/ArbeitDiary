import { createReducer } from "typesafe-actions";
import { CalenderAction, CalendersType } from "./types";
import { TEST, ADD_DATE } from "./actions";

const initialState: CalendersType = [
  {
    calendarId: "1",
    dates: [
      {
        date: "20220205",
        users: [
          {
            name: "정민규",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "농땡이놈",
            worktime: "000000000000000000000000000000000000000000000000",
          },
        ],
      },
      {
        date: "20220206",
        users: [
          {
            name: "정민규",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
        ],
      },
    ],
  },
];

const CalenderInfo = createReducer<CalendersType, CalenderAction>(
  initialState,
  {
    [TEST]: (state, action) =>
      state.map((calendar) => ({ ...calendar, id: action.payload })),
    [ADD_DATE]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.concat({
          date: action.payload,
          users: [
            {
              name: "정민규",
              worktime: "000000000000000000000000000000000000000000000000",
            },
            {
              name: "박세연",
              worktime: "000000000000000000000000000000000000000000000000",
            },
          ],
        }),
      })),
  }
);

export default CalenderInfo;
