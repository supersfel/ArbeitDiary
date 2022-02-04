import { useHistory } from "react-router";

export function CheckToken() {
  //token이 있으면 true반환
  const token = localStorage.getItem("token");
  if (!token || token === "undefined") {
    return false;
  } else return true;
}

export function CheckTokenMoveHome() {
  const history = useHistory();
  const token = localStorage.getItem("token");
  if (!token || token === "undefined") {
    alert("로그인 토큰이 만료되었습니다");
    history.push("/");
  }
}

export default CheckToken;
