import React from "react";

export interface Profile {
    email?:string;
    firstName?:string;
    lastName?:string;
    contactPhone?:string;
    address?:string;
}

const ProfileInfoDisplay: React.FC<Profile> = ({
  email,
  firstName,
  lastName,
  contactPhone,
  address,
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
    <h2 className="mb-4">Profile</h2>
    <p>
      <strong>Email:</strong> {email}
    </p>
    <p>
      <strong>First Name:</strong> {firstName}
    </p>
    <p>
      <strong>Last Name:</strong> {lastName}
    </p>
    <p>
      <strong>Contact Phone:</strong> {contactPhone}
    </p>
    <p>
      <strong>Address:</strong> {address}
    </p>
  </div>
);

export default ProfileInfoDisplay;
