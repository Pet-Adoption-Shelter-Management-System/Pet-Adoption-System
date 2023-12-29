import React, { useEffect, useRef, useState } from "react";

import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";
import axios from "axios";
import PetsList from "../Components/PetsList";

interface UserInfo {
  firstName: string;
  lastName: string;
}

const PetsPage = () => {
  const location = useLocation();
  var { userToken, from, shelterName, role } = location.state || {};
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const isMounted = useRef<boolean>(true);

  const fetchUserInfo = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9080/api/petPage/getUserInfo?role=${role}&shelterName=${shelterName}`,
        {
          headers: {
            Authorization: `Bearer ${userToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      setUserInfo(response.data);
    } catch (error) {
      console.error("Access denied !");
    }
  };

  // useEffect runs on component mount
  // useEffect(() => {
  //   if (isMounted.current) {
  //     fetchUserInfo();
  //     isMounted.current = false;
  //   }
  // }, []);

  
  const getPets = async() => {
    try {
      // TODO add the authorization header
      const response = await axios.get("http://localhost:9080/api/allPets" ); // Replace with your backend URL
      const pets: PetDto[] = response.data;
      console.log("🚀 ~ file: PetsPage.tsx:50 ~ getPets ~ response.data:", response.data)
      return pets;
    } catch (error) {
      alert (error);
      const pets: PetDto[] = [];
      return pets;
    }
  };

  const getShelterPets = async() => {
    let response : PetDto[] = []
    return response
    // TODO
  }

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
        role={"manager"}
        getPets={getPets} //TODO chnage it according to the role
        passedPets={[]} // TODO
      />
    </>
  );
};

export default PetsPage;
