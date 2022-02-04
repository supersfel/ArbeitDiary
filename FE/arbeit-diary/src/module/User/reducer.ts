import { createReducer } from "typesafe-actions";
import { GET_USER, ADD_ID, ADD_PROJECT } from "./actions";
import { UserAction, User } from "./types";

const initialState: User = [
  {
    id: "1",
    projects: [
      {
        projectId: "1",
        projectName: "서브웨이 연수점",
        calendarId: "firstcalendar",
        userList: [
          { userName: "정민규", userId: "supersfel@naver.com" },
          {
            userName: "박세연",
            userId: "parkseyeon99@naver.com",
          },
        ],
      },
      {
        projectId: "2",
        projectName: "맘스터치 개봉점",
        calendarId: "secondecalendar",
        userList: [
          { userName: "정민규", userId: "supersfel@naver.com" },
          {
            userName: "박세연",
            userId: "parkseyeon99@naver.com",
          },
        ],
      },
    ],
  },
];

const Userinfo = createReducer<User, UserAction>(initialState, {
  [GET_USER]: (state, action) => state,
  [ADD_ID]: (state, action) =>
    state.map((user) => ({ ...user, project: [], id: action.payload })),
  [ADD_PROJECT]: (state, action) =>
    state.map((user) => ({
      ...user,
      projects: user.projects.concat({
        projectId: "3",
        projectName: action.payload,
        calendarId: "3",
        userList: [{ userName: "정민규", userId: "supersfel@naver.com" }],
      }),
    })),
});

export default Userinfo;
