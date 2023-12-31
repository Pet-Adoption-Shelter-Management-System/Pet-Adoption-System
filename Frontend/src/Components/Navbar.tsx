import React, { useState, useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faList,
  faHeart,
  faShoppingCart,
  faSignOutAlt,
  faSearch,
  faUserPlus,
} from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "./Navbar.css"; // Make sure to create this CSS file
import axios from "axios";
import AddAdminPopup from "./AddAdminPopup";
import { Button } from "react-bootstrap";
import AddEditPet from "./AddEditPet";
// import AddAdminPopup from "./AddAdminPopup";

interface NavbarProps {
  shelterName: string;
  firstName: string;
  lastName: string;
  role: string; // adopter/manager/staff
  token: string;
  isPets?: boolean;
  isApplication?: boolean;
  isStaffInfo?: boolean;
  isShelterInfo?: boolean;
}

const Navbar: React.FC<NavbarProps> = ({
  shelterName,
  firstName,
  lastName,
  role,
  token,
  isPets = false,
  isApplication = false,
  isStaffInfo = false,
  isShelterInfo = false,
}) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLLIElement>(null);
  const [isSearchFocused, setIsSearchFocused] = useState(false);
  const navigate = useNavigate();
  const [isCartHovered, setIsCartHovered] = useState(false);
  const [isHeartHovered, setIsHeartHovered] = useState(false);
  const [isMyOrderHovered, setIsMyOrderHovered] = useState(false);
  const [isProfileHovered, setIsProfileHovered] = useState(false);
  const [isAddAdminHovered, setIsAddAdminHovered] = useState(false);
  const [isLogoutHovered, setIsLogoutHovered] = useState(false);
  const [searchKey, setSearchKey] = useState("");
  const [showAddAdminPopup, setShowAddAdminPopup] = useState(false);

  const [addNewPet, setAddNewPet] = useState(false);

  const handleAddAdminClick = () => {
    setShowAddAdminPopup(true);
  };

  const handleAddNewPetClick = () => {
    setAddNewPet(true);
  };

  const resetAddNewPet = () => {
    setAddNewPet(false);
  };

  const handleCloseAddAdminPopup = () => {
    setShowAddAdminPopup(false);
  };

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (
      dropdownRef.current &&
      !dropdownRef.current.contains(event.target as Node)
    ) {
      setDropdownOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleClickOutside);

    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

  const handlePetsClick = () => {
    navigate("/PetsPage", {
      state: {
        userToken: token,
        from: "Details",
        shelterName: shelterName,
        role: role,
        pets: [],
      },
    });
  };

  const handleProfileClick = () => {
    navigate("/profile", {
      state: {
        shelterName: shelterName,
        firstName: firstName,
        lastName: lastName,
        token: token,
        role: role,
      },
    });
  };

  const handleLogoutClick = () => {
    navigate("/");
  };

  const handleApplicationClick = () => {
    role !== "adopter" 
      ? navigate("/dashboard", {
          state: {
            shelterName: shelterName,
            firstName: firstName,
            lastName: lastName,
            token: token,
            role: role,
          },
        })
      : navigate("/applications", {
          state: {
            shelterName: shelterName,
            firstName: firstName,
            lastName: lastName,
            token: token,
            role: role,
          },
        });
  };

  const handleStaffClick = () => {
    navigate("/staffInfo", {
      state: {
        shelterName: shelterName,
        firstName: firstName,
        lastName: lastName,
        token: token,
        role: role,
      },
    });
  };

  const handleShelterClick = () => {
    navigate("/shelterInfo", {
      state: {
        shelterName: shelterName,
        firstName: firstName,
        lastName: lastName,
        token: token,
        role: role,
      },
    });
  };

  const handleSearchKeyChange = (e: any) => {
    setSearchKey(e.target.value);
  };

  function toHomePage(): void {
    navigate("/PetsPage");
  }

  const [isHomeUse, setIsHomeUse] = useState(true);
  const [isNavbarVisible, setIsNavbarVisible] = useState(true);


  const getSearchedPets = async () => {
    console.log ("key:  ", searchKey)
    let url: string = "";
    let entity: string = "pet";
    let criteria: string = "search";
    if (role === "adopter") {
      url = `http://localhost:9080/api/filter/customerFilterEntity/${entity}/${criteria}/${searchKey}`;
    } else {
      url = `http://localhost:9080/api/filter/employeeFilterEntity/${entity}/${criteria}/${searchKey}/${shelterName}`;
    }
    try {
      const response = await axios(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(response.data);
      const pets: PetDto[] = response.data;
      return pets;
    } catch (error) {
      console.log("Error:", error);
      const pets: PetDto[] = [];
      return pets;
    }
  };

  const handleSearchPets = async (event: any) => {
    event.preventDefault();

    const returnedPets = await getSearchedPets();

    console.log(
      "🚀 ~ file: Navbar.tsx:232 ~ handleSearchPets ~ returnedPets:",
      returnedPets
    );

    navigate("/PetsPage", {
      state: {
        userToken: token,
        from: "Search",
        shelterName: shelterName,
        role: role,
        passedPets: returnedPets,
      },
    });
  };

  return (
    <>
      {addNewPet && (
        <>
          <AddEditPet
            isEdit={false}
            adminToken={token}
            resetButton={resetAddNewPet}
          />
        </>
      )}

      <div
        style={{ borderBottom: "1px solid #ccc" }}
        className={`shadow-sm sticky-top`}
      >
        <div
          style={{
            backgroundColor: "rgb(21, 15, 1)",
          }}
          className="container-fluid"
        >
          <div className="row">
            <div className="col-md-2 my-auto d-none d-sm-none d-md-block d-lg-block">
              <h5 style={{ color: "#fff" }} className="brand-name">
                {shelterName}
              </h5>
            </div>
            <div className="col-md-5 my-auto">
              <form role="search">
                <div
                  className={`input-group ${
                    isSearchFocused ? "search-focused" : ""
                  }`}
                >
                  <input
                    type="search"
                    placeholder="Search your pet"
                    className="form-control bg-white"
                    onFocus={() => setIsSearchFocused(true)}
                    onBlur={() => setIsSearchFocused(false)}
                    value={searchKey}
                    onChange={handleSearchKeyChange}
                  />
                  <button
                    className={`btn ${
                      isSearchFocused ? "btn-secondary" : "btn-light"
                    }`}
                    onClick={handleSearchPets}
                  >
                    <FontAwesomeIcon icon={faSearch} />
                  </button>
                </div>
              </form>
            </div>
            <div className="col-md-5 my-auto">
              <ul className="nav justify-content-end">
                {(role === "manager" || role === "staff") && (
                  <>
                    <li>
                      <Button
                        variant="success"
                        style={{ marginRight: "50px", padding: "10px" }}
                        onClick={handleAddNewPetClick}
                      >
                        <strong>Add New Pet</strong>
                      </Button>
                    </li>
                  </>
                )}

                <li className="nav-item dropdown" ref={dropdownRef}>
                  <button
                    className="nav-link dropdown-toggle nav-bar-icons"
                    id="navbarDropdown"
                    role="button"
                    onClick={toggleDropdown}
                    aria-expanded={dropdownOpen}
                    style={{ color: "white" }}
                  >
                    <FontAwesomeIcon icon={faUser} />{" "}
                    {firstName + " " + lastName}
                  </button>
                  <ul
                    className={`dropdown-menu${dropdownOpen ? " show" : ""}`}
                    aria-labelledby="navbarDropdown"
                  >
                    <li>
                      <button
                        className="dropdown-item nav-bar-icons"
                        onClick={handleProfileClick}
                        onMouseEnter={() => setIsProfileHovered(true)}
                        onMouseLeave={() => setIsProfileHovered(false)}
                        style={{
                          color: isProfileHovered ? "#064fc4" : "#000",
                          transition: "color 0.3s ease",
                        }}
                      >
                        <FontAwesomeIcon icon={faUser} /> Profile
                      </button>
                    </li>
                    {role === "manager" && (
                      <li>
                        <button
                          className="dropdown-item nav-bar-icons"
                          onClick={handleAddAdminClick}
                          onMouseEnter={() => setIsAddAdminHovered(true)}
                          onMouseLeave={() => setIsAddAdminHovered(false)}
                          style={{
                            color: isAddAdminHovered ? "#064fc4" : "#000",
                            transition: "color 0.3s ease",
                          }}
                        >
                          <FontAwesomeIcon icon={faUserPlus} /> Add Employee
                        </button>
                      </li>
                    )}

                    <li>
                      <button
                        className="dropdown-item nav-bar-icons"
                        onClick={handleLogoutClick}
                        onMouseEnter={() => setIsLogoutHovered(true)}
                        onMouseLeave={() => setIsLogoutHovered(false)}
                        style={{
                          color: isLogoutHovered ? "#064fc4" : "#000",
                          transition: "color 0.3s ease",
                        }}
                      >
                        <FontAwesomeIcon icon={faSignOutAlt} /> Logout
                      </button>
                    </li>
                  </ul>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <nav
          className="downnav navbar navbar-expand-lg"
          style={{ padding: "0px", backgroundColor: "white" }}
        >
          <div className="container-fluid">
            <button className="navbar-brand d-block d-sm-block d-md-none d-lg-none">
              {shelterName}
            </button>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>

            <div
              className="collapse navbar-collapse"
              id="navbarSupportedContent"
            >
              <ul className="navbar-nav ms-auto me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <button
                    className="nav-link nav-bar-icons"
                    onClick={handlePetsClick}
                    style={{
                      color: "black",
                      backgroundColor: `${
                        isPets! ? "rgb(133, 133, 133)" : "none"
                      }`,
                    }}
                  >
                    <h5>Pets</h5>
                  </button>
                </li>
                <li className="nav-item">
                  <button
                    className="nav-link nav-bar-icons"
                    onClick={handleApplicationClick}
                    style={{
                      color: "black",
                      backgroundColor: `${
                        isApplication! ? "rgb(133, 133, 133)" : "none"
                      }`,
                    }}
                  >
                    <h5>Applications</h5>
                  </button>
                </li>
                {role === "manager" && (
                  <li className="nav-item">
                    <button
                      className="nav-link nav-bar-icons"
                      onClick={handleStaffClick}
                      style={{
                        color: "black",
                        backgroundColor: `${
                          isStaffInfo! ? "rgb(133, 133, 133)" : "none"
                        }`,
                      }}
                    >
                      <h5>Staff Info</h5>
                    </button>
                  </li>
                )}

                {role !== "adopter" && (
                  <li className="nav-item">
                    <button
                      className="nav-link nav-bar-icons"
                      onClick={handleShelterClick}
                      style={{
                        color: "black",
                        backgroundColor: `${
                          isShelterInfo! ? "rgb(133, 133, 133)" : "none"
                        }`,
                      }}
                    >
                      <h5>Shelter Info</h5>
                    </button>
                  </li>
                )}
              </ul>
            </div>
          </div>
        </nav>
        <AddAdminPopup
          show={showAddAdminPopup}
          handleClose={handleCloseAddAdminPopup}
          token={token}
          shelterName={shelterName}
        />
      </div>
    </>
  );
};

export default Navbar;
