import React from "react";
import { useDispatch, useSelector } from "react-redux";
import Header from "../components/Header";
import MkProject from "../components/MkProject";
import { RootState } from "../module";
import { onTest } from "../module/Shedule";

function Index() {
  const user = useSelector((state: RootState) => state.Userinfo);
  const schedule = useSelector((state: RootState) => state.ScheduleInfo);
  const dispatch = useDispatch();

  const Test = () => {
    dispatch(onTest("3"));
    console.log(schedule);
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
