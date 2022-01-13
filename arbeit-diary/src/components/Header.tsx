import React from "react";
import { Link } from "react-router-dom";
import "../css/components/Header.css";

function Header() {
  return (
    <>
      <header className="Header">
        <div className="left">로고</div>
        <Link to="/login" className="right">
          로그인
        </Link>
      </header>
    </>
  );
}

export default Header;
