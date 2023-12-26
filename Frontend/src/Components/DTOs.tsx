// The OTD for the the authentication resonse
interface AuthenticationResponse {
  token: string;
  // Can be [{token: "SUCCESS tok"}, {token: "Already Exist"}, {token: errorMessage}]
}

// An OTD for the register r
interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

// The DTO for loging in
interface LoginRequest {
  email: string;
  password: string;
  role: string; //manager ,,, staff,,, adopter
  shelterName: string;
}


// the DTO for creating a shelter request
interface CreateShelterRequest {
  email: string;
  password: string;
  shelterName: string;
  address: string;
  contactPhone: string;
  contactEmail: string;
}

interface PetDto {
  id: number;
  name: string;
  male: boolean;
  houseTrained: boolean;
  description: string;
  healthStatus: string;
  age: number;
  behaviour: string;
  breed: string;
  species: string;
  spayed: boolean;
  shelterName: string;
  petVaccinations: string[];
}



