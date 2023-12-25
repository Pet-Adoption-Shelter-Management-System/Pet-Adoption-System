import React, { ChangeEvent, FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ShelterCreationForm from "../Components/ShelterCreationForm";

const CreateShelter = () => {
  const navigate = useNavigate();
  const [responseStatus, setResponseStatus] = useState("");

  //TODO modify the loginRequest to handle the shelter for the manager
  //function to direct us to the login using credential handeling
  const getCreateShelterRequest = (createShelterRequest: CreateShelterRequest) => {
    console.log("Now we can create a shelter");
    console.log(createShelterRequest);
    handleCreateShelterRequest(createShelterRequest);
  };

  function handleCreateShelterRequest(createShelterRequest: CreateShelterRequest) {
    //TODO
  }

  return <ShelterCreationForm getCreateShelterRequest={getCreateShelterRequest}/>;
};

export default CreateShelter;
