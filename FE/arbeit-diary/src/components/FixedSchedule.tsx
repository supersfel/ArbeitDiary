import React from "react";
import { useSelector } from "react-redux";
import "../css/components/FixedSchedule.css";
import { RootState } from "../module";
import { userListType } from "../module/Calendar/types";

type fixedScheduleProps = {
  userfixedInfo: userListType;
  FixedSchedulevisible: boolean;
  setFixedSchedulevisible: (value: boolean) => void;
};

type FixedscheduletimeProps = {
  worktimes: string;
};

function FixedSchedule({
  userfixedInfo,
  FixedSchedulevisible,
  setFixedSchedulevisible,
}: fixedScheduleProps) {
  if (!FixedSchedulevisible) return <></>;

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

  const { userId, name, fixedtimes } = userfixedInfo;

  const FixedScheduletime = ({ worktimes }: FixedscheduletimeProps) => {
    const worktimelst = worktimes.split("");

    return (
      <div className="worktimes">
        {worktimelst.map((worktime, index) => {
          return worktime === "1" ? (
            <div
              className="time work touch"
              key={index}
              style={{ background: "var(--main-color)" }}
            ></div>
          ) : (
            <div className="time touch" key={index}></div>
          );
        })}
      </div>
    );
  };

  return (
    <>
      <div
        className="fixedSchedule"
        onClick={() => setFixedSchedulevisible(false)}
      >
        <div
          className="settingBox shadowBox "
          onClick={(event) => event.stopPropagation()}
        >
          {name} 님의 고정근무 설정
          <div className="settingBoxUp schedule shadowBox">
            <div className="times column-line fixedtimeline">
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
            {fixedtimes.map((day, index) => {
              return (
                <div className="times columnline fixedtimeline" key={index}>
                  <div className="first-row-line">
                    {day.dayId.slice(0, 3).toUpperCase()}
                  </div>
                  <FixedScheduletime worktimes={day.worktime} />
                </div>
              );
            })}
          </div>
          <div className="settingBoxDown">
            <div className="settingBoxbtn btn">저장</div>
            <div
              className="settingBoxbtn btn"
              onClick={() => setFixedSchedulevisible(false)}
            >
              취소
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default FixedSchedule;
