import React from "react";
import "../css/MkProject.css";

function MkProject() {
  return (
    <div className="MkProject">
      <div className="description">
        클릭으로 완성되는 <br />
        스마트한 스케쥴러 <br />
        알바일기
      </div>
      <div className="project">
        <div className="btn btn--new">새 프로젝트</div>
        <div className="btn btn--old">기존 프로젝트</div>
      </div>
    </div>
  );
}

export default MkProject;
