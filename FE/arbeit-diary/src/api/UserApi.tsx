import axios from "axios";

type UserApiprops = {
  userId: string;
  userPassword: string;
  userName?: string;
  userPhone?: string;
};

function UserApi(params: UserApiprops) {
  axios({
    method: "get",
    url: "http://localhost:8080/api/users",
    headers: {
      'Accept': "application/json", //prettier-ignore
      "Content-Type": "application/json",
    },
    params,
  }).then((response) => {
    console.log(response.data);
  });
}



export default UserApi;
