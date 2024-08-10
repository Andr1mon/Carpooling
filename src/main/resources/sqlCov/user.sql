CREATE DATABASE  IF NOT EXISTS `covoiturage`;
USE `covoiturage`;

--
-- Table structure for table `user`
--
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `ine` int(11) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `password` char(80) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;


