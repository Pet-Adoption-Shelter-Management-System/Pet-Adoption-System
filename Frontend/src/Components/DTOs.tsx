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

interface AuthResponse {
  token: string;
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

interface DocumentDto {
  docName: string;
  type: string;
  encodedFile: ArrayBuffer;
}

interface ShelterDto {
  id: number;
  name: string;
  location: string;
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
  available: boolean;
  shelter: ShelterDto;
  petVaccinations: string[];
  docs: DocumentDto[];
}

interface ApplicationDto {
  id: number;
  adopterID: number;
  petDto: PetDto;
  date: string; 
  status: string;
}

interface ApplicationRequestDto {
  petID: number;
  shelterID: number;
}


// interface Pet {
//   petDto:PetDto;
//   imageLink:string;
// }
