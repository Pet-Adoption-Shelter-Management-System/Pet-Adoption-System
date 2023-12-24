import React, { useState } from "react";
import Form from "../Components/Form";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [responseStatus, setResponseStatus] = useState("");

  //TODO modify the loginRequest to handle the shelter for the manager
  //function to direct us to the login using credential handeling
  const getLogInCredentials = (customer: LoginRequest) => {
    handelLoginBasicCredentialsRequest(customer);
  };

  function handelLoginBasicCredentialsRequest(customer: LoginRequest) {
    //TODO
  }

  return <Form isLogin={true} getLogInCredentials={getLogInCredentials} />;
};

export default Login;
