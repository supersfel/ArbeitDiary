import React from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import CheckToken, { CheckTokenMoveHome } from "../api/CheckToken";
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
      to={`project/${projectId}`}
      className="diary-inner-box Select-diary-box"
    >
      {projectName}
    </Link>
  );
}

function OldProject() {
  CheckTokenMoveHome();
  const { projects } = useSelector((state: RootState) => state.Userinfo)[0];
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
