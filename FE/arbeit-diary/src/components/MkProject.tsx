import React from "react";
import { Link } from "react-router-dom";
import CheckToken from "../api/CheckToken";
import "../css/MkProject.css";

function MkProject() {
  return (
    <div className="MkProject">
      <div className="description">
        <span className="highlite">
          <div className="logo-title"></div>
        </span>{" "}
        <br />
        클릭으로 완성되는 스마트한 스케쥴러
      </div>
      <div className="project">
        <Link
          to={CheckToken() ? "/newproject" : "/login"}
          className="btn btn--new"
        >
          새 프로젝트
        </Link>
        <Link
          to={CheckToken() ? "/oldproject" : "/login"}
          className="btn btn--old"
        >
          내 프로젝트
        </Link>
        <Link
          to={CheckToken() ? "/joinproject" : "/login"}
          className="btn btn--join"
        >
          프로젝트 참여
        </Link>
      </div>
    </div>
  );
}

export default MkProject;
