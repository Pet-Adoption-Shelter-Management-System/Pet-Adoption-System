import React, { useEffect, useState } from 'react';
import axios from 'axios';



const PetComponent: React.FC = () => {
  const [pet, setPet] = useState<PetDto | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<PetDto>('http://localhost:8080/api/getPet'); // Replace with your backend URL
        const data = response.data;

        // Map the received data to the PetDto interface
        setPet({
          id: data.id,
          name: data.name,
          male: data.male,
          houseTrained: data.houseTrained,
          description: data.description,
          healthStatus: data.healthStatus,
          age: data.age,
          behaviour: data.behaviour,
          breed: data.breed,
          species: data.species,
          spayed: data.spayed,
          shelterName: data.shelterName,
          petVaccinations: data.petVaccinations,
          docs: data.docs,
        });
      } catch (error) {
        console.error('Error fetching pet data:', error);
      }
    };

    fetchData();
  }, []);

//   const base64ToArrayBuffer = (base64: string): ArrayBuffer => {
//     const binaryString = window.atob(base64);
//     const len = binaryString.length;
//     const bytes = new Uint8Array(len);

//     for (let i = 0; i < len; i++) {
//       bytes[i] = binaryString.charCodeAt(i);
//     }

//     return bytes.buffer;
//   };

  return (
    <div>
      {pet && (
        <div>
          <h1>{pet.name}</h1>
          <p>{pet.description}</p>
          {/* Display other pet information */}
          {pet.docs.map((doc, index) => (
            <div key={index}>
              <p>{doc.docName}</p>
              <p>{doc.type}</p>
              <img src={`data:image/jpeg;base64,${doc.encodedFile}`} alt="Document" />

              {/* Display other document information */}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default PetComponent;
