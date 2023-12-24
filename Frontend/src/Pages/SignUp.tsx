import React, { useState } from "react";
import Form from "../Components/Form";
import { useNavigate } from "react-router-dom";

const SignUp = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [responseStatus, setResponseStatus] = useState("");

  // const []
  //function to direct the flow to sign up using credentials
  const getSignUpCredentials = (customer: RegisterRequest) => {
    handelSignUpBasicCredentialsRequest(customer);
  };

  const handelSignUpBasicCredentialsRequest = async (customer: RegisterRequest) => {
    // TODO
  };

  const handelSignUpBasicCredentialsResponse = () => {
    //TODO
  };

  return <Form isLogin={false} getSignUpCredentials={getSignUpCredentials} />;
};

export default SignUp;
