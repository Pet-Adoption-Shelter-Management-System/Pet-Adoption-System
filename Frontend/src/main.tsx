import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App.tsx";
import "./index.css";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import Login from "./Pages/Login.tsx";
import SignUp from "./Pages/SignUp.tsx";
import StaffSignUP from "./Pages/StaffSignUP.tsx";
import CreateShelter from "./Pages/CreateShelter.tsx";
import Verification from "./Pages/Verification.tsx";
import PetsPage from "./Pages/PetsPage.tsx";

import PetDetails from "./Pages/PetDetails.tsx";
import EmployeeVerification from "./Pages/EmployeeVerification.tsx";
import ShelterInfo from "./Pages/ShelterInfo.tsx";
import StaffInfo from "./Pages/StaffInfo.tsx";
import ProfilePage from "./Pages/ProfilePage.tsx";
import AdopterApplications from "./Pages/AdopterApplications.tsx";
import Dashboard from "./Pages/Dashboard.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login",
    element: <Login/>
  },
  {
    path: "/petDetails",
    element: <PetDetails/>
  },
  {
    path: "/signup",
    element: <SignUp />
  },
  {
    path: "/staffSignup",
    element: <StaffSignUP />
  },
  {
    path: "/createShelter",
    element:<CreateShelter/>
  },
  {
    path: "/applications",
    element:<AdopterApplications/>
  },
  {
    path: "/dashboard",
    element:<Dashboard/>
  },
  {
    path: "/verification",
    element: <Verification />,
  },
  {
    path: "/PetsPage",
    element: <PetsPage />,
  },
  {
    path: "/verificationSignup",
    element: <EmployeeVerification />,
  },
  {
    path: "/shelterInfo",
    element: <ShelterInfo />,
  },
  {
    path: "/staffInfo",
    element: <StaffInfo />,
  },
  {
    path: "/profile",
    element: <ProfilePage />,
  },
  {
    path: "*",
    element: <h1>404 Not found</h1>,
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
