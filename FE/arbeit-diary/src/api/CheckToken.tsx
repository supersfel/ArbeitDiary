import React from "react";
import { useHistory } from "react-router";

function CheckToken() {
  const history = useHistory();
  const token = localStorage.getItem("token");
  if (token === null) {
    alert("로그인 토큰이 만료되었습니다");
    history.push("/");
  }
}

export default CheckToken;
