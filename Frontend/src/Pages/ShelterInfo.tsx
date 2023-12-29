import React, { useEffect, useRef, useState } from "react";

import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";
import ShelterInfoDisplay, { Shelter } from "../Components/ShelterInfoDisplay";
import axios from "axios";
import { Button } from "react-bootstrap";
import ShelterInfoEdit from "../Components/ShelterInfoEdit";

interface UpdateShelterRequest {
  oldName: string;
  shelterName: string;
  address: string;
  contactPhone: string;
  contactEmail: string;
}

const ShelterInfo = () => {
  const location = useLocation();
  var { shelterName, firstName, lastName, token, role } = location.state || {};
  const isMounted = useRef<boolean>(true);

  const shelterEx: Shelter = {
    name: "Amazon",
    location: "Alex-23st",
    contactPhone: "01204191992",
    contactEmail: "mahmoudatyaa@gmail.com",
  };

  const [shelter, setShelter] = useState<Shelter | null>(null);
  const [editModalShow, setEditModalShow] = useState(false);

  useEffect(() => {
    const fetchShelterInfo = async () => {
      console.log("sh name:", shelterName);
      console.log(token);
      let url = `http://localhost:9080/api/shelter/getShelter/${shelterName}`;
      try {
        const response = await axios(url, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log(response.data);
        setShelter(response.data);
      } catch (error) {
        if (axios.isAxiosError(error)) {
          // This type assertion tells TypeScript that error is an AxiosError
          const axiosError = error as import("axios").AxiosError;
          if (axiosError.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            alert((axiosError.response.data as Shelter).name);
            //   setResponseData(axiosError.response.data as string);
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
    if (isMounted.current) {
      isMounted.current = false;
      //   setShelter(shelterEx);
      //   console.log(shelterEx);
      fetchShelterInfo();
    }
  }, []);

  const [editedShelter, setEditedShelter] = useState<Shelter>({ ...shelter }); // Separate state for edited data
  //   setShelter(shelterEx);
  //   console.log(shelterEx);

  //   const handleEditClick = () => {
  //     setEditModalShow(true);
  //   };

  const handleEditClick = () => {
    setEditedShelter({ ...shelter }); // Initialize edited data with current shelter data
    setEditModalShow(true);
  };

  const handleEditClose = () => {
    setEditModalShow(false);
  };

  const handleEditSubmit = async (updatedShelter: Shelter) => {
    try {
      let request: UpdateShelterRequest = {
        oldName: shelterName,
        shelterName: shelterName,
        address: updatedShelter.location || "",
        contactEmail: updatedShelter.contactEmail || "",
        contactPhone: updatedShelter.contactPhone || "",
      };
      console.log("update req:", request);
      await axios.put(`http://localhost:9080/api/shelter/update`, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      let newShelter: Shelter = {
        name: request.shelterName,
        location: request.address,
        contactEmail: request.contactEmail,
        contactPhone: request.contactPhone,
      };
      setShelter(newShelter);
    } catch (error) {
      //   console.error("Error updating shelter information", error);
      //   alert(error)
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          alert(axiosError.response.data as AuthResponse);
          //   setResponseData(axiosError.response.data as string);
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
      <Navbar
        shelterName={shelterName}
        firstName={firstName}
        lastName={lastName}
        role={role}
        token={token}
        isShelterInfo={true}
      />
      <div>
        <ShelterInfoDisplay {...shelter} />
      </div>
      {role === "manager" && (
        <>
          <div className="container mt-4 text-center">
            <Button variant="primary" onClick={handleEditClick}>
              Edit Shelter
            </Button>
          </div>
          <ShelterInfoEdit
            show={editModalShow}
            handleClose={handleEditClose}
            shelter={editedShelter} // Pass editedShelter to the modal
            onSubmit={(updatedShelter) => {
              handleEditSubmit(updatedShelter);
              handleEditClose();
            }}
          />
        </>
      )}
    </>
    // <div style={{ overflowX: "hidden" }}>
    //   <div
    //     className="home-navbar-container"
    //     style={{
    //       display: "flex",
    //       alignItems: "center",
    //       justifyContent: "flex-start",
    //       marginBottom: "200px",
    //     }}
    //   >
    //     <Navbar
    //       shelterName={shelterName}
    //       firstName={firstName}
    //       lastName={lastName}
    //       role={role}
    //       token={token}
    //       isShelterInfo={true}
    //     />
    //   </div>
    //   <div className="row">
    //     <ShelterInfoDisplay {...shelter} />
    //   </div>
    //   {role === "manager" && (
    //     <>
    //       <div className="container mt-4 text-center">
    //         <Button variant="primary" onClick={handleEditClick}>
    //           Edit Shelter
    //         </Button>
    //       </div>
    //       <ShelterInfoEdit
    //         show={editModalShow}
    //         handleClose={handleEditClose}
    //         shelter={editedShelter} // Pass editedShelter to the modal
    //         onSubmit={(updatedShelter) => {
    //           handleEditSubmit(updatedShelter);
    //           handleEditClose();
    //         }}
    //       />
    //     </>
    //   )}
    // </div>
  );
};

export default ShelterInfo;
