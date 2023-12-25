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
    path: "*",
    element: <h1>404 Not found</h1>,
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
