//npm install @react-oauth/google@latest
import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';

const GoogleLoginButton = () => {
  const clientId =
    '669544904652-boavvtjin3nqm73m4vqrpt7k5lmsioc0.apps.googleusercontent.com';

  return (
    <div>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(res) => {
            console.log(res);
          }}
          onFailure={(err) => {
            console.log(err);
          }}
        />
      </GoogleOAuthProvider>
    </div>
  );
};
export default GoogleLoginButton;
