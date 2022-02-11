import { useHistory } from "react-router";
import { getUserinfoApi } from "./UserApi";

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
    history.push("/");
  }
}

export async function checkEffectiveToken(dispatch: any, history: any) {
  const token = localStorage.getItem("token");
  const response = await getUserinfoApi(
    "http://localhost:8080/api/oldproject",
    token !== null ? token : "",
    dispatch
  );
  if (response === false) {
    alert("만료된 토큰입니다.");
    localStorage.clear();
    history.push("/");
  }
}

export default CheckToken;
