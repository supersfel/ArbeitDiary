import React from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import CheckToken, { CheckTokenMoveHome } from "../api/CheckToken";
import Header from "../components/Header";
import "../css/containers/NewProject.css";
import { addProject } from "../module/User";

function NewProject() {
  CheckTokenMoveHome();

  const dispatch = useDispatch();
  const history = useHistory();

  const makeNewProject = (e: any) => {
    e.preventDefault();
    dispatch(addProject(e.target.Name.value));
    history.push("/oldproject");
  };

  return (
    <>
      <Header />
      <div className="NewProject">
        <form onSubmit={makeNewProject}>
          <div className="Stepbox">
            매장명 하나면 충분합니다!
            <input
              type="text"
              placeholder="매장명을 입력하세요"
              id="Name"
              className="question"
            ></input>
          </div>
          <button className="makeProject">만들기!</button>
        </form>
      </div>
    </>
  );
}

export default NewProject;
