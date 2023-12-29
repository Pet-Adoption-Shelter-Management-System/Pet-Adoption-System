import React, { useEffect, useRef, useState } from "react";
import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";
import axios from "axios";
import { Button } from "react-bootstrap";
import ProfileInfoDisplay, { Profile } from "../Components/ProdileInfoDisplay";
import ProfileInfoEdit from "../Components/ProfileInfoEdit";


const ProfilePage = () => {
  const location = useLocation();
  var { shelterName, firstName, lastName, token, role } = location.state || {};
  const isMounted = useRef<boolean>(true);

  const[actualFirstName, setActualFirstName] = useState(firstName);
  const[actualLastName, setActualLastName] = useState(lastName);

  const [profile, setProfile] = useState<Profile | null>(null);
  const [editModalShow, setEditModalShow] = useState(false);

  const fetchProfileInfo = async () => {
    console.log(token);
    let url = `http://localhost:9080/api/profile/getProfile`;
    try {
      const response = await axios(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(response.data);
      setProfile(response.data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          alert((axiosError.response.data as Profile).email);
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

  useEffect(() => {
    
    if (isMounted.current) {
      isMounted.current = false;
      //   setShelter(shelterEx);
      //   console.log(shelterEx);
        fetchProfileInfo();
    }
  }, []);

  const [editedProfile, setEditedProfile] = useState<Profile>({ ...profile }); // Separate state for edited data

  const handleEditClick = () => {
    setEditedProfile({ ...profile }); // Initialize edited data with current shelter data
    setEditModalShow(true);
  };

  const handleEditClose = () => {
    setEditModalShow(false);
  };

  const handleEditSubmit = async (updatedProfile: Profile) => {
    try {
      let request: Profile = {
        email: profile?.email,
        firstName: updatedProfile.firstName,
        lastName: updatedProfile.lastName,
        contactPhone: updatedProfile.contactPhone,
        address: updatedProfile.address,
      };
      console.log("update req:", request);
      await axios.put(`http://localhost:9080/api/profile/update`, request, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      setProfile(request);
      setActualFirstName(request.firstName);
      setActualLastName(request.lastName);
    //   fetchProfileInfo();
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
    // setProfile(updatedProfile);
  };

  return (
    <>
      <Navbar
        shelterName={shelterName}
        firstName={actualFirstName}
        lastName={actualLastName}
        role={role}
        token={token}
        // shelterName="Pet Shelter"
        // firstName="Mahmoud"
        // lastName="Attia"
        // role="manager"
        // token="1234"
      />
      <div>
        <ProfileInfoDisplay {...profile} />
      </div>

      <div className="container mt-4 text-center">
        <Button variant="primary" onClick={handleEditClick}>
          Edit Profile
        </Button>
      </div>
      <ProfileInfoEdit
        show={editModalShow}
        handleClose={handleEditClose}
        profile={editedProfile} 
        onSubmit={(updatedProfile) => {
          handleEditSubmit(updatedProfile);
          handleEditClose();
        }}
      />
    </>
  );
};

export default ProfilePage;
