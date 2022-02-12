import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useLocation } from "react-router";
import { checkEffectiveToken, CheckTokenMoveHome } from "../api/CheckToken";

import Calendar from "../components/Calendar";
import FixedSchedule from "../components/FixedSchedule";
import Header from "../components/Header";
import ModalJoinProject from "../components/ModalJoinProject";
import UserList from "../components/UserList";
import "../css/containers/Project.css";
import { RootState } from "../module";
import DayDetail from "./DayDetail";

function Project() {
  CheckTokenMoveHome();
  const user = useSelector((state: RootState) => state.Userinfo)[0];

  const dispatch = useDispatch();
  const history = useHistory();
  useEffect(() => {
    checkEffectiveToken(dispatch, history);
  }, []);

  const location = useLocation();
  const projectId = location.search.slice(11);

  const [visible, setvisible] = useState(false); //토글방식
  const [JoinModalvisible, setJoinModalvisible] = useState(false);
  const [FixedSchedulevisible, setFixedSchedulevisible] = useState(false);
  const [selectedDay, setselectedDay] = useState(""); //날짜전달
  const [userfixedInfo, setuserfixedInfo] = useState({
    userId: "",
    name: "",
    fixedtimes: [],
  });

  const projects = user.projects;
  const constproject = projects.filter(
    (project) => project.projectId === projectId
  )[0]; //해당 id의 userList 추출

  /*새로고침 오류 방지 */
  if (constproject === undefined) {
    return <></>;
  }

  const onConfirmDay = (days: moment.Moment) => {
    setselectedDay(days.format("YYYY년 MM월 DD일"));
  };

  const onCancel = () => setvisible(false);
  const onConfirm = () => setvisible(true);

  const onCancelJoinModal = () => setJoinModalvisible(false);
  const onActiveJoinModal = () => setJoinModalvisible(true);

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
          onActiveJoinModal={onActiveJoinModal}
          setFixedSchedulevisible={setFixedSchedulevisible}
          setuserfixedInfo={setuserfixedInfo}
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
        projectRole={
          constproject.projectRole === undefined ? "" : constproject.projectRole
        }
      />
      <ModalJoinProject
        visible={JoinModalvisible}
        projectId={projectId}
        onCancelJoinModal={onCancelJoinModal}
      />

      <FixedSchedule
        userfixedInfo={userfixedInfo}
        FixedSchedulevisible={FixedSchedulevisible}
        setFixedSchedulevisible={setFixedSchedulevisible}
      />
    </>
  );
}

export default Project;
