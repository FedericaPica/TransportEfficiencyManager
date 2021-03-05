USE `tem`;

/*Data for the table `dettaglio_utente` */

/*Data for the table `utente` */

INSERT INTO profilo (nome_profilo) VALUES ('Admin');
INSERT INTO profilo (nome_profilo) VALUES ('Azienda');

insert  into `utente`(`id`,`email`,`password`,`username`,`id_dettaglio`,`id_profilo`) values (1,'federica.pica@tem.com','$2a$10$XGvTFYeOqD3W0ZQyeuh6oeP63DYLMAPqQMcpEKKQXQrNwgUWeofAm','Federica.Pica',NULL,1),(2,'federica.attianese@tem.com','$2a$10$XGvTFYeOqD3W0ZQyeuh6oeP63DYLMAPqQMcpEKKQXQrNwgUWeofAm','Federica.Attianese',NULL,1),(3,'francesca.moschella@tem.com','$2a$10$XGvTFYeOqD3W0ZQyeuh6oeP63DYLMAPqQMcpEKKQXQrNwgUWeofAm','Francesca.Moschella',NULL,1);

