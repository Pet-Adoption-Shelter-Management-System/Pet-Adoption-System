import React, { useEffect, useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import Navbar from "../Components/Navbar";
import axios from "axios";
import { Button, Carousel } from "react-bootstrap";
import "./PetDetails.css";
import { height } from "@fortawesome/free-brands-svg-icons/fa42Group";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";

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
  const [images, setImages] = useState<DocumentDto[]>([]);
  const [pdfs, setPdfs] = useState<DocumentDto[]>([]);
  const [otherDocs, setOtherDocs] = useState<DocumentDto[]>([]);

  const fetchData = async () => {
    try {
      // TODO add the authorization header
      let url: string = `http://localhost:9080/api/petDetails?petID=${petID}`;
      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }); // Replace with your backend URL
      console.log(response.data);
      setPet(response.data);
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

  const processPet = () => {
    // Here we need to form our docs and dispach each type to its array

    if (pet != null) {
      if (pet.docs.length > 0) {
        let imagesArray: DocumentDto[] = [];
        let pdfsArray: DocumentDto[] = [];
        let otherDocsArray: DocumentDto[] = [];

        pet.docs.forEach((document) => {
          // Check for substrings in the type property

          if (document.type.includes("image")) {
            imagesArray.push(document);
          } else if (document.type.includes("pdf")) {
            pdfsArray.push(document);
          } else {
            otherDocsArray.push(document);
          }
        });

        // Update state with the organized arrays
        setImages(imagesArray);
        setPdfs(pdfsArray);
        setOtherDocs(otherDocsArray);
      }
    }
  };

  useEffect(() => {
    if (isMounted.current) {
      fetchData();
      isMounted.current = false;
    }
  }, []); // Empty dependency array means it runs once when component mounts

  useEffect(() => {
    // Run processPet when pet is updated
    processPet();
  }, [pet]); // pet is a dependency for this useEffect

  return (
    <>
      <Navbar
        shelterName={role === "adopter" ? "Pets Adoption" : shelterName}
        firstName={firstName || "Mahmoud"}
        lastName={lastName || "Attia"}
        role={role}
        token={token}
      />
      {pet != null && (
        <>
          <div
            className="container-fluid"
            style={{
              marginTop: "50px",
              display: "flex",
              justifyContent: "space-around",
              flexWrap: "wrap",
              marginBottom: "200px",
            }}
          >
            <div className="imagesCarousel col-6">
              <Carousel id="carouselExampleCaptions">
                {images.map((image, index) => (
                  <Carousel.Item key={index}>
                    <img
                      src={`data:${image.type};base64,${image.encodedFile}`}
                      className="d-block w-100"
                      alt={`Slide ${index + 1}`}
                      style={{ width: "50%" }}
                    />
                  </Carousel.Item>
                ))}
              </Carousel>
              {/* {images.length <= 2 && images.length >= 1 && (
                <>
                  <img
                    src={`data:${images[0].type};base64,${images[0].encodedFile}`}
                    className="d-block w-100"
                    alt={`Slide ${1}`}
                    // style={{ height: "50%" }}
                  />
                  {images.length == 2 && (
                    <>
                      <img
                        src={`data:${images[1].type};base64,${images[1].encodedFile}`}
                        className="d-block w-100"
                        alt={`Slide ${2}`}
                        style={{ marginTop: "30px" }}
                      />
                    </>
                  )}
                </>
              )}
              {images.length > 2 && (
                <>
                  <img
                    src={`data:${images[0].type};base64,${images[0].encodedFile}`}
                    className="d-block w-100"
                    alt={`Slide ${1}`}
                    style={{ height: "50%", marginBottom: "30px" }}
                  />
                  
                </>
              )} */}
              {/* <Carousel id="carouselExampleCaptions">
                {images.map((image, index) => (
                  <Carousel.Item key={index}>
                    <img
                      src={`data:${image.type};base64,${image.encodedFile}`}
                      className="d-block w-100"
                      alt={`Slide ${index + 1}`}
                    />
                  </Carousel.Item>
                ))}
              </Carousel> */}
            </div>
            <div className="petDetails" style={{ width: "40%" }}>
              <table>
                <tbody>
                  <tr className="tableFieldHeader">
                    <td
                      colSpan={2}
                      style={{ backgroundColor: "#f44336", color: "white" }}
                    >
                      Basic Info About the Pet
                    </td>
                  </tr>
                  <tr>
                    <td>Name</td>
                    <td>{pet.name}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>Species</td>
                    <td style={{ backgroundColor: "white" }}>{pet.species}</td>
                  </tr>
                  <tr>
                    <td>Breed</td>
                    <td>{pet.breed}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>Age</td>
                    <td style={{ backgroundColor: "white" }}>
                      {" "}
                      {pet.age > 1
                        ? `${pet.age} Years`
                        : `${pet.age * 12} Months`}
                    </td>
                  </tr>
                  <tr>
                    <td>Gender</td>
                    <td>{pet.male ? "Male" : "Female"}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>
                      Spaying/Neutering Status
                    </td>
                    <td style={{ backgroundColor: "white" }}>
                      {pet.spayed ? "Spayed" : "Not Spayed"}
                    </td>
                  </tr>
                  <tr>
                    <td>House-trained Status</td>
                    <td>
                      {pet.houseTrained ? "House-trained" : "Not house-trained"}
                    </td>
                  </tr>
                  <tr className="tableFieldHeader">
                    <td
                      colSpan={2}
                      style={{ backgroundColor: "#f44336", color: "white" }}
                    >
                      Health & Behaviour info
                    </td>
                  </tr>
                  <tr>
                    <td>Health Status</td>
                    <td>{pet.healthStatus}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>
                      Behaviour Status
                    </td>
                    <td style={{ backgroundColor: "white" }}>
                      {pet.behaviour}
                    </td>
                  </tr>
                  <tr>
                    <td>Description</td>
                    <td>{pet.description}</td>
                  </tr>
                  {pet.petVaccinations.length > 0 && (
                    <>
                      <tr>
                        <td
                          colSpan={2}
                          style={{ backgroundColor: "#f44336", color: "white" }}
                        >
                          Vaccination(s) taken
                        </td>
                      </tr>

                      <tr>
                        <td colSpan={2}>
                          <ul
                            style={{
                              display: "flex",
                              flexDirection: "column",
                              justifyContent: "center",
                              alignItems: "center",
                            }}
                          >
                            {pet.petVaccinations.map(
                              (petVaccination, index) => (
                                <>
                                  <li
                                    style={{
                                      textAlign: "start",
                                      width: "100%",
                                    }}
                                  >
                                    {petVaccination}
                                  </li>
                                </>
                              )
                            )}
                          </ul>
                        </td>
                      </tr>
                    </>
                  )}

                  <tr className="tableFieldHeader">
                    <td
                      colSpan={2}
                      style={{ backgroundColor: "#f44336", color: "white" }}
                    >
                      Shelter Details
                    </td>
                  </tr>
                  <tr>
                    <td>Shelter Name</td>
                    <td>{pet.shelter.name}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>Contact E-mail</td>
                    <td style={{ backgroundColor: "white" }}>
                      {pet.shelter.contactEmail}
                    </td>
                  </tr>
                  <tr>
                    <td>Contact phone</td>
                    <td>{pet.shelter.contactPhone}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div
            className="container-fluid docs"
            style={{
              marginTop: "50px",
              display: "flex",
              justifyContent: "space-around",
              flexWrap: "wrap",
              marginBottom: "100px",
              width: "93vw",
            }}
          >
            {pdfs.length > 0 && (
              <>
                <div className="pdfs" style={{ width: "100%" }}>
                  <h2 style={{ marginBottom: "50px", fontWeight: "500" }}>
                    PDF Document(s)
                  </h2>
                  <div
                    className="pdfContainer"
                    style={{
                      display: "flex",
                      flexDirection: "row",
                      flexWrap: "wrap",
                    }}
                  >
                    {pdfs.map((pdf, index) => (
                      <>
                        <h4>{pdf.docName}</h4>
                        <embed
                          src={`data:${pdf.type};base64,${pdf.encodedFile}`}
                          type={pdf.type}
                          style={{
                            width: "100vw",
                            height: "1202px",
                            marginBottom: "30px",
                          }}
                        />
                      </>
                    ))}
                  </div>
                </div>
              </>
            )}

            {otherDocs.length > 0 && (
              <>
                <div className="otherDocs" style={{ width: "100%" }}>
                  <h2 style={{ marginBottom: "50px", fontWeight: "500" }}>
                    Other Document(s)
                  </h2>
                  <div
                    className="otheDocsContainer"
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      justifyContent: "flex-start",
                      marginTop: "30px",
                    }}
                  >
                    {otherDocs.map((doc, index) => (
                      <>
                        <div style={{ marginBottom: "30px" }}>
                          <a
                            href={`data:${doc.type};base64,${doc.encodedFile}`}
                            download={`${doc.docName}`}
                          >
                            {/* <Button variant="primary">
                              <strong style={{ marginRight: "10px" }}>
                                {doc.docName}
                              </strong>{" "}
                              <FontAwesomeIcon icon={faDownload} />
                            </Button> */}
                            <ul
                              className="list-group list-group-horizontal"
                              style={{ wordWrap: "break-word" }}
                            >
                              <li
                                className="list-group-item"
                                style={{ width: "300px" }}
                              >
                                <h5>{doc.docName}</h5>
                              </li>
                              <li className="list-group-item">
                                <Button variant="primary">
                                  <strong style={{ marginRight: "10px" }}>
                                    Download
                                  </strong>
                                  <FontAwesomeIcon icon={faDownload} />
                                </Button>
                              </li>
                            </ul>
                          </a>
                        </div>

                        {/* <embed
                          src={`data:${pdf.type};base64,${pdf.encodedFile}`}
                          type={pdf.type}
                          style={{
                            width: "100vw",
                            height: "1202px",
                            marginBottom: "30px",
                          }}
                        /> */}
                      </>
                    ))}
                  </div>
                </div>
              </>
            )}
          </div>
        </>
      )}
    </>
  );
};

export default PetDetails;
