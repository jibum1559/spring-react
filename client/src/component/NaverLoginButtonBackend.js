import React from 'react';
import axios from 'axios';

export default function NaverLoginButton() {
  const handleNaverLogin = async () => {
    try {
      const response = await axios.get(
        //주소를 변경하고 싶다면 package.json 에서 private : true 아래 "proxy" : "http://localhost:5000" 이렇게 작성
        //spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/login/login/oauth2/code/{registrationId} 이렇게 작성했으면 아래
        'http://localhost:8080/login/oauth2/code/naver',
        //'http://localhost:8080/oauth2/authrization/naver',
        { withCredentials: true }
      );
      console.log('FrontEnd에서 제공되는 콘솔 로그');
      console.log(response.data);
    } catch (error) {
      console.error('Naver login error : ', error);
    }
  };
  return (
    <div>
      <button onClick={handleNaverLogin}>naver Login</button>
    </div>
  );
}
