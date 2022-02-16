import React from "react";
import { Link } from "react-router-dom";
import { findpasswordapi } from "../api/UserApi";

function FindPassword() {
  const onSubmit = (e: any) => {
    e.preventDefault();
    findpasswordapi({
      userId: e.target.userId.value,
      userName: e.target.userName.value,
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
          <h1 className="h3 mb-3 font-weight-normal">비밀번호 찾기</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="text"
            id="userName"
            className="form-control"
            placeholder="이름"
            required
            autoFocus
          />
          <label htmlFor="inputPassword" className="sr-only">
            Password
          </label>
          <input
            type="text"
            id="userId"
            className="form-control"
            placeholder="아이디 (이메일)"
            required
          />

          <button className="btn-login" type="submit">
            비밀번호 찾기
          </button>

          <div className="find">
            <Link to="/findid">아이디 찾기</Link>
            <Link to="/regist">회원가입</Link>
          </div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
    </>
  );
}

export default FindPassword;
