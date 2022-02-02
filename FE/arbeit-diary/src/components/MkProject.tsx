import React from "react";
import { Link } from "react-router-dom";
import "../css/MkProject.css";

function MkProject() {
  const token = localStorage.getItem("token");

  return (
    <div className="MkProject">
      <div className="description">
        <span className="highlite">알바일기</span> <br />
        클릭으로 완성되는 스마트한 스케쥴러
      </div>
      <div className="project">
        <Link
          to={token === null ? "/login" : "/newproject"}
          className="btn btn--new"
        >
          새 프로젝트
        </Link>
        <Link
          to={token === null ? "/login" : "/oldproject"}
          className="btn btn--old"
        >
          기존 프로젝트
        </Link>
      </div>
    </div>
  );
}

export default MkProject;
