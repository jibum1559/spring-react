//npm install react-kakao-login
//ddf75d929aba537832267e3b0a569adb
import KakaoLogin from 'react-kakao-login';
import kakaoLoginImg from '../img/kakao_login.png';
const KakaoApp = () => {
  const KakaoLoginSuccess = (res) => {
    console.log(res);
  };
  const KakaoLoginFailure = (err) => {
    console.error(err);
  };
  return (
    <div>
      <KakaoLogin
        token="ddf75d929aba537832267e3b0a569adb"
        onSuccess={KakaoLoginSuccess}
        onFailure={KakaoLoginFailure}
        //getProfile={true}
        render={(props) => (
          <button onClick={props.onClick}>
            <img src={kakaoLoginImg} />
          </button>
        )}
      />
    </div>
  );
};
export default KakaoApp;
