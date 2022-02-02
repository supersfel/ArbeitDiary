import React from "react";
import { Link } from "react-router-dom";
import CheckToken from "../api/CheckToken";
import Header from "../components/Header";
import "../css/containers/OldProject.css";

function OldProject() {
  CheckToken();
  return (
    <>
      <Header />
      <div className="diary-container">
        <Link to="/newproject" className="diary-inner-box Make-diary-box">
          새 프로젝트
        </Link>

        <Link to={`project/2`} className="diary-inner-box Select-diary-box">
          기존 프로젝트1
        </Link>
        <div className="diary-inner-box Select-diary-box">기존 프로젝트2</div>
      </div>
    </>
  );
}

export default OldProject;
