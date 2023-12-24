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
  const getSignUpCredentials = (customer: RegisterRequest) => {
    handleStaffSignupRequest(customer);
  };

  const handleStaffSignupRequest = async (customer: RegisterRequest) => {
    // TODO
  };

  const handleStaffSignupResponse = () => {
    //TODO
  };
  // TODO get the email form the email parameter from the link by making a constructor
  // TODO set the useState constant by this email and you're good to go

  return (
    <Form
      isLogin={false}
      isStaff={true}
      staffEmail={staffEmail}
      getSignUpCredentials={getSignUpCredentials}
    />
  );
};

export default StaffSignUP;
