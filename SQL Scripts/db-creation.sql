Create database petadoptiondb;
use petadoptiondb;

CREATE TABLE `Shelter`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `location` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `contact phone` VARCHAR(255) NOT NULL,
    `contact email` VARCHAR(255) NOT NULL
);

CREATE TABLE `Employee`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` BIGINT NULL,
    `first name` VARCHAR(255) NULL,
    `last name` VARCHAR(255) NULL,
    `role` ENUM('') NOT NULL,
    `contact phone` BIGINT NOT NULL,
    `shelterID` BIGINT UNSIGNED NOT NULL
);

CREATE TABLE `Adopter`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL unique,
    `password` VARCHAR(255) NOT NULL,
    `first name` VARCHAR(255) NOT NULL,
    `last name` VARCHAR(255) NOT NULL,
    `contact phone` VARCHAR(255) NULL,
    `address` VARCHAR(255) NULL
);

CREATE TABLE `Pet`(
     `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `name` VARCHAR(255) NOT NULL,
     `isMale` TINYINT NOT NULL,
     `isHouseTrained` TINYINT NOT NULL,
     `description` VARCHAR(255) NOT NULL,
     `health status` VARCHAR(255) NOT NULL,
     `age` DOUBLE NOT NULL,
     `behaviour` VARCHAR(255) NOT NULL,
     `breed` VARCHAR(255) NOT NULL,
     `species` BIGINT NOT NULL,
     `shelterID` BIGINT UNSIGNED NOT NULL,
     `isSpayed` TINYINT NOT NULL
 );

CREATE TABLE `Manager`(
    `employeeID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `shelterID` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`employeeID`, `shelterID`)
);


CREATE TABLE `PetVaccinations`(
    `petID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `vaccination` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`petID`, `vaccination`)
);

CREATE TABLE `Document`(
    `petID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `link` VARCHAR(255) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
     PRIMARY KEY(`petID`, `link`)
);

CREATE TABLE `Application`(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `date` DATETIME NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `petID` BIGINT UNSIGNED NOT NULL,
    `adopterID` BIGINT UNSIGNED NOT NULL,
    `shelterID` BIGINT UNSIGNED NOT NULL
);

ALTER TABLE
    `Employee` ADD CONSTRAINT `employee_shelterid_foreign` FOREIGN KEY(`shelterID`) REFERENCES `Shelter`(`id`);
    
ALTER TABLE
    `Manager` ADD CONSTRAINT `manager_emp_id_foreign` FOREIGN KEY(`employeeID`) REFERENCES `Employee`(`id`); 
    
ALTER TABLE
    `Manager` ADD CONSTRAINT `manager_shelter_id_foreign` FOREIGN KEY(`shelterID`) REFERENCES `Shelter`(`id`);     

ALTER TABLE
    `Pet` ADD CONSTRAINT `pet_shelter_id_foreign` FOREIGN KEY(`shelterID`) REFERENCES `Shelter`(`id`); 
    
ALTER TABLE
    `Document` ADD CONSTRAINT `documet_pet_id_foreign` FOREIGN KEY(`petID`) REFERENCES `Pet`(`id`);  

ALTER TABLE
    `PetVaccinations` ADD CONSTRAINT `vaccination_pet_id_foreign` FOREIGN KEY(`petID`) REFERENCES `Pet`(`id`);

ALTER TABLE
    `Application` ADD CONSTRAINT `application_pet_id_foreign` FOREIGN KEY(`petID`) REFERENCES `Pet`(`id`);

ALTER TABLE
    `Application` ADD CONSTRAINT `application_shelter_id_foreign` FOREIGN KEY(`shelterID`) REFERENCES `Shelter`(`id`);

ALTER TABLE
    `Application` ADD CONSTRAINT `application_adopter_id_foreign` FOREIGN KEY(`adopterID`) REFERENCES `Adopter`(`id`);
    
 