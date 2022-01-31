import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import { RootState } from "../module";

function Index() {
  const user = useSelector((state: RootState) => state.Userinfo);
  const dispatch = useDispatch();
  {
    console.log(user[0].id);
  }

  return (
    <>
      <Header />
      <MkProject />
    </>
  );
}

export default Index;
