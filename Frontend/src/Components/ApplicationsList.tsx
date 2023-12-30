import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ApplicationsList.css";
import Pagination from "./Pagination";
import { Button, Modal } from "react-bootstrap";
import ConfirmationModal from "./GenericConfirmationModal";
import AlertModal from "./AlertModal";

interface ApplicationsListProps {
  getApplications: () => Promise<ApplicationDto[]>;
  updateApplicationStatus: (
    applicationID: number,
    newStatus: string
  ) => Promise<string>;
  role: string;
  firstName: string;
  lastName: string;
  shelterName: string;
  userToken: string;
}

const formatDate = (dateString: string): string => {
  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "long",
    day: "numeric",
  };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

const ApplicationsList = ({
  getApplications,
  updateApplicationStatus,
  role,
  firstName,
  lastName,
  shelterName,
  userToken,
}: ApplicationsListProps) => {
  const nullDocument: DocumentDto = {
    docName: "",
    type: "",
    encodedFile: new ArrayBuffer(0),
  };

  const nullShelter: ShelterDto = {
    id: 0,
    name: "",
    location: "",
    contactPhone: "",
    contactEmail: "",
  };

  const nullPet: PetDto = {
    id: 0,
    name: "",
    male: false,
    houseTrained: false,
    description: "",
    healthStatus: "",
    age: 0,
    behaviour: "",
    breed: "",
    species: "",
    spayed: false,
    available: false,
    shelter: nullShelter,
    petVaccinations: [],
    docs: [],
  };

  const nullApplicationDto: ApplicationDto = {
    id: 0,
    adopterID: 0,
    firstName: "",
    lastName: "",
    contactPhone: "",
    email: "",
    address: "",
    petDto: nullPet,
    date: "",
    status: "",
  };

  const [applications, setApplications] = useState<ApplicationDto[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [applicationsPerPage] = useState(9);
  const [showModal, setShowModal] = useState(false);
  const [selectedApplication, setSelectedApplication] =
    useState<ApplicationDto>(nullApplicationDto);
  const [fadeAnimation, setFadeAnimation] = useState("");
  const [fadeAnimationSingle, setFadeAnimationSingle] = useState("");
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [confirmModalContent, setConfirmModalContent] = useState({
    message: "",
    onConfirm: () => {},
  });
  const [showAlertModal, setShowAlertModal] = useState(false);
  const [alertModalContent, setAlertModalContent] = useState({
    message: "",
    onConfirm: () => {},
  });

  const navigate = useNavigate();

  const handleApplicationClick = (application: ApplicationDto) => {
    setSelectedApplication(application);
    setShowModal(true);
  };

  const handleClose = () => {
    setShowModal(false);
  };

  const handleStatusChange = (
    application: ApplicationDto,
    newStatus: string
  ) => {
    updateApplicationStatus(application.id, newStatus).then((response) => {
      if (response.includes("Status updated")) {
        selectedApplication.status = newStatus;
        setAlertModalContent({
          message: "Status updated successfully",
          onConfirm: () => {
            handleClose();
            setTimeout(() => {
              handleApplicationClick(selectedApplication);
            }, 0);
          },
        });
      } else {
        setAlertModalContent({
          message: response,
          onConfirm: () => {
            handleClose();
            setTimeout(() => {
              handleApplicationClick(selectedApplication);
            }, 0);
          },
        });
      }
    });
    setShowAlertModal(true);
  };

  const formatAge = (age: number): string => {
    const years = Math.floor(age);
    const months = Math.floor((age - years) * 12);
    if (years === 0) return `${months} months`;
    else return `${years} years ${months} months`;
  };

  const handlePetClicked = (pet: PetDto) => {
    const id = pet.id;
    navigate("/petDetails", {
      state: {
        firstName: firstName,
        lastName: lastName,
        role: role,
        petID: id,
        token: userToken,
        shelterName: shelterName,
      },
    });
  };

  const handFetchedApplications = useRef(false);
  useEffect(() => {
    if (handFetchedApplications.current) {
      return;
    }
    handFetchedApplications.current = true;
    const fetchApplications = async () => {
      getApplications().then((applications) => setApplications(applications));
    };
    fetchApplications();
  }, []);

  // Get current orders
  const indexOfLastApplication = currentPage * applicationsPerPage;
  const indexOfFirstApplication = indexOfLastApplication - applicationsPerPage;
  const currentApplications = applications.slice(
    indexOfFirstApplication,
    indexOfLastApplication
  );

  // Change page
  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);
  const handlePaginationClick = (pageNumber: number) => {
    setFadeAnimation("fade-out");
    setTimeout(() => {
      paginate(pageNumber);
      setFadeAnimation("fade-in");
    }, 300);
  };

  const getColor = (status: string) => {
    if (status === "Pending") return "gray";
    else if (status === "Approved") return "#088611b1";
    else return "#dc3545";
  };

  return (
    <div>
      {applications.length === 0 && role === "adopter" && (
        <h1 className="semi-faded-text mb-0 d-flex justify-content-center align-items-center">
          Refer to the pets page to see available-for-adoption pets
        </h1>
      )}
      {applications.length === 0 && role !== "adopter" && (
        <h1 className="semi-faded-text mb-0 d-flex justify-content-center align-items-center">
          No applications have been made yet to your shelter
        </h1>
      )}
      <div
        className={`orders-list ${fadeAnimation}`}
        style={{ marginTop: "30px" }}
      >
        {currentApplications.map((application) => (
          <div
            key={application.id}
            className="order-card"
            onClick={() => handleApplicationClick(application)}
          >
            <table>
              <tbody>
                {role !== "adopter" && (
                  <>
                    <tr className="tableFieldHeader">
                      <td
                        colSpan={2}
                        style={{ backgroundColor: "#6198d3", color: "white" }}
                      >
                        Basic Info About the Adopter
                      </td>
                    </tr>
                    <tr>
                      <td>Adopter ID</td>
                      <td>{application.adopterID}</td>
                    </tr>
                    <tr>
                      <td>Adopter First Name</td>
                      <td>{application.firstName}</td>
                    </tr>
                    <tr>
                      <td>Adopter Last Name</td>
                      <td>{application.lastName}</td>
                    </tr>
                    <tr>
                      <td>Adopter E-mail</td>
                      <td>{application.email}</td>
                    </tr>
                  </>
                )}
                <tr className="tableFieldHeader">
                  <td
                    colSpan={2}
                    style={{ backgroundColor: "#6198d3", color: "white" }}
                  >
                    Basic Info about the Application
                  </td>
                </tr>
                <tr>
                  <td>Application ID</td>
                  <td>{application.id}</td>
                </tr>
                <tr>
                  <td>Pet Name</td>
                  <td>{application.petDto.name}</td>
                </tr>
                <tr>
                  <td>Application Status</td>
                  <td style={{ color: `${getColor(application.status)}` }}>
                    <strong>{application.status}</strong>
                  </td>
                </tr>
                <tr>
                  <td>Date</td>
                  <td>{formatDate(application.date)}</td>
                </tr>
                <tr className="tableFieldHeader">
                  <td
                    colSpan={2}
                    style={{ backgroundColor: "#6198d3", color: "white" }}
                  >
                    Basic Info about the Shelter
                  </td>
                </tr>
                <tr>
                  <td>Shelter Name</td>
                  <td>{application.petDto.shelter.name}</td>
                </tr>
                <tr>
                  <td>Shelter Location</td>
                  <td>{application.petDto.shelter.location}</td>
                </tr>
                <tr>
                  <td>Contact phone</td>
                  <td>{application.petDto.shelter.contactPhone}</td>
                </tr>
                <tr>
                  <td>Contact E-mail</td>
                  <td>{application.petDto.shelter.contactEmail}</td>
                </tr>
              </tbody>
            </table>
          </div>
        ))}
      </div>
      <footer>
        <Pagination
          itemsPerPage={applicationsPerPage}
          totalItems={applications.length}
          paginate={handlePaginationClick}
          currentPage={currentPage}
        />
      </footer>
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Application Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {role !== "adopter" && (
            <>
              <p>
                <strong>Adopter's ID:</strong> {selectedApplication.adopterID}
              </p>
              <p>Modify the status.</p>
            </>
          )}
          <div className={`products-container ${fadeAnimationSingle}`}>
            <table
              className="order-items-table"
              onClick={() => handlePetClicked(selectedApplication.petDto)}
            >
              <tbody>
                <tr>
                  <td>Pet Name</td>
                  <td>{selectedApplication.petDto.name}</td>
                </tr>
                <tr>
                  <td>Gender</td>
                  <td>{selectedApplication.petDto.male ? "Male" : "Female"}</td>
                </tr>
                <tr>
                  <td>Age</td>
                  <td>{formatAge(selectedApplication.petDto.age)}</td>
                </tr>
                <tr>
                  <td>Breed</td>
                  <td>{selectedApplication.petDto.breed}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div className="status-btn-container">
            <p>
              Status:{" "}
              <strong
                style={{ color: `${getColor(selectedApplication.status)}` }}
              >
                <strong>{selectedApplication.status}</strong>
              </strong>
            </p>
            {role !== "adopter" && (
              <>

                <Button
                  className="status-btn"
                  variant="success"
                  onClick={() =>
                    handleStatusChange(selectedApplication, "Approved")
                  }
                  disabled={
                    selectedApplication.status === "Approved" ||
                    selectedApplication.status === "Rejected"
                  }
                >
                  Mark as Approved
                </Button>
                <Button
                  className="status-btn"
                  variant="danger"
                  onClick={() =>
                    handleStatusChange(selectedApplication, "Rejected")
                  }
                  disabled={
                    selectedApplication.status === "Approved" ||
                    selectedApplication.status === "Rejected"
                  }
                >
                  Mark as Rejected
                </Button>
              </>
            )}
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <ConfirmationModal
        show={showConfirmModal}
        message={confirmModalContent.message}
        onConfirm={() => {
          confirmModalContent.onConfirm();
          setShowConfirmModal(false);
        }}
        onCancel={() => setShowConfirmModal(false)}
      />
      <AlertModal
        show={showAlertModal}
        message={alertModalContent.message}
        onConfirm={() => {
          alertModalContent.onConfirm();
          setShowAlertModal(false);
        }}
      />
    </div>
  );
};

export default ApplicationsList;
