import React, { useState } from "react";
import Form from "../Components/Form";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import BobUpWindow from "../Components/BobUpWindow";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
import Loading from "../Components/Loading";

const SignUp = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [responseStatus, setResponseStatus] = useState("");

  // const []
  //function to direct the flow to sign up using credentials
  const getSignUpCredentials = (customer: RegisterRequest) => {
    handelSignUpBasicCredentialsRequest(customer);
  };

  const handelSignUpBasicCredentialsRequest = async (
    customer: RegisterRequest
  ) => {
    setLoading(true);
    try {
      const response = await axios({
        method: "post",
        url: "http://localhost:9080/api/auth/adopterSignUp",
        data: customer,
      });
      setLoading(false);
      // Success
      setResponseStatus(response.data);
    } catch (error) {
      // Handle errors here
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          setResponseStatus(axiosError.response.data as string);
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
      {responseStatus ===
        "Signed up successfully and a verification email's sent to you to complete sign up" && (
        <>
          <BobUpWindow setResponseStatus={setResponseStatus}>
            <p>
              <FontAwesomeIcon
                icon={faCircleCheck}
                style={{
                  color: "green",
                  fontSize: "18px",
                  marginRight: "10px",
                }}
              />
              {responseStatus}
            </p>
          </BobUpWindow>
        </>
      )}
      {responseStatus === "Already exist!" && (
        <>
          <BobUpWindow setResponseStatus={setResponseStatus}>
            <p style={{ color: "black" }}>
              The email address you provided already exists. Log in instead
            </p>
            <button
              type="button"
              className="btn btn-primary"
              style={{ marginLeft: 0 }}
              onClick={() => navigate("/login")}
            >
              Log in
            </button>
          </BobUpWindow>
        </>
      )}

      {responseStatus !== "Already exist!" &&
        responseStatus !==
          "Signed up successfully and a verification email's sent to you to complete sign up" &&
        responseStatus !== "" && (
          <>
            <BobUpWindow setResponseStatus={setResponseStatus}>
              <p style={{ color: "red" }}>{responseStatus}</p>
              <button
                type="button"
                className="btn btn-primary"
                style={{ marginLeft: 0 }}
                onClick={() => navigate("/signup")}
              >
                OK
              </button>
            </BobUpWindow>
          </>
        )}
      {loading && <Loading isLoading={loading} />}

      <Form isLogin={false} getSignUpCredentials={getSignUpCredentials} isEmployeeSignup={false}  />
    </>
  );
};

export default SignUp;
