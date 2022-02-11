import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { checkEffectiveToken, CheckTokenMoveHome } from "../api/CheckToken";
import { PostApi } from "../api/UserApi";
import Header from "../components/Header";
import "../css/containers/NewProject.css";
import { addProject } from "../module/User";

function NewProject() {
  CheckTokenMoveHome();
  const dispatch = useDispatch();
  const history = useHistory();
  useEffect(() => {
    checkEffectiveToken(dispatch, history);
  }, []);

  const makeNewProject = async (e: any) => {
    const token = localStorage.getItem("token");

    e.preventDefault();
    dispatch(addProject(e.target.Name.value));
    const api = await PostApi(
      "http://localhost:8080/api/newproject",
      token !== null ? token : "",
      { projectName: e.target.Name.value }
    )
      .then(() => {
        history.push("/oldproject");
      })
      .catch((e) => {
        console.log("makeNewProject Error!!!");
      });
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
