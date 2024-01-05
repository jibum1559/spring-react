//npm install react-naver-login
import React from 'react';
import NaverLogin from 'react-naver-login';

const NaverLoginFontend = () => {
  const clientId = '8aA9mcsqN8544S_2bNZN';
  const NaverLoginSuccess = (res) => {
    console.log(res);
  };
  const NaverLoginFailure = (err) => {
    console.error(err);
  };
  return (
    <NaverLogin
      clientId={clientId}
      callbackUrl="http://localhost:3000/login/oauth2/code/naver"
      onSuccess={NaverLoginSuccess}
      onFailure={NaverLoginFailure}
      render={(props) => (
        //render={(props) : 사용자가 버튼을 클릭하는 것을 나타내는 추가 구문
        <button onClick={props.onClick}>네이버로 로그인</button>
      )}
    />
  );
};
export default NaverLoginFontend;
