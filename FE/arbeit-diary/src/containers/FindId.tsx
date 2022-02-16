import React from "react";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import CheckToken from "../api/CheckToken";

function FindId() {
  const history = useHistory();
  const dispatch = useDispatch();
  const onSubmit = (e: any) => {
    e.preventDefault();
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
          <h1 className="h3 mb-3 font-weight-normal">아이디 찾기</h1>
          <label htmlFor="inputEmail" className="sr-only">
            Email address
          </label>
          <input
            type="text"
            id="name"
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
            id="phone"
            className="form-control"
            placeholder="핸드폰 번호"
            required
          />

          <button className="btn-login" type="submit">
            아이디 찾기
          </button>

          <div className="find">
            <Link to="/login">로그인</Link>
            <Link to="/findpassword">비밀번호 찾기</Link>
            <Link to="/regist">회원가입</Link>
          </div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
    </>
  );
}

export default FindId;
