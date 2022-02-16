import React from "react";
import { Route } from "react-router-dom";
import Index from "./containers/Index";
import Login from "./containers/Login";
import Regist from "./containers/Regist";
import OldProject from "./containers/OldProject";
import Project from "./containers/Project";
import NewProject from "./containers/NewProject";
import Emaildone from "./containers/Emaildone";
import JoinProject from "./containers/JoinProject";
import FindId from "./containers/FindId";
import FindPassword from "./containers/FindPassword";

function App() {
  return (
    <div className="All">
      <Route path="/" exact component={Index} />
      <Route path="/login" component={Login} />
      <Route path="/regist" component={Regist} />
      <Route path="/oldproject" component={OldProject} />
      <Route path="/project" component={Project} />
      <Route path="/newproject" component={NewProject} />
      <Route path="/done" component={Emaildone} />
      <Route path="/joinproject" component={JoinProject} />
      <Route path="/findid" component={FindId} />
      <Route path="/findpassword" component={FindPassword} />
    </div>
  );
}

export default App;
