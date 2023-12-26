import React, { ChangeEvent, FormEvent, useState } from "react";
import { Link } from "react-router-dom";

interface ShelterCreationFormProps {
  getCreateShelterRequest: (createShelterRequest: CreateShelterRequest) => void;
}

const ShelterCreationForm = ({
  getCreateShelterRequest,
}: ShelterCreationFormProps) => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    shelterName: "",
    shelterLocation: "",
    contactPhone: "",
    contactEmail: "",
  });

  //here's states to show whether there's error for a specific field
  const [formErrors, setFormErrors] = useState({
    email: "",
    password: "",
    shelterName: "",
    shelterLocation: "",
    contactPhone: "",
    contactEmail: "",
  });

  function handleFormSubmit(e: FormEvent<HTMLFormElement>): void {
    e.preventDefault();
    let validFields = CheckFields();

    if (
      validFields.email &&
      validFields.password &&
      validFields.shelterName &&
      validFields.shelterLocation &&
      validFields.contactPhone &&
      validFields.contactEmail
    ) {
      let createShelterRequest: CreateShelterRequest = {
        email: formData.email,
        password: formData.password,
        shelterName: formData.shelterName,
        address: formData.shelterLocation,
        contactPhone: formData.contactPhone,
        contactEmail: formData.contactEmail,
      };
      getCreateShelterRequest(createShelterRequest);
    }
  }

  function handleInputChange(e: ChangeEvent<HTMLInputElement>): void {
    const { name, value } = e.target;

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));

    if (
      name == "email" &&
      !(
        formData.contactEmail !== "" && formData.contactEmail !== formData.email
      )
    ) {
      setFormData((prevData) => ({
        ...prevData,
        contactEmail: value,
      }));
    }

    if (name === "email") {
      if (checkEmail().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            email: "",
          };
        });
    }
    if (name === "password") {
      if (checkPassword().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            password: "",
          };
        });
    }

    if (name === "shelterName") {
      if (checkShelterName().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            shelterName: "",
          };
        });
    }

    if (name === "shelterLocation") {
      if (checkShelterLocation().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            shelterLocation: "",
          };
        });
    }

    if (name === "contactPhone") {
      if (checkContactPhone().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            contactPhone: "",
          };
        });
    }

    if (name === "contactEmail") {
      if (checkContactEmail().length == 0)
        setFormErrors((prvious) => {
          return {
            ...prvious,
            contactEmail: "",
          };
        });
    }
  }

  //function to check the validity of all the fields and change the errors states using comments
  //associated with each violation
  function CheckFields() {
    let errorMessages = {
      email: "",
      password: "",
      shelterName: "",
      shelterLocation: "",
      contactPhone: "",
      contactEmail: "",
    };

    let validFields = {
      email: true,
      password: true,
      shelterName: true,
      shelterLocation: true,
      contactPhone: true,
      contactEmail: true,
    };

    let emailComment = checkEmail();
    if (emailComment.length != 0) {
      errorMessages.email = emailComment;
      if (formData.email.length == 0)
        errorMessages.email = "This is a mandatory field to fill !";
      validFields.email = false;
    }

    let passwordComment = checkPassword();
    if (passwordComment.length != 0) {
      errorMessages.password = passwordComment;
      if (formData.password.length == 0)
        errorMessages.password = "This is a mandatory field to fill !";
      validFields.password = false;
    }

    let shelterNameComment = checkShelterName();
    if (shelterNameComment.length != 0) {
      errorMessages.shelterName = shelterNameComment;
      if (formData.shelterName.length == 0)
        errorMessages.shelterName = "This is a mandatory field to fill !";
      validFields.shelterName = false;
    }

    let shelterLocationComment = checkShelterLocation();
    if (shelterLocationComment.length != 0) {
      errorMessages.shelterLocation = shelterLocationComment;
      if (formData.shelterLocation.length == 0)
        errorMessages.shelterLocation = "This is a mandatory field to fill !";
      validFields.shelterLocation = false;
    }

    let contactPhoneComment = checkContactPhone();
    if (contactPhoneComment.length != 0) {
      errorMessages.contactPhone = contactPhoneComment;
      if (formData.contactPhone.length == 0)
        errorMessages.contactPhone = "This is a mandatory field to fill !";
      validFields.contactPhone = false;
    }

    let contactEmailComment = checkContactEmail();
    if (contactEmailComment.length != 0) {
      errorMessages.contactEmail = contactEmailComment;
      if (formData.contactEmail.length == 0)
        errorMessages.contactEmail = "This is a mandatory field to fill !";
      validFields.contactEmail = false;
    }

    setFormErrors(errorMessages);
    console.log(formErrors);
    return validFields;
  }

  //--------------------------------Here are some individual checking functions for each field----------------
  function checkEmail() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let comment = "";
    if (!emailRegex.test(formData.email.trim())) {
      comment = "Please enter a valid email of the format username@domain";
    }
    if (formData.email.trim().length < 6) {
      comment = "Email is too short !";
    }
    return comment;
  }

  function checkPassword() {
    let comment = "";
    if (formData.password.length < 8) {
      comment = "Password must be at least 8 characters long";
    }
    return comment;
  }

  function checkShelterName() {
    if (formData.shelterName.length < 4) {
      return "Must be at least 4 characters";
    }
    return "";
  }

  function checkShelterLocation() {
    if (formData.shelterLocation.length < 8) {
      return "Must be at least 8 characters";
    }
    return "";
  }

  function checkContactPhone() {
    const phoneRegex = /^\+?[0-9\s-]+$/;

    if (!phoneRegex.test(formData.contactPhone.trim())) {
      return "Please enter a valid phone number.";
    }

    if (formData.contactPhone.trim().length < 10) {
      return "Must be at least 10 digits";
    }

    if (formData.contactPhone.trim().length > 12) {
      return "Must be at most 12 digits";
    }
    return ""; // Empty string means no errors
  }

  function checkContactEmail() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let comment = "";
    if (!emailRegex.test(formData.contactEmail.trim())) {
      comment = "Please enter a valid email of the format username@domain";
    }
    if (formData.contactEmail.trim().length < 6) {
      comment = "Email is too short !";
    }
    return comment;
  }
  //--------------------------------the end of some individual checking functions for each field----------------

  return (
    <>
      <div className="col-lg-5 col-sm-12 container form-container">
        <form className="row g-3" onSubmit={handleFormSubmit} noValidate>
          <header style={{ marginBottom: "0px" }}>
            <h3 style={{ marginBottom: "10px", color: "#007bff" }}>
              Create a shelter
            </h3>
            <h6 style={{ color: "gray" }}>
              Please fill in this form in order to create a shelter
            </h6>
            <hr style={{ marginBottom: "0px" }} />
          </header>

          <div className="col-12 input-cont">
            <label className="form-label formLabel">Manager email</label>
            <input
              type="email"
              style={{ padding: "0.8rem 0.75rem" }}
              className={`form-control ${formErrors.email ? "is-invalid" : ""}`}
              placeholder="Username@domain"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
            {formErrors.email && (
              <div className="invalid-feedback">{formErrors.email}</div>
            )}
          </div>

          <div className="col-12 input-cont">
            <label className="form-label formLabel">Password</label>
            <input
              type="password"
              className={`form-control ${
                formErrors.password ? "is-invalid" : ""
              }`}
              id="password"
              name="password"
              style={{ padding: "0.8rem 0.75rem" }}
              value={formData.password}
              onChange={handleInputChange}
              placeholder="At least 8 characters"
              required
            />
            {formErrors.password && (
              <div className="invalid-feedback">{formErrors.password}</div>
            )}
          </div>

          <div className="line-container" style={{ marginBottom: "15px" }}>
            <div className="line"></div>
            <div className="or">Fill in the shelter info</div>
            <div className="line"></div>
          </div>

          <div className="col-md-5 input-cont">
            <label className="form-label formLabel">Shelter name</label>
            <input
              type="text"
              placeholder="At least 4 characters"
              style={{ padding: "0.8rem 0.75rem" }}
              className={`form-control ${
                formErrors.shelterName ? "is-invalid" : ""
              }`}
              name="shelterName"
              aria-label="Shelter name"
              value={formData.shelterName}
              onChange={handleInputChange}
              required
            />
            {formErrors.shelterName && (
              <div className="invalid-feedback">{formErrors.shelterName}</div>
            )}
          </div>

          <div className="col-md-7 input-cont">
            <label className="form-label formLabel">Shelter location</label>
            <input
              type="text"
              placeholder="At least 8 characters"
              style={{ padding: "0.8rem 0.75rem" }}
              className={`form-control ${
                formErrors.shelterLocation ? "is-invalid" : ""
              }`}
              name="shelterLocation"
              aria-label="Shelter location"
              value={formData.shelterLocation}
              onChange={handleInputChange}
              required
            />
            {formErrors.shelterLocation && (
              <div className="invalid-feedback">
                {formErrors.shelterLocation}
              </div>
            )}
          </div>

          <div className="col-md-5 input-cont">
            <label className="form-label formLabel">Contact phone</label>
            <input
              type="tel"
              placeholder="At least 10 digits"
              style={{ padding: "0.8rem 0.75rem" }}
              className={`form-control ${
                formErrors.contactPhone ? "is-invalid" : ""
              }`}
              name="contactPhone"
              aria-label="Contact phone"
              value={formData.contactPhone}
              onChange={handleInputChange}
              required
            />
            {formErrors.contactPhone && (
              <div className="invalid-feedback">{formErrors.contactPhone}</div>
            )}
          </div>

          <div className="col-7 input-cont">
            <label className="form-label formLabel">Contact email</label>
            <input
              type="email"
              style={{ padding: "0.8rem 0.75rem" }}
              className={`form-control ${
                formErrors.contactEmail ? "is-invalid" : ""
              }`}
              placeholder="Username@domain"
              name="contactEmail"
              value={formData.contactEmail}
              onChange={handleInputChange}
              required
            />
            {formErrors.contactEmail && (
              <div className="invalid-feedback">{formErrors.contactEmail}</div>
            )}
          </div>

          <button type="submit" className="btn btn-primary col">
            Continue
          </button>
          <Link to="/login">If you want to just log-in, click here</Link>
        </form>
      </div>
    </>
  );
};

export default ShelterCreationForm;
