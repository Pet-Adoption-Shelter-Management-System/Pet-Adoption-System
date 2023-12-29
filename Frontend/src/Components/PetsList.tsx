import React, { useEffect, useRef, useState } from "react";
import AddEditPet, { EditedPet } from "./AddEditPet";
import { useNavigate } from "react-router-dom";
import "./PetsList.css";
import Pagination from "./Pagination";

interface Props {
  passedPets: PetDto[];
  firstName: string;
  lastName: string;
  role: string;
  userToken: string;
  shelterName?: string;
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

  // Pageination
  const [currentPage, setCurrentPage] = useState(1);
  const [petsPerPage, setPetsPerPage] = useState(15);
  const [fadeAnimation, setFadeAnimation] = useState("");

  // Edit and Add
  const [editPet, setEditPet] = useState(false);
  const [editedPet, setEditedPet] = useState<EditedPet>();

  // Sort and Filter
  const [sortParams, setSortParams] = useState({ sortBy: "", sortOrder: true });
  const [showSortModal, setShowSortModal] = useState(false);
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

  const fetchData = async () => {
    const petss = await getPets();
    console.log("🚀 ~ file: PetsList.tsx:60 ~ fetchData ~ petss:", petss);
    setPets(petss);
    console.log("Pets: ", pets);
  };

  // Get the pets once the component is mounted
  useEffect(() => {
    if (isMounted.current) {
      if (passedPets.length > 0) setPets(passedPets);
      else fetchData();
      isMounted.current = false;
    }
  }, []);

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

  // TODO go to the PetDetails
  const handlePetClicked = (pet: PetDto) => {
    //   const id = product.id;
    //   navigate("/product-details", {
    //     state: {
    //       firstName: firstName,
    //       lastName: lastName,
    //       isAdmin: isAdmin,
    //       productID: id,
    //       token: userToken,
    //     },
    //   });
  };

  //TODO sorting
  const handleSortButtonClick = async () => {
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
  };

  //     // Load images
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

  const toggleSortModal = () => {
    setShowSortModal((prev) => !prev);
  };

  //TODO filter
  //   const handleFilterButtonClick = async() => {
  //     const products = await getFilteredProducts(filter);

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
  //     }

  //     // Load images
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

  //   const handleInputChange = (e: any) => {
  //     const { name, value, type, checked } = e.target;
  //     setFilter(prevFilter => ({
  //       ...prevFilter,
  //       [name]: type === 'checkbox' ? checked : value
  //     }));
  //   };

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
      shelterName: pet.shelterName,
      petVaccinations: pet.petVaccinations,
    });
    setEditPet(true);
  };

  const resetEditButton = () => {
    setEditPet(false);
  };

  const handleAppliyClicled = (pet: PetDto) => {
    //TODO
  };

  return (
    <div>
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
        Sort
      </button>

      {/* TODO */}
      {/* <button className="btn btn-primary btn-filter" type="button" data-bs-toggle="offcanvas"
                  data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">
        <i className="fas fa-filter"></i>
      </button>
      <div className="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false"
       tabIndex={-1} id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
        <div className="offcanvas-header">
          <h5 className="offcanvas-title" id="offcanvasScrollingLabel">Filter Options</h5>
            <button type="button" className="btn-close text-reset" data-bs-dismiss="offcanvas"
                  aria-label="Close"></button>
        </div>
        <div className="offcanvas-body">
          <label>Product Name:</label>
          <input type="text" name="productName" className="form-control" value={filter.productName} onChange={handleInputChange} />

          <div className="mb-3">
            <label htmlFor="minPrice">Minimum Price: <span id="minPriceValue">0</span></label>
            <input type="range" className="form-range" min="0" max="20000" id="minPrice" onInput={updateMinPrice}
             name="fromPrice" value={filter.fromPrice} onChange={handleInputChange}/>
          </div>

          <div className="mb-3">
            <label htmlFor="maxPrice">Maximum Price: <span id="maxPriceValue">20000</span></label>
            <input type="range" className="form-range" min="0" max="20000" id="maxPrice" onInput={updateMaxPrice}
             name="toPrice" value={filter.toPrice} onChange={handleInputChange}/>
          </div>

          <label>Product Description:</label>
          <input type="text" name="description" className="form-control" value={filter.description} onChange={handleInputChange} />

          <div className="mb-3 mt-3">
            <label className="form-check-label" htmlFor="outOfStockCheckbox">
              Include Out of Stock
            </label>
            <input name="available" className="form-check-input" type="checkbox" id="outOfStockCheckbox" 
            checked={filter.available} style={{ marginLeft: "10px", position: "relative", bottom: "4px" }} onChange={handleInputChange} />
          </div>

          <label>Brand:</label>
          <input type="text" name="brand" className="form-control" value={filter.brand} onChange={handleInputChange} />

          <div className="mb-3">
            <label htmlFor="minDiscountPercentage">Minimum Discount Percentage: <span id="minDiscountPercentageValue">0</span></label>
            <input type="range" className="form-range" min="0" max="100" id="minDiscountPercentage" onInput={updateMinDiscountPercentage}
             name="fromDiscountPercentage" value={filter.fromDiscountPercentage} onChange={handleInputChange}/>
          </div>

          <div className="mb-3">
            <label htmlFor="maxDiscountPercentage">Maximum Discount Percentage: <span id="maxDiscountPercentageValue">100</span></label>
            <input type="range" className="form-range" min="0" max="100" id="maxDiscountPercentage" onInput={updateMaxDiscountPercentage}
             name="toDiscountPercentage" value={filter.toDiscountPercentage} onChange={handleInputChange}/>
          </div>

          <label>Category:</label>
          <input type="text" name="category" className="form-control" value={filter.category} onChange={handleInputChange} />

          <div className="d-flex justify-content-end mt-2">
            <Button variant="primary" onClick={() => handleFilterButtonClick()}>
              Filter
            </Button>
          </div>
        </div>
      </div> */}

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
                    <td>{pet.shelterName}</td>
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
                    className="btn btn-primary"
                    type="button"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleAppliyClicled(pet);
                    }}
                    style={{ width: "100%", marginTop: "15px" }}
                  >
                    <strong>Apply For Adoption</strong>
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
    </div>
  );
};

export default PetsList;
