import React from 'react';
import GoogleLogin from 'react-google-login';
import '../css/Login.css'
import Header from './Layout/MainHeader.js';
import { Navigate } from 'react-router-dom';

export default function Login() {

  const [loginData, setLoginData] = React.useState({});
  const [loginSuccess, setLoginSuccess] = React.useState(false);
  const [token, setToken] = React.useState("");

  const handleFailure = (result) => {
    alert(result);
  };

  const handleLogin = async (googleData) => {
     const data = {
       token_id: googleData.profileObj.email,
       email: googleData.profileObj.email,
       notify: true
     };
     console.log(JSON.stringify(data));
    setLoginData(JSON.stringify(data));

    setToken(googleData.tokenId);
      
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };
        const r = await fetch('http://localhost:9080/api/User', requestOptions)
            .then(response => response.json())
            .then(res => console.log(res));

    //Store token_id in localStorage so other components can access it
    localStorage.setItem("token_id", googleData.profileObj.email);
 
    setLoginSuccess(true);
    
  };

  const handleLogout = () => {
    localStorage.removeItem('loginData');
    setLoginData(null);
  };

  return (

    <div className = "App">
    <Header />
      <header className = "App-header">
        <h1>Welcome to Positivitea</h1>
       <p><i>Mission: We created this site to encourage sharing with friends and loved ones in positive thoughts and
        expereinces to improve mental health. If you are suffering from serious depression, anxiety, or other mental
        emotional concerns, consider contacting a serious professional or sharing with those who help you feel safe</i></p>

        <h4> Log in </h4>
        <div>
          {
          <GoogleLogin
            clientId = {'328123063846-dlhla9cpovcucsoc5d78t7kf75g5imp8.apps.googleusercontent.com'}
            buttonText = "Log in with Google"
            onSuccess = {handleLogin}
            onFailure = {handleFailure}
            cookiePolicy = {'single_host_origin'}
          ></GoogleLogin>
        }
        {loginSuccess ?
        <Navigate to="/all-jars" /> : null
      }
        </div>
      </header>
    </div>
  )
}