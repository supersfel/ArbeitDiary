import axios from "axios";

type UserApiprops = {
  userId: string;
  userPassword: string;
  userName?: string;
  userPhone?: string;
};

function UserApi(params: UserApiprops) {
  axios({
    method: "post",
    url: "http://localhost:8080/api/login",
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
}

export default UserApi;
