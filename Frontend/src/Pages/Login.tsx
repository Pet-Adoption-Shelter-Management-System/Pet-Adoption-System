import React, { useState } from "react";
import Form from "../Components/Form";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Loading from "../Components/Loading";

const Login = () => {
  const navigate = useNavigate();
  const [responseStatus, setResponseStatus] = useState("");
  const [loading, setLoading] = useState(false);

  // ----------------------- For the adopter/staff login------------------------------------------
  const getLoginCredentials = (userLoginCredentials: LoginRequest) => {
    console.log(
      "🚀 ~ file: Login.tsx:13 ~ getLoginCredentials ~ userLoginCredentials:",
      userLoginCredentials
    );
    handleUserLoginRequest(userLoginCredentials);
  };

  const handleUserLoginRequest = async (userLoginCredentials: LoginRequest) => {
    setLoading(true);
    try {
      const response = await axios({
        method: "post",
        url: "http://localhost:9080/api/auth/login",
        data: userLoginCredentials,
      });
      setLoading(false);
      // Success
      alert("You've logged in successfully");
      navigate("/PetsPage", {
        state: {
          userToken: response.data.token,
          from: "logged-in",
          shelterName: response.data.shelterName,
          role: userLoginCredentials.role
        },
      });
    } catch (error) {
      setLoading(false);
      // Handle errors here
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          alert((axiosError.response.data as AuthResponse).token);
        } else if (axiosError.request) {
          // The request was made but no response was received
          console.error("No response received:", axiosError.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.error("Error:", axiosError.message);
        }
      } else {
        // Handle non-Axios errors
        console.error("Non-Axios error:", error);
      }
    }
  };

  return (
    <>
      {loading && <Loading isLoading={loading} />}

      <Form isLogin={true} getLoginCredentials={getLoginCredentials} />
    </>
  );
};

export default Login;
