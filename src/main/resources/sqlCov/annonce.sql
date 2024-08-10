CREATE DATABASE  IF NOT EXISTS `covoiturage`;
USE `covoiturage`;

--
-- Table structure for table `annonce`
--
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `annonce`;

CREATE TABLE `annonce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `date` datetime default NULL,
  `depart` varchar(45) DEFAULT NULL,
  `arrive` varchar(45) DEFAULT NULL,
  `latitude1` float(11)  DEFAULT NULL,
  `longitude1` float(11)  DEFAULT NULL,
    `latitude2` float(11)  DEFAULT NULL,
  `longitude2` float(11)  DEFAULT NULL,
  `duree` varchar(45)  DEFAULT NULL,
    `commentaire` varchar(200)  DEFAULT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;