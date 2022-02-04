import React, { useState } from "react";
import { useLocation } from "react-router";
import CheckToken from "../api/CheckToken";
import Calendar from "../components/Calendar";
import Header from "../components/Header";
import UserList from "../components/UserList";
import "../css/containers/Project.css";
import DayDetail from "./DayDetail";

function Project() {
  CheckToken();
  const location = useLocation();
  const projectId = location.search.slice(11);

  const [visible, setvisible] = useState(false); //토글방식
  const [selectedDay, setselectedDay] = useState(""); //날짜전달

  const onConfirmDay = (days: moment.Moment) => {
    setselectedDay(days.format("YYYY년 MM월 DD일"));
  };

  const onCancel = () => {
    setvisible(false);
  };

  const onConfirm = () => {
    setvisible(true);
  };

  return (
    <>
      <Header />
      <div className="project">
        <UserList projectId={projectId} />
        <div className="projectRight">
          <div className="projectTitle">맘스터치 개봉점</div>
          <Calendar onConfirm={onConfirm} onConfirmDay={onConfirmDay} />
        </div>
      </div>
      <DayDetail
        // visible={visible}
        visible={true}
        onCancel={onCancel}
        selectedDay={selectedDay}
      />
    </>
  );
}

export default Project;
