import React, { ChangeEvent, FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ShelterCreationForm from "../Components/ShelterCreationForm";
import axios from "axios";
import BobUpWindow from "../Components/BobUpWindow";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Loading from "../Components/Loading";

const CreateShelter = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [responseStatus, setResponseStatus] = useState("");

  //function to direct us to the login using credential handeling
  const getCreateShelterRequest = (
    createShelterRequest: CreateShelterRequest
  ) => {
    handleCreateShelterRequest(createShelterRequest);
  };

  const handleCreateShelterRequest = async (
    createShelterRequest: CreateShelterRequest
  ) => {
    setLoading(true);
    try {
      const response = await axios({
        method: "post",
        url: "http://localhost:9080/api/shelter/create",
        data: createShelterRequest,
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
      {responseStatus === "Shelter Created Successfully" && (
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
      {responseStatus !== "Shelter Created Successfully" &&
        responseStatus !== "" && (
          <>
            <BobUpWindow setResponseStatus={setResponseStatus}>
              <p style={{ color: "red" }}>{responseStatus}</p>
              <button
                type="button"
                className="btn btn-primary"
                style={{ marginLeft: 0 }}
                onClick={() => navigate("/createShelter")}
              >
                OK
              </button>
            </BobUpWindow>
          </>
        )}
      {loading && <Loading isLoading={loading} />}
      <ShelterCreationForm getCreateShelterRequest={getCreateShelterRequest} />
    </>
  );
};

export default CreateShelter;
