import React from "react";
import "../css/containers/Introduce.css";
function Introduce() {
  return (
    <div className="Introduce">
      <div className="comment">함께 만들어 나가는 ,</div>
      <div className="shadowBox intro">
        <div className="Box first">
          아르바이트생도
          <div className="staff">
            <img
              className="img"
              src="https://img.icons8.com/external-icongeek26-flat-icongeek26/100/000000/external-waiter-food-and-delivery-icongeek26-flat-icongeek26-1.png"
            />
            <img
              className="img"
              src="https://img.icons8.com/external-icongeek26-flat-icongeek26/100/000000/external-waiter-food-and-delivery-icongeek26-flat-icongeek26.png"
            />
          </div>
        </div>
        <div className="Box first">
          관리자도
          <img
            className="img"
            src="https://img.icons8.com/external-filled-outline-icons-maxicons/100/000000/external-boss-office-space-filled-outline-filled-outline-icons-maxicons.png"
          />
        </div>
      </div>
      <div className="comment">정말 간편한 ,</div>
      <div className="shadowBox intro">
        <div className="Box">
          손쉽게 클릭 한번으로 !
          <div className="makeprojectimg img"></div>
        </div>
      </div>
    </div>
  );
}

export default Introduce;
