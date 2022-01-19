import moment from "moment";
import React, { useState } from "react";
import "../css/components/Calendar.css";
import { MdChevronRight, MdChevronLeft } from "react-icons/md";

function Calendar() {
  const [getMoment, setMoment] = useState(moment());
  const today = getMoment; // today == moment()   입니다.

  const firstWeek = today.clone().startOf("month").week();
  const lastWeek =
    today.clone().endOf("month").week() === 1
      ? 53
      : today.clone().endOf("month").week();

  const calendarArr = () => {
    let result: any = []; //type 맞춰줘야함
    let week = firstWeek;

    for (week; week <= lastWeek; week++) {
      result = result.concat(
        <div className="calendarWeek" key={week}>
          {Array(7)
            .fill(0)
            // eslint-disable-next-line no-loop-func
            .map((data, index) => {
              let days = today
                .clone()
                .startOf("year")
                .week(week)
                .startOf("week")
                .add(index, "day"); //d로해도되지만 직관성

              if (moment().format("YYYYMMDD") === days.format("YYYYMMDD")) {
                return (
                  <div
                    className="calendarDay"
                    key={index}
                    style={{ backgroundColor: "var(--main-color)" }}
                  >
                    {days.format("D")}
                  </div>
                );
              } else if (days.format("MM") !== today.format("MM")) {
                return (
                  <div
                    className="calendarDay"
                    key={index}
                    style={{ backgroundColor: "#d2d2d2" }}
                  >
                    {days.format("D")}
                  </div>
                );
              } else {
                return (
                  <div className="calendarDay" key={index}>
                    {days.format("D")}
                  </div>
                );
              }
            })}
        </div>
      );
    }
    return result;
  };

  return (
    <div className="calendar">
      <div className="control">
        <button
          className="btn btn-calendar btn-beforeMonth"
          onClick={() => {
            setMoment(getMoment.clone().subtract(1, "month"));
          }}
        >
          <MdChevronLeft />
        </button>
        <span>{today.format("YYYY 년 MM 월")}</span>
        <button
          className="btn btn-calendar btn-afterMonth"
          onClick={() => {
            setMoment(getMoment.clone().add(1, "month"));
          }}
        >
          <MdChevronRight />
        </button>
      </div>

      <div className="calendarBody">
        <div className="calendarDays">
          <div className="calendarDayofWeek sunday">일</div>
          <div className="calendarDayofWeek">월</div>
          <div className="calendarDayofWeek">화</div>
          <div className="calendarDayofWeek">수</div>
          <div className="calendarDayofWeek">목</div>
          <div className="calendarDayofWeek">금</div>
          <div className="calendarDayofWeek saturday">토</div>
        </div>
        {calendarArr()}
      </div>
    </div>
  );
}

export default Calendar;
