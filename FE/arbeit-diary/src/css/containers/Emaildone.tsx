import React from "react";
import { useLocation } from "react-router";
import { Emailrequestapi } from "../../api/UserApi";
function Emaildone() {
  const location = useLocation();

  const id = location.search.slice(4);
  console.log(id);

  Emailrequestapi({ id });

  return <div></div>;
}

export default Emaildone;
