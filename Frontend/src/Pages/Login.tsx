import React, { useState } from "react";
import Form from "../Components/Form";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [responseStatus, setResponseStatus] = useState("");

  // ----------------------- For the adopter/staff login------------------------------------------
  const getAdopterStaffLoginCredentials = (
    adopterStaffLoginRequest: AdopterStaffLoginRequest
  ) => {
    alert("You can login !");
    console.log(adopterStaffLoginRequest);
    handelAdaptorStaffLoginBasicCredentialsRequest(adopterStaffLoginRequest);
  };

  const handelAdaptorStaffLoginBasicCredentialsRequest = (
    adopterStaffLoginRequest: AdopterStaffLoginRequest
  ) => {
    //TODO
  };
  // -----------------------End For the adopter/staff login------------------------------------------

  // ----------------------- For the manager login------------------------------------------
  const getManagerLoginCredentials = (
    managerLoginRequest: ManagerLoginRequest
  ) => {
    alert("You can login !");
    console.log(managerLoginRequest);
    handelManagerLoginBasicCredentialsRequest(managerLoginRequest);
  };

  const handelManagerLoginBasicCredentialsRequest = (
    managerLoginRequest: ManagerLoginRequest
  ) => {
    //TODO
  };
  // -----------------------End For the adopter/staff login------------------------------------------

  return (
    <Form
      isLogin={true}
      getAdopterStaffLoginCredentials={getAdopterStaffLoginCredentials}
      getManagerLoginCredentials={getManagerLoginCredentials}
    />
  );
};

export default Login;
