import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { CheckTokenMoveHome } from "../api/CheckToken";
import { getUserinfoApi } from "../api/UserApi";
import Header from "../components/Header";
import "../css/containers/OldProject.css";
import { RootState } from "../module";

type ProjecttitleProps = {
  projectId: string;
  projectName: string;
};

function Projecttitle({ projectId, projectName }: ProjecttitleProps) {
  return (
    <Link
      to={`project/?projectId=${projectId}`}
      className="diary-inner-box Select-diary-box"
    >
      {projectName}
    </Link>
  );
}

function OldProject() {
  CheckTokenMoveHome();
  const { projects } = useSelector((state: RootState) => state.Userinfo)[0];

  const dispatch = useDispatch();
  useEffect(() => {
    const token = localStorage.getItem("token");
    getUserinfoApi(
      "http://localhost:8080/api/oldproject",
      token !== null ? token : "",
      dispatch
    );
  }, []);

  return (
    <>
      <Header />
      <div className="diary-container">
        <Link to="/newproject" className="diary-inner-box Make-diary-box">
          새 프로젝트
        </Link>
        {projects.map((project, index) => {
          return (
            <Projecttitle
              key={index}
              projectId={project.projectId}
              projectName={project.projectName}
            />
          );
        })}
      </div>
    </>
  );
}

export default OldProject;
