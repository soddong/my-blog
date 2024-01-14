import React from 'react';
import '../../css/style.css'; 
import sohyunImage from '../../assets/sohyun.jpg';

const Home = () => {
  return (
    <main>
      <section className="main-content">
        <div className="profile">
          <img src={sohyunImage} alt="내 사진" />
          <div className="details">
            <h2>최소현</h2><br />
            <p>도전을 두려워하지 않는 백엔드 개발자 지망생 입니다</p>
          </div>
        </div>

        <div className="section-header-pink">
          <h2>Goal</h2>
        </div>
        <div className="goal-content">
          <div className="goal-section">
            <h2>#인생</h2>
            <div className="details">
              <p>목표가 있는 삶</p>
            </div>
          </div>
          <div className="goal-section">
            <h2>#개발자</h2>
            <div className="details">
              <p>다양한 경험으로 최적의 선택을 하는 개발자</p>
            </div>
          </div>
        </div><br />

        <div className="section-header-blue">
          <h2>Foot Print</h2>
        </div>
        <div className="footprint-content">
          <span className="time-period">2017 ~ 2021</span>
          <span className="detail">OO대학교 OOOO공학과</span>
        </div>
        <div className="footprint-content">
          <span className="time-period">2021 ~ 2023</span>
          <span className="detail">OOOOO OOOOO팀</span>
        </div>
        <div className="footprint-content">
          <span className="time-period">2024 ~ ING</span>
          <span className="detail">SSAFY (Samsung Software Academy For Youth) 교육</span>
        </div>
        
        <div className="section-header-yellow">
          <h2>Project</h2>
        </div>
        <div className="project-content">
          <span className="time-period">2019.12 ~ 2020.10 (11m)</span>
          <span className="detail">창작자동차 경진대회 자율주행 부문</span>
        </div>
        <div className="project-content">
          <span className="time-period">2020.8 ~ 2021.1 (6m)</span>
          <span className="detail">V2X 기반 운전보조서비스 개발 [학부연구생]</span>
        </div>
      </section>
    </main>
  );
};

export default Home;
