import React, { ChangeEvent, useEffect, useState } from "react";
import { Button, Dropdown, Modal } from "react-bootstrap";
import "./AddEditPet.css";
import { useNavigate } from "react-router";
import axios from "axios";
import GenericAlertModal from "./GenericAlertModal";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";

export interface EditedPet {
  id: string;
  name: string;
  isMale: string;
  isHouseTrained: string;
  description: string;
  healthStatus: string;
  age: string;
  behaviour: string;
  breed: string;
  species: string;
  isSpayed: string; // Use null if the property can be nullable
  shelterName: string;
  petVaccinations: string[];
}

interface AddEditPetProps {
  resetButton: () => void;
  adminToken: string;
  isEdit: Boolean;
  pet?: EditedPet;
}

const AddEditPet = ({
  resetButton,
  adminToken,
  isEdit,
  pet,
}: AddEditPetProps) => {
  const [formData, setFormData] = useState<EditedPet>({
    id: "",
    name: "",
    isMale: "",
    isHouseTrained: "",
    description: "",
    healthStatus: "",
    age: "",
    behaviour: "",
    breed: "",
    species: "",
    isSpayed: "",
    shelterName: "",
    petVaccinations: [],
  });
  const navigate = useNavigate();
  const [formSubmitted, setFormSubmitted] = useState(false);
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [responseData, setResponseData] = useState("");
  const [show, setShow] = useState<boolean>(true);
  let finalVaccines: string[] = [];

  useEffect(() => {
    if (pet!) {
      setFormData({
        id: pet.id,
        name: pet.name,
        isMale: pet.isMale,
        isHouseTrained: pet.isHouseTrained,
        description: pet.description,
        healthStatus: pet.healthStatus,
        age: pet.age,
        behaviour: pet.behaviour,
        breed: pet.breed,
        species: pet.species,
        isSpayed: pet.isSpayed,
        shelterName: pet.shelterName,
        petVaccinations: pet.petVaccinations,
      });
    } else {
      setFormData({
        id: "",
        name: "",
        isMale: "Male",
        isHouseTrained: "false",
        description: "",
        healthStatus: "",
        age: "",
        behaviour: "",
        breed: "",
        species: "",
        isSpayed: "false",
        shelterName: "",
        petVaccinations: [],
      });
    }
  }, [pet]);

  // Function to handle input changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (
      name === "name" ||
      name === "breed" ||
      name === "species" ||
      name === "shelterName" ||
      name === "behaviour" ||
      name === "healthStatus"
    ) {
      // Check for maximum length of 30 characters
      if (value.length > 30) {
        // Invalid input, do not update the state
        return;
      }
    }

    if (name === "age") {
      // Check for a valid age (numeric or float, no leading zeros, non-negative)
      const isValidAge =
        /^[+]?\d*\.?\d+$/.test(value) && parseFloat(value) >= 0;

      if (!isValidAge) {
        // Invalid age, do not update the state
        return;
      }
    }

    // Update the state
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Function to handle file changes
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    setSelectedFiles((prevFiles) => [...prevFiles, ...(files as FileList)]);
  };

  const isAtLeastOneImageSelected = (): Boolean => {
    return selectedFiles.some((file) => file.type.startsWith("image/"));
  };

  const handleFormSubmit = () => {
    setFormSubmitted(true);

    // Check if at least one image is selected
    let isAtLeastOneImageSelected = false;
    if (!isEdit || (isEdit && selectedFiles.length != 0)) {
      isAtLeastOneImageSelected = selectedFiles.some((file) =>
        file.type.startsWith("image/")
      );
    }

    if (
      formData.name &&
      formData.breed &&
      formData.description &&
      formData.healthStatus &&
      formData.age &&
      formData.behaviour &&
      formData.species &&
      formData.shelterName &&
      ((isEdit && selectedFiles.length == 0) ||
        (isEdit && selectedFiles.length != 0 && isAtLeastOneImageSelected) ||
        (!isEdit && isAtLeastOneImageSelected))
    ) {
      let cnt: number = 0;
      for (let i = 0; i < formData.petVaccinations.length; i++) {
        if (formData.petVaccinations[i] !== "") {
          finalVaccines[cnt++] = formData.petVaccinations[i];
        }
      }
      // Process the form data
      if (isEdit) {
        handleEditRequest();
      } else {
        handleAddRequest();
      }
    }
  };

  const handleEditRequest = async () => {
    try {
      let url: string = `http://localhost:9080/api/pet/edit?petDto=${encodeURIComponent(
        JSON.stringify(convertToDto())
      )}`;
      const formData = new FormData();
      if (selectedFiles.length != 0) {
        for (let i = 0; i < selectedFiles.length; i++) {
          formData.append("docs", selectedFiles[i]);
        }
      } else {
        formData.append("docs", "");
      }

      const response = await axios.post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${adminToken}`,
        },
      });

      // Here means that the response is Ok and the product is added successfully
      setResponseData(response.data);
      onCancel();
    } catch (error) {
      // Handle errors here
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          setResponseData(axiosError.response.data as string);
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

  const handleAddRequest = async () => {
    try {
      let url: string = `http://localhost:9080/api/pet/add?petDto=${encodeURIComponent(
        JSON.stringify(convertToDto())
      )}`;

      const formData = new FormData();

      for (let i = 0; i < selectedFiles.length; i++) {
        formData.append("docs", selectedFiles[i]);
      }

      const response = await axios.post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${adminToken}`,
        },
      });

      // Here means that the response is Ok and the product is added successfully
      setResponseData(response.data);
      onCancel();
    } catch (error) {
      console.error(error);
      // Handle errors here
      if (axios.isAxiosError(error)) {
        // This type assertion tells TypeScript that error is an AxiosError
        const axiosError = error as import("axios").AxiosError;
        if (axiosError.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          setResponseData(axiosError.response.data as string);
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

  const convertToDto = () => {
    let petDto: PetDto = {
      id: 0,
      name: formData.name,
      male: formData.isMale === "Male",
      houseTrained: formData.isHouseTrained === "true",
      description: formData.description,
      healthStatus: formData.healthStatus,
      age: Number(formData.age),
      behaviour: formData.behaviour,
      breed: formData.breed,
      species: formData.species,
      spayed: formData.isSpayed === "true",
      shelterName: formData.shelterName,
      petVaccinations: finalVaccines,
    };
    return petDto;
  };

  const handleGenderSelect = (value: string) => {
    setFormData((prevData) => ({
      ...prevData,
      ["isMale"]: value,
    }));
  };

  // Function to add a new vaccine
  const addVaccine = () => {
    setFormData((prevState) => ({
      ...prevState,
      petVaccinations: [...prevState.petVaccinations, ""],
    }));
  };

  const handleIsSpayedChange = () => {
    setFormData((prevState) => ({
      ...prevState,
      isSpayed: prevState.isSpayed === "true" ? "false" : "true",
    }));
  };

  const handleIsHouseTrainedChange = () => {
    setFormData((prevState) => ({
      ...prevState,
      isHouseTrained: prevState.isHouseTrained === "true" ? "false" : "true",
    }));
  };

  // Function to delete a vaccine by index
  const deleteVaccine = (indexToDelete: number) => {
    setFormData((prevState) => {
      const updatedVaccinations = [...prevState.petVaccinations];
      updatedVaccinations.splice(indexToDelete, 1); // Remove the vaccine at the specified index
      return {
        ...prevState,
        petVaccinations: updatedVaccinations,
      };
    });
  };

  const onCancel = () => {
    setShow(false);
    resetButton();
  };

  function handleTextAreaChange(e: ChangeEvent<HTMLTextAreaElement>): void {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  }

  const resetResponseData = () => {
    setResponseData("");
  };

  return (
    <>
      {responseData !== "" &&
        (responseData === "Pet added successfully" ||
          responseData === "Pet updated successfully") && (
          <>
            <GenericAlertModal
              onClose={resetResponseData}
              show={true}
              body={
                <>
                  <h5 style={{ color: "green" }}>
                    <FontAwesomeIcon
                      icon={faCircleCheck}
                      style={{
                        color: "green",
                        fontSize: "18px",
                        marginRight: "10px",
                      }}
                    />
                    {responseData}
                  </h5>
                </>
              }
            />
          </>
        )}

      {responseData !== "" &&
        responseData !== "Pet added successfully" &&
        responseData !== "Pet updated successfully" && (
          <>
            <GenericAlertModal
              onClose={resetResponseData}
              show={true}
              body={
                <>
                  <h5 style={{ color: "red" }}>{responseData}</h5>
                </>
              }
            />
          </>
        )}

      <Modal
        id="staticBackdrop"
        className="modal-dialog modal-lg"
        show={show}
        onHide={onCancel}
        backdrop="static"
        keyboard={false}
        tabIndex={-1}
        aria-labelledby="staticBackdropLabel"
      >
        <Modal.Header
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            color: "#007bff",
          }}
        >
          {isEdit && <h3>Edit The Pet</h3>}
          {!isEdit && <h3>Add A Pet</h3>}
        </Modal.Header>

        <Modal.Body style={{ padding: "0px" }}>
          <form noValidate style={{ overflowY: "scroll", height: "60vh" }}>
            <div
              className="productFormField"
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
              }}
            >
              <div
                className="productNameField"
                style={{ width: "20%", marginLeft: "8px" }}
              >
                <label className="form-label">Pet name*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="name"
                  aria-label="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  required
                />
                {formSubmitted && !formData.name && (
                  <div className="text-danger">*Required</div>
                )}
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Breed*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="breed"
                  aria-label="breed"
                  value={formData.breed}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.breed && (
                  <div className="text-danger">*Required</div>
                )}
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Species*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="species"
                  aria-label="species"
                  value={formData.species}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.species && (
                  <div className="text-danger">*Required</div>
                )}
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Shelter name*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="shelterName"
                  aria-label="shelterName"
                  placeholder="At least 4 chars"
                  value={formData.shelterName}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.shelterName && (
                  <div className="text-danger">*Required</div>
                )}
                {formSubmitted &&
                  formData.shelterName.length < 4 &&
                  formData.shelterName.length != 0 && (
                    <div className="text-danger">*At least 4 chars</div>
                  )}
              </div>
            </div>
            <div
              className="productFormField"
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <div
                className="productFormField"
                style={{ width: "20%", marginLeft: "0px" }}
              >
                <label className="form-label" style={{ marginLeft: "8px" }}>
                  Sex:
                </label>
                <Dropdown>
                  <Dropdown.Toggle variant="primary" id="dropdown-basic">
                    {formData.isMale}
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                    <Dropdown.Item onClick={() => handleGenderSelect("Male")}>
                      Male
                    </Dropdown.Item>
                    <Dropdown.Item onClick={() => handleGenderSelect("Female")}>
                      Female
                    </Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Age*:</label>
                <input
                  type="number"
                  className="form-control"
                  name="age"
                  placeholder="Non zero float"
                  aria-label="age"
                  value={formData.age}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.age && (
                  <div className="text-danger">*Required</div>
                )}
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Behaviour*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="behaviour"
                  aria-label="behaviour"
                  value={formData.behaviour}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.behaviour && (
                  <div className="text-danger">*Required</div>
                )}
              </div>

              <div className="productFormField" style={{ width: "20%" }}>
                <label className="form-label">Health status*:</label>
                <input
                  type="text"
                  className="form-control"
                  name="healthStatus"
                  aria-label="healthStatus"
                  value={formData.healthStatus}
                  onChange={handleInputChange}
                />
                {formSubmitted && !formData.healthStatus && (
                  <div className="text-danger">*Required</div>
                )}
              </div>
            </div>
            <div
              className="productFormField"
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                alignItems: "center",
                marginLeft: "8px",
                width: "50%",
              }}
            >
              <div
                className="productFormField"
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              >
                <input
                  className="form-check-input"
                  type="checkbox"
                  checked={formData.isSpayed === "true"}
                  id="checkbox1"
                  style={{ margin: "0px" }}
                  onChange={handleIsSpayedChange}
                />
                <label
                  className="form-check-label form-label"
                  style={{ marginLeft: "10px" }}
                >
                  Spayed
                </label>
              </div>

              <div
                className="productFormField"
                style={{
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "space-between",
                }}
              >
                <input
                  className="form-check-input"
                  type="checkbox"
                  checked={formData.isHouseTrained === "true"}
                  id="checkbox2"
                  style={{ margin: "0px" }}
                  onChange={handleIsHouseTrainedChange}
                />
                <label
                  className="form-check-label form-label"
                  style={{ marginLeft: "10px" }}
                >
                  House trained
                </label>
              </div>
            </div>

            <div className="productFormField" style={{ marginLeft: "8px" }}>
              <label className="form-label">Description*:</label>
              <textarea
                className="form-control"
                name="description"
                aria-label="description"
                value={formData.description}
                onChange={handleTextAreaChange}
                rows={2} // You can adjust the number of rows as needed
              />
              {formSubmitted && !formData.description && (
                <div className="text-danger">*Description is required</div>
              )}
              {formSubmitted &&
                formData.description.length < 8 &&
                formData.description.length != 0 && (
                  <div className="text-danger">*At least 8 chars</div>
                )}
            </div>
            <div className="line-container" style={{ marginTop: "30px" }}>
              <div className="line"></div>
              <div className="or" style={{ fontWeight: "500" }}>
                The pet vaccination(s)
              </div>
              <div className="line"></div>
            </div>
            <div className="vaccinationsContainer">
              {formData.petVaccinations.map((petVaccination, index) => (
                <div className="petVaccination" key={index}>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter vaccine"
                    value={petVaccination}
                    style={{ marginRight: "10px" }}
                    onChange={(e) =>
                      setFormData((prevState) => {
                        const updatedVaccinations = [
                          ...prevState.petVaccinations,
                        ];
                        updatedVaccinations[index] = e.target.value;
                        return {
                          ...prevState,
                          petVaccinations: updatedVaccinations,
                        };
                      })
                    }
                  />
                  <Button
                    className="btn2 remove-btn2"
                    variant="danger"
                    onClick={() => deleteVaccine(index)}
                  >
                    X
                  </Button>
                </div>
              ))}
            </div>
            <Button
              variant="success"
              type="button"
              onClick={addVaccine}
              style={{ marginLeft: "8px" }}
            >
              Add a new vaccine vaccine
            </Button>

            <div
              style={{
                borderBottom: "1px solid #000",
                margin: "20px 0",
                textAlign: "center",
                fontWeight: "500",
              }}
            ></div>

            <div className="mb-3">
              <label className="form-label">
                Upload the Pet documents including at least one image:
                {!isEdit && <>*</>}:
              </label>
              {isEdit && (
                <>
                  <label className="form-label" style={{ color: "red" }}>
                    Please note that if you chose to update the documents, then
                    we'll overwrite the old ones
                  </label>
                </>
              )}

              <input
                className="form-control"
                type="file"
                id="formFile"
                accept="*/*" // Accept any file type
                multiple // Allow multiple files
                onChange={handleFileChange}
              />
              {formSubmitted &&
                ((selectedFiles.length == 0 && !isEdit) ||
                  (selectedFiles.length != 0 &&
                    isEdit &&
                    !isAtLeastOneImageSelected()) ||
                  (!isEdit &&
                    selectedFiles.length != 0 &&
                    !isAtLeastOneImageSelected())) && (
                  <div className="text-danger">*Image is required</div>
                )}
            </div>
          </form>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="danger" onClick={onCancel}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleFormSubmit} type="submit">
            Confirm
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default AddEditPet;
