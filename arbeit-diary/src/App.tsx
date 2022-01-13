import React from "react";
import { Link, Route } from "react-router-dom";
import Index from "./containers/Index";
import Login from "./containers/Login";

const key: number = 1;

function App() {
  return (
    <>
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
    </>
  );
}

export default App;
