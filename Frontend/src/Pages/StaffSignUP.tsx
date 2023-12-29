import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "../Components/Form";

const StaffSignUP = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [responseStatus, setResponseStatus] = useState("");
  const [staffEmail, setStaffEmail] = useState("adelmahmoud199540@gmail.com");
  // const []
  //function to direct the flow to sign up using credentials
  const getStaffSignUpCredentials = (customer: RegisterRequest) => {
    handleStaffSignupRequest(customer);
  };

  const handleStaffSignupRequest = async (customer: RegisterRequest) => {
    // TODO
  };

  const handleStaffSignupResponse = () => {
    //TODO
  };

  return (
    <Form
      isLogin={false}
      isEmployeeSignup={true}
      staffEmail={staffEmail}
      getStaffSignUpCredentials={getStaffSignUpCredentials}
    />
  );
};

export default StaffSignUP;
