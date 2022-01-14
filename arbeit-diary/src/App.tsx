import React from "react";
import { Link, Route } from "react-router-dom";
import Index from "./containers/Index";
import Login from "./containers/Login";
import Regist from "./containers/Regist";
import OldProject from "./containers/OldProject";

function App() {
  return (
    <div className="All">
      <ul>
        <li>
          <Link to="/">홈</Link>
        </li>
        <li>
          <Link to="/login">로그인</Link>
        </li>
      </ul>
      <hr />
      <Route path="/" exact component={Index} />
      <Route path="/login" component={Login} />
      <Route path="/Regist" component={Regist} />
      <Route path="/oldproject" component={OldProject} />
    </div>
  );
}

export default App;
