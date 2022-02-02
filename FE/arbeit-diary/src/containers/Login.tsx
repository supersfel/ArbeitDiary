import React from "react";
import { Link, useHistory } from "react-router-dom";
import { UserApi } from "../api/UserApi";

import "../css/containers/Login.css";

function Login(): JSX.Element {
  const history = useHistory();
  const onSubmit = (e: any) => {
    e.preventDefault();
    UserApi({
      userId: e.target.email.value,
      userPassword: e.target.password.value,
    }).then(() => {
      const token = localStorage.getItem("token");
      if (token !== null) {
        history.push("/");
      } else {
        alert("로그인 정보가 없습니다");
        history.push("/login");
      }
    });
  };

  return (
    <>
      <div className="Login-body">
        <link
          rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossOrigin="anonymous"
        />
        <form className="form-signin" id="form-login" onSubmit={onSubmit}>
          <h1 className="h3 mb-3 font-weight-normal">로그인 정보 입력</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="email"
            id="email"
            className="form-control"
            placeholder="이메일 주소"
            required
            autoFocus
          />
          <label htmlFor="inputPassword" className="sr-only">
            Password
          </label>
          <input
            type="password"
            id="password"
            className="form-control"
            placeholder="비밀번호"
            required
          />

          <button className="btn-login" type="submit">
            로그인
          </button>

          <div className="find">
            <Link to="#">아이디 찾기</Link>
            <Link to="#">비밀번호 찾기</Link>
            <Link to="/regist">회원가입</Link>
          </div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
    </>
  );
}

export default Login;
