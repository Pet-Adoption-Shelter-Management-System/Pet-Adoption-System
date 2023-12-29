import React, { useState } from "react";
import { Button, Modal, Form } from "react-bootstrap";
import { Shelter } from "./ShelterInfoDisplay";
import { Profile } from "./ProdileInfoDisplay";

interface ShelterInfoEditProps {
  show: boolean;
  handleClose: () => void;
  profile: Profile;
  onSubmit: (updatedProfile: Profile) => void;
}

const ProfileInfoEdit: React.FC<ShelterInfoEditProps> = ({
  show,
  handleClose,
  profile,
  onSubmit,
}) => {
  const [updatedProfile, setUpdatedProfile] = useState<Profile>({
    email: profile.email,
    address: profile.address,
    contactPhone: profile.contactPhone,
    firstName: profile.firstName,
    lastName: profile.lastName,
  });

  const [validationErrors, setValidationErrors] = useState<
    Record<string, string>
  >({});

  const validateForm = (): boolean => {
    const errors: Record<string, string> = {};

    if ((updatedProfile.address?.length ?? 0) < 4) {
      errors["address"] = "address must be at least 4 characters";
    }

    if ((updatedProfile.firstName?.length ?? 0) < 3) {
      errors["firstName"] = "First name must be at least 3 characters";
    }

    if ((updatedProfile.lastName?.length ?? 0) < 3) {
      errors["lastName"] = "Last name must be at least 3 characters";
    }

    if (!/^\d+$/.test(updatedProfile.contactPhone || "")) {
      errors["contactPhone"] = "Contact Phone must contain only digits";
    }

    // if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(updatedProfile.email || "")) {
    //   errors["email"] = "Invalid email format";
    // }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUpdatedProfile((prev) => ({ ...prev, [name]: value }));
    setValidationErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleSubmit = () => {
    if (validateForm()) {
      onSubmit(updatedProfile);
      handleClose();
    }
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Profile Information</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formFirstName">
            <Form.Label>First Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your first name"
              name="firstName"
              value={updatedProfile.firstName}
              onChange={handleChange}
              isInvalid={!!validationErrors["firstName"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["firstName"]}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="formLastName">
            <Form.Label>Last Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your last name"
              name="lastName"
              value={updatedProfile.lastName}
              onChange={handleChange}
              isInvalid={!!validationErrors["lastName"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["lastName"]}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="formAddress">
            <Form.Label>Address</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your address"
              name="address"
              value={updatedProfile.address}
              onChange={handleChange}
              isInvalid={!!validationErrors["address"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["address"]}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="formContactPhone">
            <Form.Label>Contact Phone</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter contact phone"
              name="contactPhone"
              value={updatedProfile.contactPhone}
              onChange={handleChange}
              isInvalid={!!validationErrors["contactPhone"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["contactPhone"]}
            </Form.Control.Feedback>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Cancel
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ProfileInfoEdit;
