-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Dim 07 Mai 2017 à 01:40
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `mydb`
--
CREATE DATABASE IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mydb`;

-- --------------------------------------------------------

--
-- Structure de la table `activitej`
--

CREATE TABLE `activitej` (
  `idActiviteJ` int(11) NOT NULL,
  `jourActiviteJ` int(45) NOT NULL,
  `idRapport` int(45) NOT NULL,
  `idProjet` int(45) NOT NULL,
  `fraisActiviteJ` double NOT NULL,
  `kmActiviteJ` double NOT NULL,
  `status` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `activitej`
--

INSERT INTO `activitej` (`idActiviteJ`, `jourActiviteJ`, `idRapport`, `idProjet`, `fraisActiviteJ`, `kmActiviteJ`, `status`) VALUES
(1, 1, 2, 1, 160, 160, 0),
(2, 2, 2, 2, 8, 8, 0),
(3, 3, 3, 1, 2, 4, 0),
(4, 4, 4, 2, 8, 16, 0),
(5, 20, 4, 1, 20, 20, 0),
(6, 10, 4, 1, 10, 10, 0),
(9, 10, 73, 1, 11, 11, 0),
(10, 10, 73, 2, 2, 2, 0),
(11, 11, 73, 1, 11, 12, 0),
(12, 11, 3, 1, 2, 4, 0),
(13, 11, 3, 2, 2, 4, 0),
(14, 3, 76, 1, 10, 10, 0),
(15, 4, 4, 2, 8, 16, 0);

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE `employe` (
  `idEmploye` int(11) NOT NULL,
  `nomEmploye` varchar(45) NOT NULL,
  `prenomEmploye` varchar(45) NOT NULL,
  `mdpEmploye` varchar(45) NOT NULL,
  `mailEmploye` varchar(45) NOT NULL,
  `niveauAccesEmploye` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `employe`
--

INSERT INTO `employe` (`idEmploye`, `nomEmploye`, `prenomEmploye`, `mdpEmploye`, `mailEmploye`, `niveauAccesEmploye`) VALUES
(1, 'Moua', 'Paul', 'mdp', 'mouapaul@hotmail.com', 0),
(2, 'Siarri', 'Nicolas', 'nico', 'nico@hotmail.Fr', 1),
(3, 'Boudiaf', 'Ryan', 'mdp', 'lol', 2);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE `message` (
  `idMessage` int(11) NOT NULL,
  `idDestinataire` int(11) NOT NULL,
  `idEmetteur` int(11) NOT NULL,
  `dateEnvoi` date NOT NULL,
  `message` longtext NOT NULL,
  `status` int(11) NOT NULL,
  `objet` longtext
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Contenu de la table `message`
--

INSERT INTO `message` (`idMessage`, `idDestinataire`, `idEmetteur`, `dateEnvoi`, `message`, `status`, `objet`) VALUES
(10, 2, 1, '2017-01-01', 'Message Paul>Nico', 0, 'Paul>Nico'),
(11, 1, 2, '2017-01-01', 'Message Nico>Paul', 0, 'Nico>Paul');

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

CREATE TABLE `projet` (
  `idProjet` int(11) NOT NULL,
  `libelleProjet` varchar(45) NOT NULL,
  `etat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `projet`
--

INSERT INTO `projet` (`idProjet`, `libelleProjet`, `etat`) VALUES
(1, 'Projet1etat0', 0),
(2, 'Projet2etat1', 1),
(4, 'EssaiProjet', 0),
(5, 'EssaiBis', 0);

-- --------------------------------------------------------

--
-- Structure de la table `rapportprevimensuel`
--

CREATE TABLE `rapportprevimensuel` (
  `idRapportPreviMensuel` int(11) NOT NULL,
  `anneeRapportPreviMensuel` int(11) NOT NULL,
  `moisRapportPreviMensuel` int(11) NOT NULL,
  `idEmploye` int(11) NOT NULL,
  `status` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `rapportprevimensuel`
--

INSERT INTO `rapportprevimensuel` (`idRapportPreviMensuel`, `anneeRapportPreviMensuel`, `moisRapportPreviMensuel`, `idEmploye`, `status`) VALUES
(1, 2017, 1, 0, 0),
(2, 2017, 2, 1, 1),
(3, 2016, 2, 2, 0),
(4, 2017, 4, 2, 0),
(71, 2016, 1, 1, 0),
(72, 2016, 2, 3, 0),
(73, 2016, 1, 2, 1),
(74, 2016, 8, 1, 1),
(75, 2016, 7, 1, 1),
(76, 2016, 12, 3, 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `activitej`
--
ALTER TABLE `activitej`
  ADD PRIMARY KEY (`idActiviteJ`);

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`idEmploye`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`idMessage`);

--
-- Index pour la table `projet`
--
ALTER TABLE `projet`
  ADD PRIMARY KEY (`idProjet`);

--
-- Index pour la table `rapportprevimensuel`
--
ALTER TABLE `rapportprevimensuel`
  ADD PRIMARY KEY (`idRapportPreviMensuel`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `activitej`
--
ALTER TABLE `activitej`
  MODIFY `idActiviteJ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT pour la table `employe`
--
ALTER TABLE `employe`
  MODIFY `idEmploye` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
  MODIFY `idMessage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `projet`
--
ALTER TABLE `projet`
  MODIFY `idProjet` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `rapportprevimensuel`
--
ALTER TABLE `rapportprevimensuel`
  MODIFY `idRapportPreviMensuel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
