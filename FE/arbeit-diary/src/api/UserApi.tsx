import axios from "axios";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router";
import { get_userinfo, Usertype } from "../module/User";

type UserApiprops = {
  userId: string;
  userPassword: string;
};

type RegistApiprops = {
  userId: string;
  userPassword: string;
  userName: string;
  userPhone: string;
};

type EmailRequestApiprops = {
  id: string;
};

export async function getUserinfoApi(
  url: string,
  token: string,
  dispatch: any
) {
  try {
    await axios({
      method: "post",
      url,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
    }).then((response) => {
      dispatch(get_userinfo(response.data));
    });
  } catch (e) {
    console.log("get logintoken error!!");
  }
}

export async function UserApi(params: UserApiprops) {
  try {
    await axios({
      method: "post",
      url: "http://localhost:8080/api/login",
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      const { authorization } = response.headers;
      console.log(authorization);
      localStorage.setItem("token", authorization);
      console.log(response.data);
    });
  } catch (e) {
    console.log("get logintoken error!!");
  }
}

export async function RegistUserApi(params: RegistApiprops) {
  try {
    await axios({
      method: "post",
      url: "http://localhost:8080/api/userRegist",
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      const { memberRefreshToken } = response.data;
      console.log(memberRefreshToken);
      localStorage.setItem("token", memberRefreshToken);
      console.log(response.data);
    });
  } catch (e) {}
}

export async function Emailrequestapi(params: EmailRequestApiprops) {
  const history = useHistory();

  try {
    await axios({
      method: "post",
      url: "http://localhost:8080/api/emailAuth",
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
      },
      data: params,
    }).then((response) => {
      if (response.data) {
        history.push("/");
      } else {
        alert("올바르지 않은 인증입니다.");
      }
    });
  } catch (e) {
    alert("올바르지 않은 인증입니다.");
    history.push("/");
  }
}

type PostApiProps = {
  projectName: string;
};
export async function PostApi(
  url: string,
  token: string,
  params: PostApiProps
) {
  try {
    await axios({
      method: "post",
      url,
      headers: {
        'Accept': "application/json", //prettier-ignore
        'Content-Type': "application/json", //prettier-ignore
        Authorization: token,
      },
      data: params,
    }).then((response) => {
      console.log(response.data);
    });
  } catch (e) {
    console.log("error in make NewProjectApi");
  }
}
