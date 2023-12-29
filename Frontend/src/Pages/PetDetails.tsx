import React, { useEffect, useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import Navbar from "../Components/Navbar";

const PetDetails = () => {
  const isMounted = useRef<boolean>(true);
  const location = useLocation();
  const { firstName, lastName, role, petID, token, shelterName } =
    location.state as {
      firstName: string;
      lastName: string;
      role: string;
      petID: number;
      token: string;
      shelterName: string;
    };
  const [pet, setPet] = useState<PetDto | null>(null);

  const fetchData = async () => {
    // TODO
  };

  useEffect(() => {
    if (isMounted.current) {
      fetchData();
      isMounted.current = false;
    }
  }, []); // Empty dependency array means it runs once when component mounts
  return (
    <>
      <Navbar
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        firstName={firstName || "Mahmoud"}
        lastName={lastName || "Attia"}
        role={role}
        token={token}
        isPets={true}
      />
      {pet != null && (
        <>
          <h1>Let's render our pet</h1>
        </>
      )}
    </>
  );
};

export default PetDetails;
