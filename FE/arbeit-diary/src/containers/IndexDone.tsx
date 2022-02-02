import React from "react";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import Login from "./Login";

function IndexDone() {
  if (localStorage.getItem("token") === null) {
    return <Login />;
  }

  return (
    <>
      <Header />
      <MkProject />
    </>
  );
}

export default IndexDone;
