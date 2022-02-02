import React from "react";
import CheckToken from "../api/CheckToken";
import Header from "../components/Header";
import "../css/containers/NewProject.css";

function NewProject() {
  CheckToken();

  return (
    <>
      <Header />
      <div className="NewProject">
        <div className="Stepbox">
          매장명 하나면 충분합니다!
          <input
            type="text"
            placeholder="매장명을 입력하세요"
            className="question"
          ></input>
        </div>
        <button className="makeProject">만들기!</button>
      </div>
    </>
  );
}

export default NewProject;
