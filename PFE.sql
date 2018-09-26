-- MySQL Script generated by MySQL Workbench
-- Wed Sep 26 17:52:35 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pfe_2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pfe_2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pfe_2` DEFAULT CHARACTER SET utf8 ;
USE `pfe_2` ;

-- -----------------------------------------------------
-- Table `pfe_2`.`adminstrateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`adminstrateur` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Nom` VARCHAR(45) NOT NULL,
  `Prenom` VARCHAR(45) NOT NULL,
  `Sexe` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Date_naissance` DATE NOT NULL,
  `Lieu_naissance` VARCHAR(45) NULL DEFAULT NULL,
  `Willaya` VARCHAR(45) NULL DEFAULT NULL,
  `Login` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`annee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`annee` (
  `Code` VARCHAR(45) NOT NULL,
  `Year` VARCHAR(10) NOT NULL,
  `Etat` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`options_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`options_table` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation_Option` VARCHAR(45) NOT NULL,
  `Niveau` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`promotion` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation` VARCHAR(45) NOT NULL,
  `Nombre_etudiants` INT(11) NOT NULL,
  `Cycle` VARCHAR(45) NOT NULL,
  `Niveau` VARCHAR(45) NOT NULL,
  `Annee_Code` VARCHAR(45) NOT NULL,
  `Option_Code` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `Annee_Code`, `Option_Code`),
  INDEX `fk_Promotion_Annee1_idx` (`Annee_Code` ASC),
  INDEX `fk_Promotion_Option1_idx` (`Option_Code` ASC),
  CONSTRAINT `fk_Promotion_Annee1`
    FOREIGN KEY (`Annee_Code`)
    REFERENCES `pfe_2`.`annee` (`Code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Promotion_Option1`
    FOREIGN KEY (`Option_Code`)
    REFERENCES `pfe_2`.`options_table` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`projet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`projet` (
  `Code` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation` LONGTEXT NOT NULL,
  `Specialite` VARCHAR(45) NOT NULL,
  `Resume` LONGTEXT NULL DEFAULT NULL,
  `Technologie` LONGTEXT NOT NULL,
  `Outil` LONGTEXT NOT NULL,
  `Prerequis` LONGTEXT NOT NULL,
  `Plan_travail` LONGTEXT NULL DEFAULT NULL,
  `Duree` VARCHAR(45) NULL DEFAULT NULL,
  `Nombre_equipe` INT(11) NULL DEFAULT NULL,
  `Promotion_Code` INT(11) NOT NULL,
  `Validation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Code`, `Promotion_Code`),
  INDEX `fk_Projet_Promotion1_idx` (`Promotion_Code` ASC),
  CONSTRAINT `fk_Projet_Promotion1`
    FOREIGN KEY (`Promotion_Code`)
    REFERENCES `pfe_2`.`promotion` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`equipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`equipe` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre_Etudiants` INT(11) NULL DEFAULT NULL,
  `Moyenne` FLOAT NULL DEFAULT NULL,
  `Projet_Code` INT(11) NULL DEFAULT NULL,
  `Promotion_Numero` INT(11) NOT NULL,
  `Projet` VARCHAR(45) NULL DEFAULT NULL,
  `Validation` VARCHAR(45) NOT NULL,
  `Designation` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`Numero`),
  INDEX `fk_Equipe_Projet1_idx` (`Projet_Code` ASC),
  INDEX `fk_Equipe_Promotion1_idx` (`Promotion_Numero` ASC),
  CONSTRAINT `fk_Equipe_Projet1`
    FOREIGN KEY (`Projet_Code`)
    REFERENCES `pfe_2`.`projet` (`Code`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipe_Promotion1`
    FOREIGN KEY (`Promotion_Numero`)
    REFERENCES `pfe_2`.`promotion` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`enseignant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`enseignant` (
  `Matricule` VARCHAR(45) NOT NULL,
  `Nom` VARCHAR(45) NOT NULL,
  `Prenom` VARCHAR(45) NOT NULL,
  `Sexe` VARCHAR(45) NOT NULL,
  `Date_Naissance` VARCHAR(45) NOT NULL,
  `Lieu_Naissance` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Adresse` VARCHAR(45) NOT NULL,
  `Willaya` VARCHAR(45) NOT NULL,
  `Specialite` VARCHAR(45) NOT NULL,
  `Grade` VARCHAR(45) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Etat` VARCHAR(45) NOT NULL,
  `Premiere` INT(11) NULL DEFAULT '1',
  PRIMARY KEY (`Matricule`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`seance_encadrement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`seance_encadrement` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Date` DATE NOT NULL,
  `equipe_Numero` INT(11) NOT NULL,
  `enseignant_Matricule` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Numero`, `equipe_Numero`, `enseignant_Matricule`),
  INDEX `fk_seance_encadrement_equipe1_idx` (`equipe_Numero` ASC),
  INDEX `fk_seance_encadrement_enseignant1_idx` (`enseignant_Matricule` ASC),
  CONSTRAINT `fk_seance_encadrement_equipe1`
    FOREIGN KEY (`equipe_Numero`)
    REFERENCES `pfe_2`.`equipe` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_seance_encadrement_enseignant1`
    FOREIGN KEY (`enseignant_Matricule`)
    REFERENCES `pfe_2`.`enseignant` (`Matricule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`appriciation_global`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`appriciation_global` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Note_Global` FLOAT NOT NULL,
  `Equipe_Numero` INT(11) NOT NULL,
  `Seance_encadrement_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`Numero`),
  INDEX `fk_Appriciation_Global_Seance_encadrement1_idx` (`Note_Global` ASC),
  INDEX `fk_global_seance_idx` (`Seance_encadrement_Numero` ASC),
  CONSTRAINT `fk_global_seance`
    FOREIGN KEY (`Seance_encadrement_Numero`)
    REFERENCES `pfe_2`.`seance_encadrement` (`Numero`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`section` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation` VARCHAR(45) NOT NULL,
  `Nombre_etudiants` INT(11) NOT NULL,
  `Promotion_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `Promotion_Numero`),
  INDEX `fk_Section_Promotion1_idx` (`Promotion_Numero` ASC),
  CONSTRAINT `fk_Section_Promotion1`
    FOREIGN KEY (`Promotion_Numero`)
    REFERENCES `pfe_2`.`promotion` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`groupe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`groupe` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation` VARCHAR(45) NOT NULL,
  `Nombre_Etudiants` INT(11) NOT NULL,
  `Section_Code` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `Section_Code`),
  INDEX `fk_Groupe_Section1_idx` (`Section_Code` ASC),
  CONSTRAINT `fk_Groupe_Section1`
    FOREIGN KEY (`Section_Code`)
    REFERENCES `pfe_2`.`section` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`etudiant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`etudiant` (
  `Matricule` VARCHAR(45) NOT NULL,
  `Nom` VARCHAR(45) NOT NULL,
  `Prenom` VARCHAR(45) NOT NULL,
  `Sexe` VARCHAR(45) NOT NULL,
  `Date_Naissance` DATE NOT NULL,
  `Lieu_Naissance` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Adresse` VARCHAR(45) NOT NULL,
  `Willaya` VARCHAR(45) NOT NULL,
  `Moyenne` FLOAT NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Groupe_Numero` INT(11) NOT NULL,
  `Equipe_Numero` INT(11) NULL DEFAULT NULL,
  `Etat` VARCHAR(45) NOT NULL,
  `Premiere` VARCHAR(45) NOT NULL DEFAULT '1',
  `Promotion_ID` INT(11) NOT NULL,
  `Qualite` VARCHAR(45) NULL DEFAULT NULL,
  `Note_Finale` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`Matricule`, `Groupe_Numero`),
  INDEX `fk_Etudiant_Groupe1_idx` (`Groupe_Numero` ASC),
  INDEX `fk_Etudiant_Equipe1_idx` (`Equipe_Numero` ASC),
  CONSTRAINT `fk_Etudiant_Equipe1`
    FOREIGN KEY (`Equipe_Numero`)
    REFERENCES `pfe_2`.`equipe` (`Numero`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `fk_Etudiant_Groupe1`
    FOREIGN KEY (`Groupe_Numero`)
    REFERENCES `pfe_2`.`groupe` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`appriciation_indv_assiduite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`appriciation_indv_assiduite` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Assiduite` FLOAT NOT NULL,
  `etudiant_Matricule` VARCHAR(45) NOT NULL,
  `Seance_encadrement_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`Numero`, `etudiant_Matricule`, `Seance_encadrement_Numero`),
  INDEX `fk_Appriciation_indv_assiduite_Seance_encadrement1_idx` (`Seance_encadrement_Numero` ASC),
  INDEX `fk_appriciation_indv_assiduite_etudiant1_idx` (`etudiant_Matricule` ASC),
  CONSTRAINT `fk_Appriciation_indv_assiduite_Seance_encadrement1`
    FOREIGN KEY (`Seance_encadrement_Numero`)
    REFERENCES `pfe_2`.`seance_encadrement` (`Numero`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appriciation_indv_assiduite_etudiant1`
    FOREIGN KEY (`etudiant_Matricule`)
    REFERENCES `pfe_2`.`etudiant` (`Matricule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`choisir`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`choisir` (
  `Equipe_Numero` INT(11) NOT NULL,
  `Projet_Code` INT(11) NOT NULL,
  `Projet` LONGTEXT NOT NULL,
  `Ordre` INT(11) NOT NULL,
  PRIMARY KEY (`Equipe_Numero`, `Projet_Code`),
  INDEX `fk_Equipe_has_Projet_Projet1_idx` (`Projet_Code` ASC),
  INDEX `fk_Equipe_has_Projet_Equipe1_idx` (`Equipe_Numero` ASC),
  CONSTRAINT `fk_Equipe_has_Projet_Equipe1`
    FOREIGN KEY (`Equipe_Numero`)
    REFERENCES `pfe_2`.`equipe` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipe_has_Projet_Projet1`
    FOREIGN KEY (`Projet_Code`)
    REFERENCES `pfe_2`.`projet` (`Code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`element_evaluation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`element_evaluation` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Coe_Logiciel` INT(11) NOT NULL,
  `Coe_Livrable` INT(11) NOT NULL,
  `Coe_Presentation` INT(11) NOT NULL,
  `Coe_AppGlobale` INT(11) NOT NULL,
  `Coe_AppIndividu` INT(11) NOT NULL,
  `promotion_ID` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `promotion_ID`),
  INDEX `fk_element_evaluation_promotion1_idx` (`promotion_ID` ASC),
  CONSTRAINT `fk_element_evaluation_promotion1`
    FOREIGN KEY (`promotion_ID`)
    REFERENCES `pfe_2`.`promotion` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`jury`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`jury` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Nmbre_enseignant` INT(11) NOT NULL,
  PRIMARY KEY (`Numero`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`enseignant_has_jury`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`enseignant_has_jury` (
  `Enseignant_Matricule` VARCHAR(45) NOT NULL,
  `Jury_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`Enseignant_Matricule`, `Jury_Numero`),
  INDEX `fk_Enseignant_has_Jury_Jury1_idx` (`Jury_Numero` ASC),
  INDEX `fk_Enseignant_has_Jury_Enseignant1_idx` (`Enseignant_Matricule` ASC),
  CONSTRAINT `fk_Enseignant_has_Jury_Enseignant1`
    FOREIGN KEY (`Enseignant_Matricule`)
    REFERENCES `pfe_2`.`enseignant` (`Matricule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Enseignant_has_Jury_Jury1`
    FOREIGN KEY (`Jury_Numero`)
    REFERENCES `pfe_2`.`jury` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`note`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`note` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `note_livrable` FLOAT NULL DEFAULT NULL,
  `note_logiciel` FLOAT NULL DEFAULT NULL,
  `note_presentation` FLOAT NULL DEFAULT NULL,
  `enseignant_Matricule` VARCHAR(45) NOT NULL,
  `equipe_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `enseignant_Matricule`, `equipe_Numero`),
  INDEX `fk_note_enseignant1_idx` (`enseignant_Matricule` ASC),
  INDEX `fk_note_equipe1_idx` (`equipe_Numero` ASC),
  CONSTRAINT `fk_note_enseignant1`
    FOREIGN KEY (`enseignant_Matricule`)
    REFERENCES `pfe_2`.`enseignant` (`Matricule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_note_equipe1`
    FOREIGN KEY (`equipe_Numero`)
    REFERENCES `pfe_2`.`equipe` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`parametre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`parametre` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Promotion_Numero` INT(11) NOT NULL,
  `Nbr_max` INT(11) NOT NULL,
  `Nbr_min` INT(11) NOT NULL,
  `Affection_mode` VARCHAR(45) NOT NULL,
  `Nombre_Choix` INT(11) NOT NULL,
  `Duree` VARCHAR(45) NOT NULL,
  `Coefficient` INT(11) NOT NULL,
  PRIMARY KEY (`ID`, `Promotion_Numero`),
  INDEX `fk_Parametre_Promotion1_idx` (`Promotion_Numero` ASC),
  CONSTRAINT `fk_Parametre_Promotion1`
    FOREIGN KEY (`Promotion_Numero`)
    REFERENCES `pfe_2`.`promotion` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`proposer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`proposer` (
  `Enseignant_Matricule` VARCHAR(45) NOT NULL,
  `Projet_Code` INT(11) NOT NULL,
  PRIMARY KEY (`Enseignant_Matricule`, `Projet_Code`),
  INDEX `fk_Enseignant_has_Projet_Projet1_idx` (`Projet_Code` ASC),
  INDEX `fk_Enseignant_has_Projet_Enseignant1_idx` (`Enseignant_Matricule` ASC),
  CONSTRAINT `fk_Enseignant_has_Projet_Enseignant1`
    FOREIGN KEY (`Enseignant_Matricule`)
    REFERENCES `pfe_2`.`enseignant` (`Matricule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Enseignant_has_Projet_Projet1`
    FOREIGN KEY (`Projet_Code`)
    REFERENCES `pfe_2`.`projet` (`Code`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`salle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`salle` (
  `Numero` INT(11) NOT NULL AUTO_INCREMENT,
  `Designation` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Numero`),
  UNIQUE INDEX `Designation_UNIQUE` (`Designation` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pfe_2`.`soutenance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pfe_2`.`soutenance` (
  `Code` INT(11) NOT NULL AUTO_INCREMENT,
  `Jour` DATE NOT NULL,
  `Heure_Debut` VARCHAR(45) NOT NULL,
  `Heur_Fin` VARCHAR(45) NOT NULL,
  `Designation_Salle` VARCHAR(45) NULL DEFAULT NULL,
  `Salle_Numero` INT(11) NOT NULL,
  `Equipe_Numero` INT(11) NOT NULL,
  `Jury_Numero` INT(11) NOT NULL,
  PRIMARY KEY (`Code`, `Salle_Numero`, `Equipe_Numero`, `Jury_Numero`),
  INDEX `fk_Soutenance_Salle1_idx` (`Salle_Numero` ASC),
  INDEX `fk_Soutenance_Equipe1_idx` (`Equipe_Numero` ASC),
  INDEX `fk_Soutenance_Jury1_idx` (`Jury_Numero` ASC),
  CONSTRAINT `fk_Soutenance_Equipe1`
    FOREIGN KEY (`Equipe_Numero`)
    REFERENCES `pfe_2`.`equipe` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Soutenance_Jury1`
    FOREIGN KEY (`Jury_Numero`)
    REFERENCES `pfe_2`.`jury` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Soutenance_Salle1`
    FOREIGN KEY (`Salle_Numero`)
    REFERENCES `pfe_2`.`salle` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
