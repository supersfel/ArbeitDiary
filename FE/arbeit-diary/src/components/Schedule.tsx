import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router";
import "../css/components/Schedule.css";
import { RootState } from "../module";
import { addDate } from "../module/Calendar";

type ScheduleProps = {
  selectedDay: string;
};

type UserScheduleProps = {
  worktimes: string;
  username: string;
};

function Schedule({ selectedDay }: ScheduleProps) {
  const dispatch = useDispatch();
  let hours = []; //00:00 ~ 23:30까지 생성
  for (let i = 0; i < 24; i++) {
    i < 10 ? hours.push("0" + String(i)) : hours.push(String(i));
  }
  const minutes = ["00", "30"];
  let times: string[] = [];
  hours.map((hour) => {
    minutes.map((minute) => {
      times.push(hour + ":" + minute);
    });
  });

  const currentdate = selectedDay.replace(/[^0-9]/g, ""); //현재날짜출력
  // dispatch(addDate(currentdate));
  // const testtt = useSelector((state: RootState) => state.CalenderInfo);
  // console.log(testtt);

  try {
    const test = useSelector(
      //users목록 추출
      (state: RootState) => state.CalenderInfo
    )[0].dates.filter((date) => date.date === currentdate)[0].users;
  } catch (error) {
    dispatch(addDate(currentdate));
  }

  const users = useSelector(
    //users목록 추출
    (state: RootState) => state.CalenderInfo
  )[0].dates.filter((date) => date.date === currentdate)[0].users;

  const UserShedule = ({ worktimes, username }: UserScheduleProps) => {
    const worktimelst = worktimes.split("");

    return (
      <div className="worktimes">
        {worktimelst.map((worktime, index) => {
          return worktime === "1" ? (
            <div className="time work" key={index}>
              {username}
            </div>
          ) : (
            <div className="time " key={index}></div>
          );
        })}
      </div>
    );
  };

  return (
    <div className="schedule">
      <div className="times column-line">
        <div className="first-row-line">시간</div>
        <div className="worktimes">
          {times.map((time, index) => {
            return (
              <div className="time" key={index}>
                {time}
              </div>
            );
          })}
        </div>
      </div>

      {users.map((user, index) => {
        return (
          <div className="column-line" key={index}>
            <div className="first-row-line">{user.name}</div>
            <UserShedule worktimes={user.worktime} username={user.name} />
          </div>
        );
      })}
    </div>
  );
}

export default Schedule;
