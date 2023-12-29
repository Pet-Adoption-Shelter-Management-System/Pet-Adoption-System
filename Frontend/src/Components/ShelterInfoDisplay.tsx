import React from "react";

export interface Shelter {
  name?: string;
  location?: string;
  contactPhone?: string;
  contactEmail?: string;
}

const ShelterInfoDisplay: React.FC<Shelter> = ({
  name,
  location,
  contactPhone,
  contactEmail,
}) => (
  <div
    className="container mt-5"
    style={{
      width: "40%",
      background: "linear-gradient(to top, #a3bded 0%, #6991c7 100%)",
      padding: "20px",
      borderRadius: "8px",
    }}
  >
    <h2 className="mb-4">Shelter Information</h2>
    <p>
      <strong>Name:</strong> {name}
    </p>
    <p>
      <strong>Location:</strong> {location}
    </p>
    <p>
      <strong>Contact Phone:</strong> {contactPhone}
    </p>
    <p>
      <strong>Contact Email:</strong> {contactEmail}
    </p>
  </div>
);

export default ShelterInfoDisplay;
