import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUserinfoApi } from "../api/UserApi";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import { RootState } from "../module";
import { onTest } from "../module/Calendar";
import { get_userinfo } from "../module/User";

function Index() {
  const user = useSelector((state: RootState) => state.Userinfo);
  const calendar = useSelector((state: RootState) => state.CalenderInfo);
  const dispatch = useDispatch();

  const Test = () => {
    let token = localStorage.getItem("token");

    const userinfo = getUserinfoApi(
      "http://localhost:8080/api/oldproject",
      token !== null ? token : ""
    );

    dispatch(get_userinfo(userinfo));
  };

  return (
    <>
      <button onClick={Test}>test</button>
      <Header />
      <MkProject />
    </>
  );
}

export default Index;
