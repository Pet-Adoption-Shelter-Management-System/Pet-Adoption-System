package com.example.Backend;

import com.example.Backend.Model.Pet;
import com.example.Backend.Model.Shelter;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataInsertion {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/petadoptiondb";
        String user = "root";
        String password = "rootpass6789?#";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false);

            // Insert Shelters
            List<Shelter> shelters = generateShelterData();
            insertShelters(connection, shelters);

            // Insert Pets
            List<Pet> pets = generatePetData(shelters);
            insertPets(connection, pets);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Shelter> generateShelterData() {
        List<Shelter> shelters = new ArrayList<>();
        Faker faker = new Faker();

        long shelterId = 1; // Start with ID 1
        for (int i = 0; i < 5; i++) { // Change 10 to the desired number of shelters
            Shelter shelter = Shelter.builder()
                    .id(shelterId++)
                    .name(faker.company().name())
                    .location(faker.address().city())
                    .contactPhone("01204191992")
                    .contactEmail("mahmoud@gmail.com")
                    .build();

            shelters.add(shelter);
        }

        return shelters;
    }

    private static void insertShelters(Connection connection, List<Shelter> shelters) throws SQLException {
//        String insertQuery = "INSERT INTO shelter (name, location, contactPhone, contactEmail) VALUES (?, ?, ?, ?)";
        String insertQuery = "INSERT INTO `petadoptiondb`.`shelter` (`id`, `name`, `location`, `contact phone`, `contact email`) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Shelter shelter : shelters) {
                preparedStatement.setLong(1, shelter.getId());
                preparedStatement.setString(2, shelter.getName());
                preparedStatement.setString(3, shelter.getLocation());
                preparedStatement.setString(4, shelter.getContactPhone());
                preparedStatement.setString(5, shelter.getContactEmail());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private static List<Pet> generatePetData(List<Shelter> shelters) {
        List<Pet> pets = new ArrayList<>();
        Faker faker = new Faker();

        for (Shelter shelter : shelters) {
            for (int i = 0; i < 1000; i++) { // Change 10 to the desired number of pets per shelter
                Pet pet = Pet.builder()
                        .name(faker.name().firstName())
                        .isMale(faker.bool().bool())
                        .isHouseTrained(faker.bool().bool())
                        .description(faker.lorem().sentence())
                        .behaviour("good")
                        .age(1.2F)
                        .breed(String.valueOf(faker.animal()))
                        .healthStatus("good")
                        .isAvailable(true)
                        .isSpayed(faker.bool().bool())
                        .species(String.valueOf(faker.animal()))
                        .shelter(shelter)
                        .build();

                pets.add(pet);
            }
        }

        return pets;
    }

    private static void insertPets(Connection connection, List<Pet> pets) throws SQLException {
        /*
        INSERT INTO `petadoptiondb`.`pet` (`age`, `behaviour`, `breed`, `description`, `health status`, `is_available`, `is_house_trained`, `is_male`, `is_spayed`, `name`, `species`, `shelterid`) VALUES (1.2, 'good', 'meai', 'good', 'good', 1,1, 0,1, 'monica', 'cat', 22);
         */
        String insertQuery = "INSERT INTO `petadoptiondb`.`pet` (`id`,`age`, `behaviour`, `breed`, `description`, `health status`, `is_available`, `is_house_trained`, `is_male`, `is_spayed`, `name`, `species`, `shelterid`) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?,?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Pet pet : pets) {

                preparedStatement.setLong(1, pet.getId());
                preparedStatement.setFloat(2, pet.getAge());
                preparedStatement.setString(3, pet.getBehaviour());
                preparedStatement.setString(4, pet.getBreed());
                preparedStatement.setString(5, pet.getDescription());
                preparedStatement.setString(6, pet.getHealthStatus());
                preparedStatement.setBoolean(7, pet.isAvailable());
                preparedStatement.setBoolean(8, pet.isHouseTrained());
                preparedStatement.setBoolean(9, pet.isMale());
                preparedStatement.setBoolean(10, pet.isSpayed());
                preparedStatement.setString(11, pet.getName());
                preparedStatement.setString(12, pet.getSpecies());
                preparedStatement.setLong(13, pet.getShelter().getId());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }
}
