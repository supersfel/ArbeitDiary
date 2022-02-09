import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation } from "react-router";
import { CheckTokenMoveHome } from "../api/CheckToken";
import { getUserinfoApi } from "../api/UserApi";
import Calendar from "../components/Calendar";
import Header from "../components/Header";
import UserList from "../components/UserList";
import "../css/containers/Project.css";
import { RootState } from "../module";
import DayDetail from "./DayDetail";

function Project() {
  CheckTokenMoveHome();
  const dispatch = useDispatch();
  useEffect(() => {
    const token = localStorage.getItem("token");
    getUserinfoApi(
      "http://localhost:8080/api/oldproject",
      token !== null ? token : "",
      dispatch
    );
  }, []);
  const location = useLocation();
  const projectId = location.search.slice(11);

  const [visible, setvisible] = useState(false); //토글방식
  const [selectedDay, setselectedDay] = useState(""); //날짜전달

  const user = useSelector((state: RootState) => state.Userinfo)[0];
  const projects = user.projects;

  const constproject = projects.filter(
    (project) => project.projectId === projectId
  )[0]; //해당 id의 userList 추출

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
        <UserList
          projectId={projectId}
          projects={projects}
          projectRole={
            constproject.projectRole === undefined
              ? ""
              : constproject.projectRole
          }
          currentUserId={user.userId}
        />
        <div className="projectRight">
          <div className="projectTitle">{constproject.projectName}</div>
          <Calendar onConfirm={onConfirm} onConfirmDay={onConfirmDay} />
        </div>
      </div>
      <DayDetail
        visible={visible}
        onCancel={onCancel}
        selectedDay={selectedDay}
      />
    </>
  );
}

export default Project;
