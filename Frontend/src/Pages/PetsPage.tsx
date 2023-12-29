import React, { useEffect, useRef, useState } from "react";

import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";
import axios from "axios";
import PetsList from "../Components/PetsList";

interface UserInfo {
  firstName: string;
  lastName: string;
}

interface HomeRequest {
  role: string;
  shelterName: string;
}

const PetsPage = () => {
  const location = useLocation();
  var { userToken, from, shelterName, role, passedPets } = location.state || {};
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const isMounted = useRef<boolean>(true);

  // const fetchUserInfo = async () => {
  //   try {
  //     let url: string = `http://localhost:9080/api/petPage/getUserInfo/${role}`;
  //     const response = await axios(url, {
  //       method: "GET",
  //       headers: {
  //         Authorization: `Bearer ${userToken}`,
  //       },
  //     });
  //     console.log(
  //       "🚀 ~ file: PetsPage.tsx:37 ~ fetchUserInfo ~ response:",
  //       response.data
  //     );
  //     console.log(userToken);
  //     setUserInfo(response.data);
  //   } catch (error) {
  //     console.error("Access denied !");
  //   }
  // };

  // useEffect(() => {
  //   if (isMounted.current) {
  //     console.log(
  //       "🚀 ~ file: PetsPage.tsx:21 ~ PetsPage ~ userToken, from, shelterName, role:",
  //       userToken,
  //       from,
  //       shelterName,
  //       role
  //     );
  //     fetchUserInfo();
  //     isMounted.current = false;
  //   }
  // }, []);

  const getPets = async () => {
    try {
      // TODO add the authorization header
      let url: string = `http://localhost:9080/api/allPets`;
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      }); // Replace with your backend URL
      const pets: PetDto[] = response.data;
      console.log(
        "🚀 ~ file: PetsPage.tsx:50 ~ getPets ~ response.data:",
        response.data
      );
      return pets;
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
      let pets: PetDto[] = [];
      return pets;
    }
  };

  const getShelterPets = async () => {
    try {
      // TODO add the authorization header
      console.log("In get shelter pets");
      console.log(
        "🚀 ~ file: PetsPage.tsx:107 ~ getShelterPets ~ shelterName:",
        shelterName
      );

      let url: string = `http://localhost:9080/api/shelter/allPets?shelterName=${shelterName}`;
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      }); // Replace with your backend URL
      const pets: PetDto[] = response.data;
      console.log(
        "🚀 ~ file: PetsPage.tsx:50 ~ getPets ~ response.data:",
        response.data
      );
      return pets;
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
      let pets: PetDto[] = [];
      return pets;
    }
  };

  return (
    <>
      <Navbar
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        firstName={userInfo?.firstName || "Mahmoud"}
        lastName={userInfo?.lastName || "Attia"}
        role={role}
        token={userToken}
        isPets={true}
      />

      <PetsList
        firstName={userInfo?.firstName || "Mahmoud"}
        lastName={userInfo?.lastName || "Attia"}
        userToken={userToken}
        role={role}
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        getPets={role === "adopter" ? getPets : getShelterPets}
        passedPets={passedPets}
      />
    </>
  );
};

export default PetsPage;
