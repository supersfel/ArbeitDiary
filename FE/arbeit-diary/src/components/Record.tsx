import React from "react";
import "../css/components/Record.css";
import { MdAdd } from "react-icons/md";
import { useSelector } from "react-redux";
import { RootState } from "../module";
import { dayIssueType } from "../module/Calendar";

type RecordProps = {
  selectedDay: string;
};

function Record({ selectedDay }: RecordProps) {
  const day =
    selectedDay.slice(0, 4) +
    "-" +
    selectedDay.slice(6, 8) +
    "-" +
    selectedDay.slice(10, 12);
  const today = new Date(day).getDay();
  const week = new Array(
    "일요일",
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일"
  ); //요일구하기
  const currentdate = selectedDay.replace(/[^0-9]/g, ""); //현재날짜출력
  const issues = useSelector(
    //users목록 추출
    (state: RootState) => state.CalenderInfo
  )[0].dates.filter((date) => date.date === currentdate)[0].dayIssues;

  console.log(issues);

  const DayIssue = ({ name, time, text }: dayIssueType) => {
    return (
      <div className="issuebox">
        <div>{text}</div>
        <div className="issuedetail">
          <div>{name}&nbsp;</div>
          <div>{time}</div>
        </div>
      </div>
    );
  };

  return (
    <div className="record">
      <div className="record-header">
        <div className="selectedDay">{selectedDay}</div>
        <div className="day-of-week">{week[today]}</div>
      </div>
      <div className="record-body">
        {issues.map((issue) => {
          return DayIssue(issue);
        })}
      </div>
      <div className="plusBox">
        <MdAdd />
      </div>
    </div>
  );
}

export default Record;
