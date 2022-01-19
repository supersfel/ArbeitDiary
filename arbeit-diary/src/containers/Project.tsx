import React from "react";
import Calendar from "../components/Calendar";
import Header from "../components/Header";
import UserList from "../components/UserList";
import "../css/containers/Project.css";

function Project() {
  return (
    <>
      <Header />
      <div className="project">
        <UserList />
        <Calendar />
      </div>
    </>
  );
}

export default Project;
