// import React, { useEffect, useRef, useState } from "react";
// import { useNavigate } from "react-router-dom";
// import "./ApplicationsList.css"
// interface ApplicationsListProps {
//   getApplications: () => Promise<ApplicationDto[]>;
//   updateApplicationStatus: (
//     applicationID: number,
//     newStatus: string
//   ) => Promise<string>;
//   role: string;
//   firstName: string;
//   lastName: string;
//   shelterName: string;
//   userToken: string;
// }

// const formatDate = (dateString: string): string => {
//   const options: Intl.DateTimeFormatOptions = {
//     year: "numeric",
//     month: "long",
//     day: "numeric",
//   };
//   return new Date(dateString).toLocaleDateString(undefined, options);
// };

// const ApplicationsList = ({
//   getApplications,
//   updateApplicationStatus,
//   role,
//   firstName,
//   lastName,
//   shelterName,
//   userToken,
// }: ApplicationsListProps) => {
//   const nullDocument: DocumentDto = {
//     docName: "",
//     type: "",
//     encodedFile: new ArrayBuffer(0),
//   };

//   const nullShelter: ShelterDto = {
//     id: 0,
//     name: "",
//     location: "",
//     contactPhone: "",
//     contactEmail: "",
//   };

//   const nullPet: PetDto = {
//     id: 0,
//     name: "",
//     male: false,
//     houseTrained: false,
//     description: "",
//     healthStatus: "",
//     age: 0,
//     behaviour: "",
//     breed: "",
//     species: "",
//     spayed: false,
//     available: false,
//     shelter: nullShelter,
//     petVaccinations: [],
//     docs: [],
//   };

//   const nullApplicationDto: ApplicationDto = {
//     id: 0,
//     adopterID: 0,
//     petDto: nullPet,
//     date: "",
//     status: "",
//   };

//   const [applications, setApplications] = useState<ApplicationDto[]>([]);
//   const [currentPage, setCurrentPage] = useState(1);
//   const [applicationsPerPage] = useState(9);
//   const [showModal, setShowModal] = useState(false);
//   const [selectedApplication, setSelectedApplication] =
//     useState<ApplicationDto>(nullApplicationDto);
//   const [fadeAnimation, setFadeAnimation] = useState("");
//   const [fadeAnimationSingle, setFadeAnimationSingle] = useState("");
//   const [showConfirmModal, setShowConfirmModal] = useState(false);
//   const [confirmModalContent, setConfirmModalContent] = useState({
//     message: "",
//     onConfirm: () => {},
//   });
//   const [showAlertModal, setShowAlertModal] = useState(false);
//   const [alertModalContent, setAlertModalContent] = useState({
//     message: "",
//     onConfirm: () => {},
//   });

//   const navigate = useNavigate();

//   const handleApplicationClick = (application: ApplicationDto) => {
//     setSelectedApplication(application);
//     setShowModal(true);
//   };

//   const handleClose = () => {
//     setShowModal(false);
//   };

//   const handleStatusChange = (
//     application: ApplicationDto,
//     newStatus: string
//   ) => {
//     updateApplicationStatus(application.id, newStatus).then((response) => {
//       if (response.includes("Status updated")) {
//         selectedApplication.status = newStatus;
//         setAlertModalContent({
//           message: "Status updated successfully",
//           onConfirm: () => {
//             handleClose();
//             setTimeout(() => {
//               handleApplicationClick(selectedApplication);
//             }, 0);
//           },
//         });
//       } else {
//         setAlertModalContent({
//           message: response,
//           onConfirm: () => {
//             handleClose();
//             setTimeout(() => {
//               handleApplicationClick(selectedApplication);
//             }, 0);
//           },
//         });
//       }
//     });
//     setShowAlertModal(true);
//   };

//   const handlePetClicked = (pet: PetDto) => {
//     const id = pet.id;
//     navigate("/petDetails", {
//       state: {
//         firstName: firstName,
//         lastName: lastName,
//         role: role,
//         petID: id,
//         token: userToken,
//         shelterName: shelterName,
//       },
//     });
//   };

//   const handFetchedApplications = useRef(false);
//   useEffect(() => {
//     if (handFetchedApplications.current) {
//       return;
//     }
//     handFetchedApplications.current = true;
//     const fetchApplications = async () => {
//       getApplications().then((applications) => setApplications(applications));
//     };
//     fetchApplications();
//   }, []);

//   // Get current orders
//   const indexOfLastApplication = currentPage * applicationsPerPage;
//   const indexOfFirstApplication = indexOfLastApplication - applicationsPerPage;
//   const currentApplications = applications.slice(indexOfFirstApplication, indexOfLastApplication);

//   // Change page
//   const paginate = (pageNumber: number) => setCurrentPage(pageNumber);
//   const handlePaginationClick = (pageNumber: number) => {
//     setFadeAnimation("fade-out");
//     setTimeout(() => {
//       paginate(pageNumber);
//       setFadeAnimation("fade-in");
//     }, 300);
//   };


//   return (
//     <div>
//       {role !== "adopter" && 
//         <h1 className='mt-3 mb-0'>Applications</h1>
//       }
//       {role === "adopter" && 
//         <h1 className='mt-3 mb-0'>My Applications</h1>
//       }
//       {applications.length === 0 && role === "adopter" &&
//         <h1 className="semi-faded-text mb-0 d-flex justify-content-center align-items-center">
//           Refer to the pets page to see available-for-adoption pets
//         </h1>
//       }
//       {applications.length === 0 && role !== "adopter" &&
//         <h1 className="semi-faded-text mb-0 d-flex justify-content-center align-items-center">
//           No applications have been made yet to your shelter
//         </h1>
//       }
//       <div className={`orders-list ${fadeAnimation}`}>
//         {currentApplications.map(application => (
//           <div key={application.id} className='order-card' onClick={() => handleApplicationClick(application)}>
//             {role !== "adopter" && 
//               <>
//                 <p><strong>Application ID:</strong> {application.id}</p>
//                 <p><strong>Customer ID:</strong> {application.adopterID}</p>
//               </>
//             }
//             <p><strong>Pet Name:</strong> ${order.totalCost}</p>
//             <p><strong>Status:</strong> {order.status}</p>
//             <p><strong>Ordered at:</strong> {formatDate(order.startDate)}</p>
//             {isAdmin && 
//               <button className="order-delete-button" onClick={(e) => { e.stopPropagation(); handleDeleteOrder(order.id); }}>
//                 <FontAwesomeIcon icon={faTrash} />
//               </button>
//             }
//           </div>
//         ))}
//       </div>
//       <footer>
//         <Pagination
//           itemsPerPage={ordersPerPage}
//           totalItems={orders.length}
//           paginate={handlePaginationClick}
//           currentPage={currentPage}
//         />
//       </footer>
//       <Modal show={showModal} onHide={handleClose}>
//         <Modal.Header closeButton>
//           <Modal.Title>Order Details</Modal.Title>
//         </Modal.Header>
//         <Modal.Body>
//           {isAdmin &&
//             <>
//               <p><strong>Customer's Name:</strong> {selectedOrder.customer.firstName} {selectedOrder.customer.lastName}</p>
//               <p><strong>Customer's ID:</strong> {selectedOrder.customer.id}</p>
//               <p>Modify the status or remove products from this order.</p>
//             </>
//           }
//           <div className={`products-container ${fadeAnimationSingle}`}>
//             <table className="order-items-table">
//               <thead>
//                 <tr>
//                   {isAdmin &&
//                     <th>ID</th>
//                   }
//                   <th>Name</th>
//                   <th>Cost</th>
//                   <th>Quantity</th>
//                 </tr>
//               </thead>
//               <tbody>
//                 {selectedOrder?.orderItems.map(orderItem => (
//                   <tr key={orderItem.product.id} onClick={() => handleItemClick(orderItem.product)}>
//                     {isAdmin && 
//                       <td>{orderItem.product.id}</td>
//                     }
//                     <td>{orderItem.product.productName}</td>
//                     <td>{orderItem.originalCost} $</td>
//                     <td>{orderItem.quantity}</td>
//                     {isAdmin && 
//                       <td>
//                         <Button className='remove-btn' variant="danger" onClick={(e) => { e.stopPropagation(); handleRemoveProduct(selectedOrder, orderItem.product)}}>
//                           Remove Product
//                         </Button>
//                       </td>
//                     }
//                   </tr>
//                 ))}
//               </tbody>
//             </table>
//           </div>
//           <div className='status-btn-container'>
//             <p>Status: <span className={`status ${selectedOrder?.status?.toLowerCase()}`}>{selectedOrder?.status}</span></p>
//             {isAdmin && 
//               <>
//                 <Button className='status-btn' onClick={() => handleStatusChange(selectedOrder, 'Pending')}>Mark as Pending</Button>
//                 <Button className='status-btn' onClick={() => handleStatusChange(selectedOrder, 'Shipped')}>Mark as Shipped</Button>
//                 <Button className='status-btn' onClick={() => handleStatusChange(selectedOrder, 'Delivered')}>Mark as Delivered</Button>
//               </>
//             }
//           </div>
//         </Modal.Body>
//         <Modal.Footer>
//           <Button variant="secondary" onClick={handleClose}>
//             Close
//           </Button>
//         </Modal.Footer>
//       </Modal>
//       {showSortModal && (
//         <div className="sort-modal">
//           <div className="sort-modal-content">
//             <div className="sort-option">
//               <label className="sort-label">
//                 Sort by:
//                 <select className="sort-select" onChange={(e) => setSortParams(prev => ({ ...prev, sortBy: e.target.value }))} value={sortParams.sortBy}>
//                   <option value="">Select Criteria</option>
//                   <option value="id">Order ID</option>
//                   <option value="customer">Customer ID</option>
//                   <option value="startDate">Order Date</option>
//                   <option value="deliveryDate">Delivery Date</option>
//                   <option value="totalAmount">Number Of Ordered Items</option>
//                   <option value="totalCost">Total Cost</option>
//                   <option value="status">Status</option>
//                 </select>
//               </label>
//             </div>
//             <div className="sort-order">
//               <label className="sort-label">
//                 <input type="radio" name="sortOrder" checked={sortParams.sortOrder} onChange={() => setSortParams(prev => ({ ...prev, sortOrder: true }))} />
//                 Ascending
//               </label>
//               <label className="sort-label">
//                 <input type="radio" name="sortOrder" checked={!sortParams.sortOrder} onChange={() => setSortParams(prev => ({ ...prev, sortOrder: false }))} />
//                 Descending
//               </label>
//             </div>
//             <div className="sort-modal-buttons">
//               <button className="apply-button" onClick={() => { handleSortButtonClick(); toggleSortModal(); }}>Apply</button>
//               <button className="cancel-button" onClick={toggleSortModal}>Cancel</button>
//             </div>
//           </div>
//         </div>      
//       )}
//       <ConfirmationModal
//         show={showConfirmModal}
//         message={confirmModalContent.message}
//         onConfirm={() => {
//           confirmModalContent.onConfirm();
//           setShowConfirmModal(false);
//         }}
//         onCancel={() => setShowConfirmModal(false)}
//       />
//       <AlertModal
//         show={showAlertModal}
//         message={alertModalContent.message}
//         onConfirm={() => {
//           alertModalContent.onConfirm();
//           setShowAlertModal(false);
//         }}
//       />
//     </div>
//   );
// };

// export default ApplicationsList;
