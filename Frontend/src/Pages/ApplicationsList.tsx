import React from "react";
import { useLocation } from "react-router-dom";
import Navbar from "../Components/Navbar";

const ApplicationsList = () => {
  const location = useLocation();

  var { shelterName, firstName, lastName, token, role } = location.state || {};

  return (
    <div>
      {/* <Navbar
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        firstName={firstName}
        lastName={lastName}
        role={role}
        token={token}
        isApplication={true}
      />

      <OrdersList
        getOrders={getOrders}
        deleteOrder={async () => "Not Required"}
        deleteOrderItem={async () => "Not Required"}
        updateOrderStatus={async () => "Not Required"}
        isAdmin={isAdmin}
        firstName={firstName}
        lastName={lastName}
        userToken={userToken}
      /> */}
    </div>
  );
};

export default ApplicationsList;
