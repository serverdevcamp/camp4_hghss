-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hghssdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hghssdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hghssdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `hghssdb` ;

-- -----------------------------------------------------
-- Table `hghssdb`.`nickname`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`nickname` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nickname_id` INT(11) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `hashed_password` VARCHAR(255) NOT NULL,
  `salt` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `nickname_id` (`nickname_id` ASC) VISIBLE,
  CONSTRAINT `user_ibfk_1`
    FOREIGN KEY (`nickname_id`)
    REFERENCES `hghssdb`.`nickname` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `logo_url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`recruit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`recruit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `company_id` INT(11) NOT NULL,
  `jobtitle` VARCHAR(255) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `content` TEXT NOT NULL,
  `view_count` INT NOT NULL,
  `homepage_count` INT NOT NULL,
  `employment_page_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `company_id` (`company_id` ASC) VISIBLE,
  CONSTRAINT `recruit_ibfk_1`
    FOREIGN KEY (`company_id`)
    REFERENCES `hghssdb`.`company` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`position` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `recruit_id` INT(11) NOT NULL,
  `field` VARCHAR(255) NOT NULL,
  `division` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `recruit_id` (`recruit_id` ASC) VISIBLE,
  CONSTRAINT `position_ibfk_1`
    FOREIGN KEY (`recruit_id`)
    REFERENCES `hghssdb`.`recruit` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`resume`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`resume` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `position_id` INT(11) NOT NULL,
  `last_mod_date` DATETIME NOT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `index` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `position_id` (`position_id` ASC) VISIBLE,
  CONSTRAINT `resume_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `hghssdb`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `resume_ibfk_2`
    FOREIGN KEY (`position_id`)
    REFERENCES `hghssdb`.`position` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`answer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `resume_id` INT(11) NOT NULL,
  `order` INT(11) NOT NULL,
  `question_content` TEXT NULL DEFAULT NULL,
  `answer_content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `resume_id` (`resume_id` ASC) VISIBLE,
  CONSTRAINT `answer_ibfk_1`
    FOREIGN KEY (`resume_id`)
    REFERENCES `hghssdb`.`resume` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`chatting_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`chatting_like` (
  `user_id` INT(11) NOT NULL,
  `company_id` INT(11) NOT NULL,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `company_id` (`company_id` ASC) VISIBLE,
  CONSTRAINT `chatting_like_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `hghssdb`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `chatting_like_ibfk_2`
    FOREIGN KEY (`company_id`)
    REFERENCES `hghssdb`.`company` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `position_id` INT(11) NOT NULL,
  `question_content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `position_id` (`position_id` ASC) VISIBLE,
  CONSTRAINT `question_ibfk_1`
    FOREIGN KEY (`position_id`)
    REFERENCES `hghssdb`.`position` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hghssdb`.`recruit_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hghssdb`.`recruit_like` (
  `user_id` INT(11) NOT NULL,
  `recruit_id` INT(11) NOT NULL,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `recruit_id` (`recruit_id` ASC) VISIBLE,
  CONSTRAINT `recruit_like_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `hghssdb`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `recruit_like_ibfk_2`
    FOREIGN KEY (`recruit_id`)
    REFERENCES `hghssdb`.`recruit` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
