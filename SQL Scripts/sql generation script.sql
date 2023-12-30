-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema petadoptiondb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema petadoptiondb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `petadoptiondb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `petadoptiondb` ;

-- -----------------------------------------------------
-- Table `petadoptiondb`.`adopter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`adopter` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `contact phone` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `first name` VARCHAR(255) NULL DEFAULT NULL,
  `is_verified` TINYINT(1) NOT NULL DEFAULT '0',
  `last name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`shelter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`shelter` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contact email` VARCHAR(255) NOT NULL,
  `contact phone` VARCHAR(255) NOT NULL,
  `location` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`pet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`pet` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `age` FLOAT NOT NULL,
  `behaviour` VARCHAR(255) NOT NULL,
  `breed` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `health status` VARCHAR(255) NOT NULL,
  `is_available` TINYINT(1) NOT NULL DEFAULT '1',
  `is_house_trained` BIT(1) NOT NULL,
  `is_male` BIT(1) NOT NULL,
  `is_spayed` TINYINT(1) NOT NULL DEFAULT '0',
  `name` VARCHAR(255) NOT NULL,
  `species` VARCHAR(255) NOT NULL,
  `shelterid` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKokwlx2aq3pcasajek741gsn4l` (`shelterid` ASC) VISIBLE,
  CONSTRAINT `FKokwlx2aq3pcasajek741gsn4l`
    FOREIGN KEY (`shelterid`)
    REFERENCES `petadoptiondb`.`shelter` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 20001
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`application` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date` DATETIME(6) NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  `adopterid` BIGINT NOT NULL,
  `petid` BIGINT NOT NULL,
  `shelterid` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_qangtm8n9dnnorl76bpl87reb` (`petid` ASC) VISIBLE,
  INDEX `FK9fh3plria0lx8ttxbrui9lo2b` (`adopterid` ASC) VISIBLE,
  INDEX `FK1egakuw6kjnppb9cm8b2otuan` (`shelterid` ASC) VISIBLE,
  CONSTRAINT `FK1egakuw6kjnppb9cm8b2otuan`
    FOREIGN KEY (`shelterid`)
    REFERENCES `petadoptiondb`.`shelter` (`id`),
  CONSTRAINT `FK9fh3plria0lx8ttxbrui9lo2b`
    FOREIGN KEY (`adopterid`)
    REFERENCES `petadoptiondb`.`adopter` (`id`),
  CONSTRAINT `FKcf59gdm3lhqlkrcqe2l2f6ofg`
    FOREIGN KEY (`petid`)
    REFERENCES `petadoptiondb`.`pet` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`document` (
  `link` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `petid` BIGINT NOT NULL,
  PRIMARY KEY (`link`, `petid`),
  INDEX `FKk3xcjh26iftmd19qieh5ureyy` (`petid` ASC) VISIBLE,
  CONSTRAINT `FKk3xcjh26iftmd19qieh5ureyy`
    FOREIGN KEY (`petid`)
    REFERENCES `petadoptiondb`.`pet` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`employee` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `contact phone` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `first name` VARCHAR(255) NULL DEFAULT NULL,
  `is_verified` TINYINT(1) NOT NULL DEFAULT '0',
  `last name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `is_manager` TINYINT(1) NOT NULL DEFAULT '0',
  `shelterid` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE,
  INDEX `FKpkybuiegxe4hgvxtsnbn5c9mj` (`shelterid` ASC) VISIBLE,
  CONSTRAINT `FKpkybuiegxe4hgvxtsnbn5c9mj`
    FOREIGN KEY (`shelterid`)
    REFERENCES `petadoptiondb`.`shelter` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `petadoptiondb`.`petvaccinations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `petadoptiondb`.`petvaccinations` (
  `vaccination` VARCHAR(255) NOT NULL,
  `petid` BIGINT NOT NULL,
  PRIMARY KEY (`petid`, `vaccination`),
  CONSTRAINT `FK1g30tnlpek8djmjfl23s2e27u`
    FOREIGN KEY (`petid`)
    REFERENCES `petadoptiondb`.`pet` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
