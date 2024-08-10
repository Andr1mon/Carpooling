CREATE DATABASE  IF NOT EXISTS `covoiturage`;
USE `covoiturage`;

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `trajet`;

CREATE TABLE `trajet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) not NULL,
	`annonce_id` int(11) not NULL,
	`est_accepte` int(11) not NULL,
	`conducteur_id` int(11) not NULL,


     constraint `FK_ANNONCE` foreign key (`annonce_id`)
    references `annonce` (`id`)
        on delete no action on update no action,

   constraint `FK_USER` foreign key (`user_id`)
    references `user` (`id`)
    on delete no action on update no action,
    
    
  PRIMARY KEY (`id`,`user_id`,`annonce_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;


