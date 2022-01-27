import React from "react";
import { Link, Route } from "react-router-dom";
import Index from "./containers/Index";
import Login from "./containers/Login";
import Regist from "./containers/Regist";
import OldProject from "./containers/OldProject";
import Project from "./containers/Project";
import IndexDone from "./containers/IndexDone";

function App() {
  return (
    <div className="All">
      <Route path="/" exact component={Index} />
      <Route path="/login" component={Login} />
      <Route path="/Regist" component={Regist} />
      <Route path="/oldproject" component={OldProject} />
      <Route path="/project" component={Project} />
      <Route path="/done" component={IndexDone} />
    </div>
  );
}

export default App;
