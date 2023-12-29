import React, { useRef, useState } from "react";

import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";

interface PetsInfo {
  firstName: string;
  lastName: string;
  Pets: [];
}

const PetsPage = () => {
  const location = useLocation();
  var { userToken, from, shelterName, role } = location.state || {};
  const [petsInfo, setPetsInfo] = useState<PetsInfo | null>(null);
  const isMounted = useRef<boolean>(true);
  console.log(userToken)
  return (
    <div style={{ overflowX: "hidden" }}>
      <div className="home-navbar-container" style={{ height: "100vh" }}>
        <Navbar
          shelterName={ role === "adopter" ? "Pets Adoption" : shelterName}
          firstName={petsInfo?.firstName || "Mahmoud"}
          lastName={petsInfo?.lastName || "Attia"}
          role={role}
          token={userToken}
          isPets={true}
        />
      </div>
    </div>
  );
};

export default PetsPage;
