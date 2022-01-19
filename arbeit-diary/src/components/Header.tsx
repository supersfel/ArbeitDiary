import React from "react";
import { Link } from "react-router-dom";
import "../css/components/Header.css";

function Header() {
  return (
    <>
      <header className="Header">
        <Link to="/" className="left">
          로고
        </Link>
        <Link to="/login" className="right">
          로그인
        </Link>
      </header>
    </>
  );
}

export default Header;
