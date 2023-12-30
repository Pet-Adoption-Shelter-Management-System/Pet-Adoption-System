import React from "react";
import { useLocation } from "react-router-dom";
import Navbar from "../Components/Navbar";
import ApplicationsList from "../Components/ApplicationsList";
import axios from "axios";

const AdopterApplications = () => {
  const location = useLocation();

  var { shelterName, firstName, lastName, token, role } = location.state || {};

  const getApplications = async () => {
    try {
      // TODO add the authorization header
      let url: string = `http://localhost:9080/api/application/get`;
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }); // Replace with your backend URL
      const apps: ApplicationDto[] = response.data;
      console.log(
        "🚀 ~ file: PetsPage.tsx:50 ~ getPets ~ response.data:",
        response.data
      );
      return apps;
    } catch (error) {
      // Handle errors here
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.error("Response data:", axiosError.response.data);
          console.error("Response status:", axiosError.response.status);
          alert("Access denied !");
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
      let apps: ApplicationDto[] = [];
      return apps;
    }
  };

  return (
    <div>
      <Navbar
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        firstName={firstName}
        lastName={lastName}
        role={role}
        token={token}
        isApplication={true}
      />
      <ApplicationsList
        getApplications={getApplications}
        updateApplicationStatus={async () => "Not required"}
        role={role}
        firstName={firstName}
        lastName={lastName}
        shelterName={shelterName}
        userToken={token}
      />
    </div>
  );
};

export default AdopterApplications;
