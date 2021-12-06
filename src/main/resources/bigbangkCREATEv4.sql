-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bigbangk
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bigbangk
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bigbangk` DEFAULT CHARACTER SET utf8 ;
USE `bigbangk` ;

-- -----------------------------------------------------
-- Table `bigbangk`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`address` (
  `postalcode` VARCHAR(10) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `number` INT NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`postalcode`, `number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`asset` (
  `code` VARCHAR(5) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`wallet` (
  `walletid` INT NOT NULL,
  `balance` DECIMAL(25,2) NOT NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`walletid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`bank` (
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `walletid` INT NOT NULL,
  PRIMARY KEY (`code`),
  INDEX `fk_Bank_Wallet1_idx` (`walletid` ASC) VISIBLE,
  CONSTRAINT `fk_Bank_Wallet1`
    FOREIGN KEY (`walletid`)
    REFERENCES `bigbangk`.`wallet` (`walletid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`banksettings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`banksettings` (
  `code` VARCHAR(45) NOT NULL,
  `startingcapital` VARCHAR(45) NOT NULL,
  `transactioncosts` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`code`),
  CONSTRAINT `fk_table1_Bank1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`bank` (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`order` (
  `orderid` INT NOT NULL,
  `coinamount` DECIMAL(40,30) NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  `placementtime` TIMESTAMP NOT NULL,
  `ordertype` VARCHAR(45) NOT NULL,
  `requistedprice` VARCHAR(45) NOT NULL,
  `walletid` INT NOT NULL,
  PRIMARY KEY (`orderid`),
  INDEX `fk_Orders_Coin1_idx` (`code` ASC) VISIBLE,
  INDEX `fk_Order_Wallet1_idx` (`walletid` ASC) VISIBLE,
  CONSTRAINT `fk_Order_Wallet1`
    FOREIGN KEY (`walletid`)
    REFERENCES `bigbangk`.`wallet` (`walletid`),
  CONSTRAINT `fk_Orders_Coin1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`asset` (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`pendingorder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`pendingorder` (
  `walletid` INT NOT NULL,
  `lowerbound` VARCHAR(45) NOT NULL,
  `pendingorderid` VARCHAR(45) NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  `coinamount` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pendingorderid`),
  INDEX `fk_Trigger_Wallet1_idx` (`walletid` ASC) VISIBLE,
  INDEX `fk_Triggeredparcel_Asset1_idx` (`code` ASC) VISIBLE,
  CONSTRAINT `fk_Trigger_Wallet1`
    FOREIGN KEY (`walletid`)
    REFERENCES `bigbangk`.`wallet` (`walletid`),
  CONSTRAINT `fk_Triggeredparcel_Asset1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`asset` (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`pricehistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`pricehistory` (
  `datetime` TIMESTAMP NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  `price` DECIMAL(20,20) NOT NULL,
  PRIMARY KEY (`datetime`, `code`),
  INDEX `fk_Price_Coin1_idx` (`code` ASC) VISIBLE,
  CONSTRAINT `fk_Price_Coin1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`asset` (`code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`transaction` (
  `transactionid` INT NOT NULL,
  `coinamount` DECIMAL(40,30) NOT NULL,
  `transactionvalue` DECIMAL(40,10) NOT NULL,
  `transactiontime` TIMESTAMP NOT NULL,
  `seller` INT NOT NULL,
  `buyer` INT NOT NULL,
  `code` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`transactionid`, `seller`, `buyer`),
  INDEX `fk_Transaction_Wallet1_idx` (`seller` ASC) VISIBLE,
  INDEX `fk_Transaction_Wallet2_idx` (`buyer` ASC) VISIBLE,
  INDEX `fk_Transaction_Coin1_idx` (`code` ASC) VISIBLE,
  CONSTRAINT `fk_Transaction_Coin1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`asset` (`code`),
  CONSTRAINT `fk_Transaction_Wallet1`
    FOREIGN KEY (`seller`)
    REFERENCES `bigbangk`.`wallet` (`walletid`),
  CONSTRAINT `fk_Transaction_Wallet2`
    FOREIGN KEY (`buyer`)
    REFERENCES `bigbangk`.`wallet` (`walletid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`wallet_has_asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`wallet_has_asset` (
  `code` VARCHAR(5) NOT NULL,
  `walletId` INT NOT NULL,
  `amount` DECIMAL(40,30) NOT NULL,
  PRIMARY KEY (`code`, `walletId`),
  INDEX `fk_Coin_has_Wallet_Wallet1_idx` (`walletId` ASC) VISIBLE,
  INDEX `fk_Coin_has_Wallet_Coin1_idx` (`code` ASC) VISIBLE,
  CONSTRAINT `fk_Coin_has_Wallet_Coin1`
    FOREIGN KEY (`code`)
    REFERENCES `bigbangk`.`asset` (`code`),
  CONSTRAINT `fk_Coin_has_Wallet_Wallet1`
    FOREIGN KEY (`walletId`)
    REFERENCES `bigbangk`.`wallet` (`walletid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bigbangk`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bigbangk`.`client` (
  `email` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `insertion` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `dateofbirth` DATE NOT NULL,
  `BSN` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `walletid` INT NOT NULL,
  `postalcode` VARCHAR(10) NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`email`),
  INDEX `verzinzelf2_idx` (`walletid` ASC) VISIBLE,
  INDEX `verzinzelf1_idx` (`postalcode` ASC, `number` ASC) VISIBLE,
  CONSTRAINT `verzinzelf2`
    FOREIGN KEY (`walletid`)
    REFERENCES `bigbangk`.`wallet` (`walletid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`postalcode` , `number`)
    REFERENCES `bigbangk`.`address` (`postalcode` , `number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


CREATE USER 'userBigbangk'@'localhost' IDENTIFIED BY 'userBigbangkPW';
GRANT ALL PRIVILEGES ON bigbangk . * TO 'userBigbangk' @'localhost';
