import { createReducer } from "typesafe-actions";
import { CalenderAction, CalendersType } from "./types";
import { REMOVE_DETAIL, TEST, ADD_DATE, ADD_DETAIL } from "./actions";

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
        dayIssues: [
          {
            name: "정민규",
            time: "08:50",
            text: "빵이 상해떠요",
          },
          {
            name: "박세연",
            time: "15:30",
            text: "확인",
          },
          {
            name: "박세연",
            time: "22:30",
            text: "가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사",
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
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            worktime: "000000000000000000000000000000001111111100000000",
          },
        ],
        dayIssues: [],
      },
    ],
  },
];

const CalenderInfo = createReducer<CalendersType, CalenderAction>(
  initialState,
  {
    [TEST]: (state, action) =>
      state.map((calendar) => ({ calendarId: "", dates: [], dayIssues: "" })),

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
          dayIssues: [],
        }),
      })),
    [ADD_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          dayIssues:
            date.date === action.payload.date
              ? date.dayIssues.concat({
                  name: action.payload.name,
                  time: action.payload.time,
                  text: action.payload.text,
                })
              : date.dayIssues,
        })),
      })),
    [REMOVE_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          dayIssues: date.dayIssues.filter(
            (dayissue) =>
              dayissue.name !== action.payload.name ||
              dayissue.text !== action.payload.text
          ),
        })),
      })),
  }
);

export default CalenderInfo;
