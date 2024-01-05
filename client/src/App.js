import GoogleApp from './component/GoogleApp';
import KakaoApp from './component/KakaoApp';
import NaverLoginFontend from './component/NaverLoginFronend';
import NaverLoginButton from './component/NaverLoginButtonBackend';
const App = () => {
  return (
    <div>
      <GoogleApp />
      <KakaoApp />
      <NaverLoginFontend />
      <NaverLoginButton />
    </div>
  );
};
export default App;
