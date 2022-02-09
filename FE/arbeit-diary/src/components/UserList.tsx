import React from "react";
import "../css/components/UserList.css";

import { MdDone, MdAdd } from "react-icons/md";

import classNames from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../module";
import { toggleName } from "../module/User";

type UserProps = {
  done: boolean;
  text: string;
  projectId: string;
};

type UserListProps = {
  projectId: string;
};

function User({ done, text, projectId }: UserProps) {
  const dispatch = useDispatch();
  const onclickUser = () => {
    dispatch(toggleName(text, projectId));
  };

  return (
    <div className="User" onClick={onclickUser}>
      <div className={classNames("checkBox", { checkBoxDone: done })}>
        {done && <MdDone />}
      </div>
      <div className={classNames("userText", { userTextDone: done })}>
        {text}
      </div>
      <MdAdd />
    </div>
  );
}

function UserList({ projectId }: UserListProps) {
  const { projects } = useSelector((state: RootState) => state.Userinfo)[0];

  const users = projects.filter((project) => project.projectId === projectId)[0]
    .userList; //해당 id의 userList 추출

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
          />
        );
      })}
      <div className="plusBox">
        <MdAdd />
      </div>
    </div>
  );
}

export default UserList;
