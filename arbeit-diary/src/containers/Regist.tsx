import React, { CSSProperties, FormEvent, useEffect, useState } from "react";
import UserApi from "../api/UserApi";
import { Link } from "react-router-dom";
import "../css/containers/Regist.css";
import axios from "axios";

function Regist() {
  const [password, setPassword] = useState(""); // 비밀번호 일치확인
  const [confirmPassword, setConfirmPassword] = useState("");

  const onSubmit = (e: any) => {
    //e.preventDefault();
    UserApi({
      userId: e.target.email.value,
      userPassword: e.target.password.value,
      userName: e.target.name.value,
      userPhone: e.target.phone.value,
    });
  };

  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  const onChangeConfirmPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setConfirmPassword(e.target.value);
  };

  const uncorrectstyle: CSSProperties = {
    border: password !== confirmPassword ? "2px solid red" : "",
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
          <h1 className="h3 mb-3 font-weight-normal">회원가입 정보 입력</h1>

          <input
            type="email"
            id="email"
            className="form-control"
            placeholder="이메일 주소"
            required
            autoFocus
          />
          <input
            type="text"
            id="name"
            className="form-control"
            placeholder="이름"
            required
            autoFocus
          />
          <input
            type="text"
            id="phone"
            className="form-control"
            placeholder="핸드폰 번호"
            required
            autoFocus
          />
          <input
            type="password"
            id="password"
            className="form-control"
            placeholder="비밀번호"
            required
            autoFocus
            onChange={onChangePassword}
            style={uncorrectstyle}
          />
          <input
            type="password"
            id="password-check"
            className="form-control"
            placeholder="비밀번호 확인"
            required
            onChange={onChangeConfirmPassword}
            style={uncorrectstyle}
          />

          <button className="btn-login" type="submit">
            회원가입
          </button>
          <div className="find"></div>
          <p className="mt-5 mb-3 text-muted">&copy; 2022</p>
        </form>
      </div>
    </>
  );
}

export default Regist;
