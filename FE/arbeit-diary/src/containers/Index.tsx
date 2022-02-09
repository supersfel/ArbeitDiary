import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUserinfoApi } from "../api/UserApi";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import { RootState } from "../module";

function Index() {
  const user = useSelector((state: RootState) => state.Userinfo);
  const calendar = useSelector((state: RootState) => state.CalenderInfo);
  const dispatch = useDispatch();

  const Test = () => {
    let token = localStorage.getItem("token");

    getUserinfoApi(
      "http://localhost:8080/api/oldproject",
      token !== null ? token : "",
      dispatch
    );
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    getUserinfoApi(
      "http://localhost:8080/api/oldproject",
      token !== null ? token : "",
      dispatch
    );
  }, []);

  return (
    <>
      <button onClick={Test}>test</button>
      <Header />
      <MkProject />
    </>
  );
}

export default Index;
