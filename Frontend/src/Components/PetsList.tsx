import React, { useEffect, useRef, useState } from "react";
import AddEditPet, { EditedPet } from "./AddEditPet";
import { useNavigate } from "react-router-dom";
import "./PetsList.css";
import Pagination from "./Pagination";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleCheck,
  faFilter,
  faSort,
} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import BobUpWindow from "./BobUpWindow";
import GenericAlertModal from "./GenericAlertModal";

interface Props {
  passedPets: PetDto[];
  firstName: string;
  lastName: string;
  role: string;
  userToken: string;
  shelterName: string;
  getPets: () => Promise<PetDto[]>;
  //   getSortedProducts: (sortBy: any, sortOrder: any) => Promise<Pet[]>;
  //   getFilteredProducts: (filter: FilterProductDto) => Promise<Product[]>;
}

const PetsList = ({
  firstName,
  lastName,
  role,
  userToken,
  shelterName,
  getPets,
  passedPets,
}: Props) => {
  const [pets, setPets] = useState<PetDto[]>([]);

  // Application response
  const [applicationResponse, setApplicationResponse] = useState("");

  // Pageination
  const [currentPage, setCurrentPage] = useState(1);
  const [petsPerPage, setPetsPerPage] = useState(10);
  const [fadeAnimation, setFadeAnimation] = useState("");

  // Edit and Add
  const [editPet, setEditPet] = useState(false);
  const [editedPet, setEditedPet] = useState<EditedPet>();

  // Sort and Filter
  const [sortParams, setSortParams] = useState({ sortBy: "", sortOrder: true });
  const [filterParams, setFilterParams] = useState({
    filterBy: "",
    filterCriteria: "",
  });

  const [showSortModal, setShowSortModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  // const [filter, setFilter] = useState<FilterProductDto>({ //TODO
  //   productName: '',
  //   fromPrice: 0,
  //   toPrice: 20000,
  //   description: '',
  //   available: false,
  //   brand: '',
  //   fromDiscountPercentage: 0,
  //   toDiscountPercentage: 100,
  //   category: ''
  // });

  const isMounted = useRef(true);
  const navigate = useNavigate();

  // const fetchData = async () => {
  //   const petss = await getPets();
  //   console.log("🚀 ~ file: PetsList.tsx:60 ~ fetchData ~ petss:", petss);
  //   setPets(petss);
  //   console.log("Pets: ", pets);
  // };

  // Get the pets once the component is mounted
  useEffect(() => {
    
    const fetchData = async () => {
      const petss = await getPets();
      console.log("🚀 ~ file: PetsList.tsx:60 ~ fetchData ~ petss:", petss);
      setPets(petss);
      console.log("Pets: ", pets);
    };

    const fetchSearchedData = async () => {
      // Set wishlist status
      for (let i = 0; i < passedPets.length; i++) {
        const pet = passedPets[i];
        // if (product.inWishlist) {
        //   setWishlistStatus((prevStatus) =>
        //     new Map(prevStatus).set(product.id, true)
        //   );
        // } else {
        //   setWishlistStatus((prevStatus) =>
        //     new Map(prevStatus).set(product.id, false)
        //   );
        // }
      }

      // Load images
      const updatedProducts = await Promise.all(
        passedPets.map(async (pet) => {
          try {
            pet.age = pet.age + 5;
            pet.age = pet.age - 5;
            return { ...pet, age: pet.age };
          } catch (error) {
            console.error("Error loading image:", error);
            return pet; // Return original product if image loading fails
          }
        })
      );

      setPets(updatedProducts);
    };

    console.log(passedPets);
    if (passedPets) {
      fetchSearchedData();
    } else fetchData();
  }, [passedPets, getPets]);

  // Get current pets
  const indexOfLastPet = currentPage * petsPerPage;
  const indexOfFirstPet = indexOfLastPet - petsPerPage;
  const currentPets = pets.slice(indexOfFirstPet, indexOfLastPet);

  // Change page
  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);
  const handlePaginationClick = (pageNumber: number) => {
    setFadeAnimation("fade-out");
    setTimeout(() => {
      paginate(pageNumber);
      setFadeAnimation("fade-in");
    }, 300);
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

  //TODO sorting
  const handleSortButtonClick = async () => {
    console.log(sortParams);

    //     const products = await getSortedProducts(sortParams.sortBy, sortParams.sortOrder);
    //     // Set wishlist status
    //     for (let i = 0; i < products.length; i++) {
    //       const product = products[i];
    //       if (product.inWishlist) {
    //         setWishlistStatus((prevStatus) =>
    //           new Map(prevStatus).set(product.id, true)
    //         );
    //       } else {
    //         setWishlistStatus((prevStatus) =>
    //           new Map(prevStatus).set(product.id, false)
    //         );
    //       }

    //     const updatedProducts = await Promise.all(
    //       products.map(async (product) => {
    //         try {
    //           const dynamicImportedImage = await import(
    //             `../assets${product.imageLink}`
    //           );
    //           return { ...product, imageLink: dynamicImportedImage.default };
    //         } catch (error) {
    //           console.error("Error loading image:", error);
    //           return product; // Return original product if image loading fails
    //         }
    //       })
    //     );
    //     setProducts(updatedProducts);
    //   };
  };

  //TODO filtering
  const handleFilterButtonClick = async () => {
    console.log(filterParams);
    //     const products = await getSortedProducts(sortParams.sortBy, sortParams.sortOrder);
    //     // Set wishlist status
    //     for (let i = 0; i < products.length; i++) {
    //       const product = products[i];
    //       if (product.inWishlist) {
    //         setWishlistStatus((prevStatus) =>
    //           new Map(prevStatus).set(product.id, true)
    //         );
    //       } else {
    //         setWishlistStatus((prevStatus) =>
    //           new Map(prevStatus).set(product.id, false)
    //         );
    //       }

    //     const updatedProducts = await Promise.all(
    //       products.map(async (product) => {
    //         try {
    //           const dynamicImportedImage = await import(
    //             `../assets${product.imageLink}`
    //           );
    //           return { ...product, imageLink: dynamicImportedImage.default };
    //         } catch (error) {
    //           console.error("Error loading image:", error);
    //           return product; // Return original product if image loading fails
    //         }
    //       })
    //     );
    //     setProducts(updatedProducts);
    //   };
  };

  const toggleSortModal = () => {
    setShowSortModal((prev) => !prev);
  };

  const toggleFilterModal = () => {
    setShowFilterModal((prev) => !prev);
  };

  // Edit
  const handleEditPet = (pet: PetDto) => {
    console.log(pet);
    // Your logic for editing a pet can go here
    setEditedPet({
      id: String(pet.id),
      name: pet.name,
      isMale: pet.male ? "Male" : "Female",
      isHouseTrained: pet.houseTrained ? "true" : "false",
      description: pet.description,
      healthStatus: pet.healthStatus,
      age: String(pet.age),
      behaviour: pet.behaviour,
      breed: pet.breed,
      species: pet.species,
      isSpayed: pet.spayed ? "true" : "false",
      available: pet.available ? "true" : "false",
      shelterName: pet.shelter.name,
      petVaccinations: pet.petVaccinations,
    });
    setEditPet(true);
  };

  const resetEditButton = () => {
    setEditPet(false);
  };

  const handleAppliyClicled = async (pet: PetDto) => {
    try {
      let application: ApplicationRequestDto = {
        petID: pet.id,
        shelterID: pet.shelter.id,
      };
      let url: string = `http://localhost:9080/api/application/create`;
      const response = await axios.post(url, application, {
        headers: {
          Authorization: `Bearer ${userToken}`,
          "Content-Type": "application/json",
        },
      });
      // Here means that the response is Ok and the product is added successfully
      setApplicationResponse(response.data);
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
          setApplicationResponse(axiosError.response.data as string);
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

  const resetApplicationResponse = () => {
    setApplicationResponse("");
  };

  return (
    <div>
      {applicationResponse === "Application submitted Successfully" && (
        <>
          <GenericAlertModal
            show={true}
            onClose={resetApplicationResponse}
            resetResponseData={resetApplicationResponse}
            body={
              <>
                <p>
                  <FontAwesomeIcon
                    icon={faCircleCheck}
                    style={{
                      color: "green",
                      fontSize: "18px",
                      marginRight: "10px",
                    }}
                  />
                  {applicationResponse}
                </p>
              </>
            }
          ></GenericAlertModal>
        </>
      )}
      {applicationResponse !== "Application submitted Successfully" &&
        applicationResponse !== "" && (
          <>
            <GenericAlertModal
              show={true}
              onClose={resetApplicationResponse}
              resetResponseData={resetApplicationResponse}
              body={
                <>
                  <p style={{ color: "red" }}>{applicationResponse}</p>
                </>
              }
            ></GenericAlertModal>
          </>
        )}
      {editPet && (
        <>
          <AddEditPet
            isEdit={true}
            adminToken={userToken}
            pet={editedPet}
            resetButton={resetEditButton}
          />
        </>
      )}

      <button className="sort-button" onClick={toggleSortModal}>
        <FontAwesomeIcon icon={faSort} /> Sort
      </button>

      {/* TODO */}
      <button
        className="btn btn-primary btn-filter"
        onClick={toggleFilterModal}
      >
        <FontAwesomeIcon icon={faFilter} /> Filter
      </button>
      <div
        className={`products-list ${fadeAnimation}`}
        style={{ marginTop: "30px" }}
      >
        {currentPets.map((pet) => (
          <div
            key={pet.id}
            className="product-card"
            onClick={() => handlePetClicked(pet)}
          >
            <div
              style={{
                width: "85%",
                height: "50%",
                position: "relative",
                marginBottom: "8px",
                top: "0",
                left: "50%",
                transform: "translate(-50%, 0)",
              }}
            >
              <img
                src={`data:${pet.docs[0].type};base64,${pet.docs[0].encodedFile}`}
                alt={pet.docs[0].docName}
                style={{ width: "100%", height: "auto", borderRadius: "5px" }}
              />
            </div>
            <div
              style={{
                position: "relative",
                marginTop: "20px",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
              }}
            >
              <table>
                <tbody>
                  <tr>
                    <td>Name</td>
                    <td>{pet.name}</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>Gender</td>
                    <td style={{ backgroundColor: "white" }}>
                      {pet.male ? "Male" : "Felmale"}
                    </td>
                  </tr>
                  <tr>
                    <td>Age</td>
                    <td>{pet.age} years</td>
                  </tr>
                  <tr>
                    <td style={{ backgroundColor: "white" }}>Breed</td>
                    <td style={{ backgroundColor: "white" }}>{pet.breed}</td>
                  </tr>
                  <tr>
                    <td>Shelter</td>
                    <td>{pet.shelter.name}</td>
                  </tr>
                </tbody>
              </table>

              {(role === "manager" || role === "staff") && (
                <button
                  className="btn btn-primary"
                  type="button"
                  onClick={(e) => {
                    e.stopPropagation();
                    handleEditPet(pet);
                  }}
                  style={{ width: "100%", marginTop: "15px" }}
                >
                  <strong>Edit</strong>
                </button>
              )}
              {role === "adopter" && (
                <>
                  <button
                    className={`btn btn-${
                      pet.available ? "primary" : "danger"
                    }`}
                    type="button"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleAppliyClicled(pet);
                    }}
                    style={{ width: "100%", marginTop: "15px" }}
                    disabled={!pet.available}
                  >
                    <strong>
                      {pet.available
                        ? "Apply For Adoption"
                        : "Not Available for doption"}
                    </strong>
                  </button>
                </>
              )}
            </div>
          </div>
        ))}
      </div>
      <footer>
        <Pagination
          itemsPerPage={petsPerPage}
          totalItems={pets.length}
          paginate={handlePaginationClick}
          currentPage={currentPage}
        />
      </footer>
      {showSortModal && (
        <div className="sort-modal">
          <div className="sort-modal-content">
            <div className="sort-option">
              <label className="sort-label">
                Sort by:
                <select
                  className="sort-select"
                  onChange={(e) =>
                    setSortParams((prev) => ({
                      ...prev,
                      sortBy: e.target.value,
                    }))
                  }
                  value={sortParams.sortBy}
                >
                  <option value="">Select Criteria</option>
                  <option value="productName">Name</option>
                  <option value="price">Price</option>
                  <option value="averageRating">Rating</option>
                  <option value="numberOfReviews">Reviews</option>
                  <option value="postedDate">Date Added</option>
                  <option value="productCountAvailable">
                    Remaining in Stock
                  </option>
                  <option value="productSoldCount">Sold Count</option>
                  <option value="brand">Brand</option>
                </select>
              </label>
            </div>
            <div className="sort-order">
              <label className="sort-label">
                <input
                  type="radio"
                  name="sortOrder"
                  checked={sortParams.sortOrder}
                  onChange={() =>
                    setSortParams((prev) => ({ ...prev, sortOrder: true }))
                  }
                />
                Ascending
              </label>
              <label className="sort-label">
                <input
                  type="radio"
                  name="sortOrder"
                  checked={!sortParams.sortOrder}
                  onChange={() =>
                    setSortParams((prev) => ({ ...prev, sortOrder: false }))
                  }
                />
                Descending
              </label>
            </div>
            <div className="modal-buttons">
              <button
                className="apply-button"
                onClick={() => {
                  handleSortButtonClick();
                  toggleSortModal();
                }}
              >
                Apply
              </button>
              <button className="cancel-button" onClick={toggleSortModal}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

      {showFilterModal && (
        <div className="sort-modal">
          <div className="sort-modal-content">
            <div className="sort-option">
              <label className="sort-label">
                Filter Criteria:
                <select
                  className="sort-select"
                  onChange={(e) =>
                    setFilterParams((prev) => ({
                      ...prev,
                      filterBy: e.target.value,
                    }))
                  }
                  value={filterParams.filterCriteria}
                >
                  <option value="">Select Criteria</option>
                  <option value="productName">Name</option>
                  <option value="price">Price</option>
                  <option value="averageRating">Rating</option>
                  <option value="numberOfReviews">Reviews</option>
                  <option value="postedDate">Date Added</option>
                  <option value="productCountAvailable">
                    Remaining in Stock
                  </option>
                  <option value="productSoldCount">Sold Count</option>
                  <option value="brand">Brand</option>
                </select>
              </label>
            </div>
            <div
              className="sort-order"
              style={{ display: "flex", alignItems: "center" }}
            >
              <label className="sort-label" style={{ marginRight: "10px" }}>
                Filter By
              </label>
              <input
                type="text"
                name="filterCriteria"
                value={filterParams.filterCriteria}
                onChange={(e) =>
                  setFilterParams((prev) => ({
                    ...prev,
                    filterCriteria: e.target.value,
                  }))
                }
              />
            </div>
            <div className="modal-buttons">
              <button
                className="apply-button"
                onClick={() => {
                  handleFilterButtonClick();
                  toggleFilterModal();
                }}
              >
                Apply
              </button>
              <button className="cancel-button" onClick={toggleFilterModal}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default PetsList;
