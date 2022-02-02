import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import { RootState } from "../module";
import { addId } from "../module/User";

function Index() {
  const user = useSelector((state: RootState) => state.Userinfo);
  const dispatch = useDispatch();

  const onTest = () => {
    console.log(user[0].id);
    dispatch(addId("3"));
    console.log(user[0].id);
    console.log(localStorage.getItem("userId"));
    alert(localStorage.getItem("token"));
  };

  return (
    <>
      <button onClick={onTest}>test</button>
      <Header />
      <MkProject />
    </>
  );
}

export default Index;
