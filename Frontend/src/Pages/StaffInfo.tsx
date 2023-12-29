import { useEffect, useRef, useState } from "react";
import { useLocation } from "react-router";
import Navbar from "../Components/Navbar";
import axios from "axios";

interface Employee {
  email: string;
  firstName: string;
  lastName: string;
  contactPhone?: string;
  address?: string;
  verified: boolean; //may cause problem
}

const StaffInfo = () => {
  const location = useLocation();
    var { shelterName, firstName, lastName, token, role } = location.state || {};
  const isMounted = useRef<boolean>(true);
  const [employees, setEmployees] = useState<Employee[]>([]);

  // const empsEx: Employee[] = [
  //   {
  //     email: "mahmoud@gmail.com",
  //     firstName: "mahmoud",
  //     lastName: "attia",
  //     contactPhone: "",
  //     address: "",
  //     isVerified: true,
  //   },
  //   {
  //     email: "mahmoud2@gmail.com",
  //     firstName: "mahmoud",
  //     lastName: "attia",
  //     contactPhone: "01204191992",
  //     address: "alex-st",
  //     isVerified: true,
  //   },
  // ];

  useEffect(() => {

    const fetchEmployees = async () => {
      console.log("sh name:", shelterName);
      console.log(token);
      let url = `http://localhost:9080/api/shelter/getEmployees/${shelterName}`;
      try {
        const response = await axios(url, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        console.log(response.data);
        setEmployees(response.data);
      } catch (error) {
        if (axios.isAxiosError(error)) {
          // This type assertion tells TypeScript that error is an AxiosError
          const axiosError = error as import("axios").AxiosError;
          if (axiosError.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            alert((axiosError.response.data));
            //   setResponseData(axiosError.response.data as string);
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
    if (isMounted.current) {
      isMounted.current = false;
      document.body.style.overflowX = "hidden";
      fetchEmployees();
      //   setShelter(shelterEx);
      //   console.log(shelterEx);
      // setEmployees(empsEx);
    }
  }, []);

  return (
    <>
      <Navbar
        shelterName={shelterName}
        firstName={firstName}
        lastName={lastName}
        role={role}
        token={token}
        isStaffInfo={true}

        // shelterName="Pet Shelter"
        // firstName="Mahmoud"
        // lastName="Attia"
        // role="manager"
        // token="1234"
        // isStaffInfo={true}
      />
      <div
        style={{ overflowX: "hidden", overflowY: "hidden", padding: "20px" }}
      >
        <div className="row mt-4">
          <div className="col">
            <div className="row mt-1 md-1" style={{ marginBottom: "10px" }}>
              <div className="col-md-4 col-lg-3">
                <h2>Staff Members</h2>
              </div>
            </div>

            <div className="table-container">
              <div className="table-responsive">
                <table
                  className="table table-fixed"
                  // style={{
                  //   borderRadius: "10px",
                  // //   background:
                  // //     "linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%)",
                  // }}
                >
                  <thead className="custom-table-header">
                    <tr
                      style={{
                        fontSize: "20px",
                        fontWeight: "bold",
                        borderRadius: "10px",
                      }}
                    >
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        Email
                      </td>
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        First Name
                      </td>
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        Last Name
                      </td>
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        Contact Phone
                      </td>
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        Address
                      </td>
                      <td
                        style={{
                          background: "#919191",
                        }}
                      >
                        Is Verified
                      </td>
                    </tr>
                  </thead>
                  <tbody
                    className="custom-table-body"
                    // style={{
                    //   borderRadius: "10px",
                    //   background:
                    //     "linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%)",
                    // }}
                  >
                    {employees.map((emp) => (
                      <tr key={emp.email} className="hover-bg-gray">
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.email}
                        </td>
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.firstName}
                        </td>
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.lastName}
                        </td>
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.contactPhone? emp.contactPhone: "not provided"}
                        </td>
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.address? emp.address: "not provided"}
                        </td>
                        <td
                          style={{
                            background: "#d9d9d9",
                          }}
                        >
                          {emp.verified ? "Yes" : "No"}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default StaffInfo;
