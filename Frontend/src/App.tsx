import { Button } from "react-bootstrap";
import "./App.css";
import SignUp from "./Pages/SignUp";
import CreateShelter from "./Pages/CreateShelter";
import PetsPage from "./Pages/PetsPage";
import AddEditPet from "./Components/AddEditPet";
import { useNavigate } from "react-router-dom";

function App() {
  const resetButton = () => {};
  const navigate = useNavigate();

  const token: string =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGVsbWFobW91ZDE5OTU0MEBnbWFpbC5jb20iLCJpYXQiOjE3MDM4MDcwOTgsImV4cCI6MTcwMzg5MzQ5OH0.wU_OzsqqiORV_oY84STErTFOiAY4geOLHiH-gjvAaM0";
  return (
    <>
      {/* <SignUp /> */}
      {/* <AddEditPet isEdit={false} adminToken={token} resetButton={resetButton} /> */}
      {/* <PetsPage /> */}
      <SignUp />
      {/* <PetComponent/> */}
    </>
  );
}

export default App;
