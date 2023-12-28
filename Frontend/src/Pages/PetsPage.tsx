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
  useEffect(() => {
    if (isMounted.current) {
      fetchUserInfo();
      isMounted.current = false;
    }
  }, []);

  const getPets = async() => {
    let response : PetDto[] = []
    return response
    // TODO
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
        role={role}
        getPets={role === "adopter" ? getPets : getShelterPets}
        passedPets={[]} // TODO
      />
    </>
  );
};

export default PetsPage;
