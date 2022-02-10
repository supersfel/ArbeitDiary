import React from "react";
import "../css/components/UserList.css";

import { MdDone, MdAdd } from "react-icons/md";

import classNames from "classnames";
import { useDispatch } from "react-redux";
import { projectType, toggleName } from "../module/User";
import { api, PostApi } from "../api/UserApi";
import { useHistory } from "react-router";

type UserProps = {
  done: boolean;
  text: string;
  projectId: string;
  projectRole: string;
  userId: string;
  currentUserId: string;
  joinId: string;
};

type UserListProps = {
  projectId: string;
  projects: projectType[];
  projectRole: string;
  currentUserId: string;
  onActiveJoinModal: () => void;
};

function User({
  done,
  text,
  projectId,
  projectRole,
  userId,
  currentUserId,
  joinId,
}: UserProps) {
  const dispatch = useDispatch();
  const history = useHistory();
  const onclickUser = () => {
    dispatch(toggleName(text, projectId));
  };
  const checkMaster = (projectRole: string) => {
    return projectRole === "MASTER" ? true : false;
  };

  const token = localStorage.getItem("token");

  const onRemoveUser = () => {
    PostApi(`${api}/api/deleteproject`, token === null ? "" : token, {
      joinId,
      targetId: userId,
    });
    history.push("/");
  };
  return (
    <div className="User">
      <div className="User-left">
        <div
          className={classNames("checkBox", { checkBoxDone: done })}
          onClick={onclickUser}
        >
          {done && <MdDone />}
        </div>
        <div
          className={classNames("userText", { userTextDone: done })}
          onClick={onclickUser}
        >
          {text}
        </div>
      </div>
      <div className="userdelete">
        {checkMaster(projectRole) === true || userId === currentUserId ? (
          <MdAdd onClick={onRemoveUser} />
        ) : (
          ""
        )}
      </div>
    </div>
  );
}

function UserList({
  projectId,
  projects,
  projectRole,
  currentUserId,
  onActiveJoinModal,
}: UserListProps) {
  const project = projects.filter(
    (project) => project.projectId === projectId
  )[0];

  const users = project.userList;

  return (
    <div className="UserList">
      <div className="UserList-title">사용자 목록</div>

      {users.map((user, index) => {
        return (
          <User
            key={index}
            done={user.done}
            text={user.userName}
            projectId={projectId}
            projectRole={projectRole}
            userId={user.userId}
            currentUserId={currentUserId}
            joinId={project.joinId === undefined ? "" : project.joinId}
          />
        );
      })}
      <div className="plusBox">
        <MdAdd onClick={onActiveJoinModal} />
      </div>
    </div>
  );
}

export default UserList;
