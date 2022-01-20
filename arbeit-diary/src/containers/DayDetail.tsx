import React, { useState } from "react";
import "../css/containers/DayDetail.css";

type DaydetailProps = {
  visible: boolean;
  onCancel: () => void;
  selectedDay: string;
};

function DayDetail({ visible, onCancel, selectedDay }: DaydetailProps) {
  if (!visible) return null;

  return (
    <div
      className={"background" + (visible ? " visible" : "")}
      onClick={(event) => {
        onCancel();
      }}
    >
      <div className="dayDetail" onClick={(event) => event.stopPropagation()}>
        {/*이벤트 캡쳐링 방지 */}
        <button onClick={onCancel}>취소</button>
        <p>{selectedDay}</p>
      </div>
    </div>
  );
}

export default DayDetail;
