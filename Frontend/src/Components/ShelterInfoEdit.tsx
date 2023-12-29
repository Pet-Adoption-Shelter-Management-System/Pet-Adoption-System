import React, { useState } from "react";
import { Button, Modal, Form } from "react-bootstrap";
import { Shelter } from "./ShelterInfoDisplay";

interface ShelterInfoEditProps {
  show: boolean;
  handleClose: () => void;
  shelter: Shelter;
  onSubmit: (updatedShelter: Shelter) => void;
}

const ShelterInfoEdit: React.FC<ShelterInfoEditProps> = ({
  show,
  handleClose,
  shelter,
  onSubmit,
}) => {
  const [updatedShelter, setUpdatedShelter] = useState<Shelter>({
    name: shelter.name,
    location: shelter.location,
    contactPhone: shelter.contactPhone,
    contactEmail: shelter.contactEmail,
  });

  const [validationErrors, setValidationErrors] = useState<Record<string, string>>(
    {}
  );

  const validateForm = (): boolean => {
    const errors: Record<string, string> = {};

    if ((updatedShelter.location?.length ?? 0) < 4) {
      errors["location"] = "Location must be at least 4 characters";
    }

    if (!/^\d+$/.test(updatedShelter.contactPhone || "")) {
      errors["contactPhone"] = "Contact Phone must contain only digits";
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(updatedShelter.contactEmail || "")) {
      errors["contactEmail"] = "Invalid Contact Email format";
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setUpdatedShelter((prev) => ({ ...prev, [name]: value }));
    setValidationErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const handleSubmit = () => {
    if (validateForm()) {
      onSubmit(updatedShelter);
      handleClose();
    }
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Shelter Information</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formLocation">
            <Form.Label>Location</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter shelter location"
              name="location"
              value={updatedShelter.location}
              onChange={handleChange}
              isInvalid={!!validationErrors["location"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["location"]}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formContactPhone">
            <Form.Label>Contact Phone</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter contact phone"
              name="contactPhone"
              value={updatedShelter.contactPhone}
              onChange={handleChange}
              isInvalid={!!validationErrors["contactPhone"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["contactPhone"]}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formContactEmail">
            <Form.Label>Contact Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter contact email"
              name="contactEmail"
              value={updatedShelter.contactEmail}
              onChange={handleChange}
              isInvalid={!!validationErrors["contactEmail"]}
            />
            <Form.Control.Feedback type="invalid">
              {validationErrors["contactEmail"]}
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

export default ShelterInfoEdit;
