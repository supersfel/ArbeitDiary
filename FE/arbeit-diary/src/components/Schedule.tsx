import React from "react";
import "../css/components/Schedule.css";

function Schedule() {
  let hours = [];

  for (let i = 0; i < 24; i++) {
    i < 10 ? hours.push("0" + String(i)) : hours.push(String(i));
  }
  const minutes = ["00", "30"];
  //test
  let times: string[] = [];
  hours.map((hour) => {
    minutes.map((minute) => {
      times.push(hour + ":" + minute);
    });
  });
  //test
  return (
    <div className="schedule">
      <div className="times">
        <div>시간</div>
        {times.map((time, index) => {
          return (
            <div className="time" key={index}>
              {time}
            </div>
          );
        })}
      </div>
      <div className="usershedule">hi</div>
    </div>
  );
}

export default Schedule;
