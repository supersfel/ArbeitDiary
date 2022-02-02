import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../css/components/Header.css";

function Header() {
  const [ScrollY, setScrollY] = useState(0); // window 의 pageYOffset값을 저장
  const [ScrollActive, setScrollActive] = useState(false);
  function handleScroll() {
    if (ScrollY > 10) {
      setScrollY(window.pageYOffset);
      setScrollActive(true);
    } else {
      setScrollY(window.pageYOffset);
      setScrollActive(false);
    }
  }
  useEffect(() => {
    function scrollListener() {
      window.addEventListener("scroll", handleScroll);
    } //  window 에서 스크롤을 감시 시작
    scrollListener(); // window 에서 스크롤을 감시
    return () => {
      window.removeEventListener("scroll", handleScroll);
    }; //  window 에서 스크롤을 감시를 종료
  });

  const token = localStorage.getItem("token");

  const onlogout = () => {
    if (token !== null) {
      localStorage.clear();
    }
  };

  return (
    <>
      <header className={"Header" + (ScrollActive ? " onScrolled" : "")}>
        <Link to="/" className="left">
          로고
        </Link>
        <Link
          to={token === null ? "/login" : "/"}
          onClick={onlogout}
          className="right"
        >
          {token === null ? "로그인" : "로그아웃"}
        </Link>
      </header>
    </>
  );
}

export default Header;