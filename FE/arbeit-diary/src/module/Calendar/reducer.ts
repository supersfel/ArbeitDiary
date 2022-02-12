import { createReducer } from "typesafe-actions";
import { CalenderAction, CalendersType } from "./types";
import {
  TOGGLE_DETAIL,
  REMOVE_DETAIL,
  ADD_DATE,
  ADD_DETAIL,
  ADD_SCHEDULE_USER,
  REMOVE_SCHEDULE_USER,
} from "./actions";

const initialState: CalendersType = [
  {
    calendarId: "1",
    projectId: "13",
    projectName: "서브웨이",
    userList: [
      {
        name: "정민규",
        userId: "supersfel@naver.com",
        fixedtimes: [
          {
            dayId: "Monday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Tuesday",
            worktime: "000000011111111000000000000000000000000000000000",
          },
          {
            dayId: "Wednesday",
            worktime: "000000000000000011111111000000000000000000000000",
          },
          {
            dayId: "Thursday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Friday",
            worktime: "000000000000000011111111000000000000000000000000",
          },
          {
            dayId: "Saturday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Sunday",
            worktime: "000000000000000011111111000000000000000000000000",
          },
        ],
      },
      {
        name: "박세연",
        userId: "parkseyeon99@naver.com",
        fixedtimes: [
          {
            dayId: "Monday",
            worktime: "000111110000000000000000000000001111111100000000",
          },
          {
            dayId: "Tuesday",
            worktime: "111110000000000000000111111110000000000000000000",
          },
          {
            dayId: "Wednesday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Thursday",
            worktime: "000000000000111111110000000000000000000000000000",
          },
          {
            dayId: "Friday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Saturday",
            worktime: "000000000011111111000000000000000000000000000000",
          },
          {
            dayId: "Sunday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
        ],
      },
      {
        name: "농땡이놈",
        userId: "어쩔티비@naver.com",
        fixedtimes: [
          {
            dayId: "Monday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Tuesday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Wednesday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Thursday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Friday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Saturday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            dayId: "Sunday",
            worktime: "000000000000000000000000000000001111111100000000",
          },
        ],
      },
    ],
    dates: [
      {
        date: "20220205",
        dateId: "155",
        dayId: "SATURDAY",
        users: [
          {
            name: "정민규",
            userId: "supersfel@naver.com",
            worktime: "000000000000000000000000000000001111111100000000",
          },
          {
            name: "박세연",
            userId: "parkseyeon99@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "농땡이놈",
            userId: "어쩔티비@naver.com",
            worktime: "000000000000000000000000000000000000000000000000",
          },
        ],
        dayIssues: [
          {
            name: "정민규",
            time: "08:50",
            text: "빵이 상해떠요",
            userId: "supersfel@naver.com",
          },
          {
            name: "박세연",
            time: "15:30",
            text: "확인",
            userId: "parkseyeon99@naver.com",
          },
          {
            name: "박세연",
            time: "22:30",
            text: "가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사",
            userId: "supersfel@naver.com",
          },
        ],
      },
      {
        date: "20220206",
        dayId: "SATURDAY",
        dateId: "133",
        users: [
          {
            name: "정민규",
            userId: "supersfel@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkseyeon99@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkseyeo9@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkseye9@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkseyeo@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkse99@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "paron99@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "park99@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parksey@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
          },
          {
            name: "박세연",
            userId: "parkseyeo@naver.com",
            worktime: "000000000000000000000000000111111111000000000000",
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
    [ADD_DATE]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.concat({
          date: action.payload,
          dateId: "3",
          dayId: "SATURDAY",
          users: [],
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
                  userId: action.payload.userId,
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
    [TOGGLE_DETAIL]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.map((user) =>
                  user.userId === action.payload.userId
                    ? user.worktime[action.payload.index] === "1"
                      ? {
                          ...user,
                          worktime:
                            user.worktime.slice(0, action.payload.index) +
                            "0" +
                            user.worktime.slice(action.payload.index + 1),
                        }
                      : {
                          ...user,
                          worktime:
                            user.worktime.slice(0, action.payload.index) +
                            "1" +
                            user.worktime.slice(action.payload.index + 1),
                        }
                    : { ...user }
                )
              : date.users,
        })),
      })),
    [ADD_SCHEDULE_USER]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.concat({
                  name: action.payload.name,
                  userId: action.payload.userId,
                  worktime: "000000000000000000000000000000000000000000000000",
                })
              : date.users,
        })),
      })),
    [REMOVE_SCHEDULE_USER]: (state, action) =>
      state.map((calendar) => ({
        ...calendar,
        dates: calendar.dates.map((date) => ({
          ...date,
          users:
            date.date === action.payload.date
              ? date.users.filter(
                  (user) => user.userId !== action.payload.userId
                )
              : date.users,
        })),
      })),
  }
);

export default CalenderInfo;
