
DROP TABLE IF EXISTS generazione_automatica;
DROP VIEW IF EXISTS generazione_automatica;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `generazione_automatica` AS (
select  `dati_corsa`.`id` AS `id`,  `dati_corsa`.`linea_corsa` AS `linea_corsa`,  if(avg(`dati_corsa`.`traffico`) >= 0.5,'Si','No') AS `traffico`,  cast((sum(`dati_corsa`.`passeggeri_non_saliti`) + sum(`dati_corsa`.`passeggeri_saliti`)) / count(`dati_corsa`.`linea_corsa`) as signed) AS `attesi`,  `dati_corsa`.`orario_corsa` AS `orario`,  NULL AS `conducente`,  NULL AS `mezzo`,  `dati_corsa`.`azienda_id` AS `azienda_id`,  IF(cast(`dati_corsa`.`andata` as signed)>0, 'true','false') AS `andata` from `dati_corsa` group by `dati_corsa`.`linea_corsa`,`dati_corsa`.`orario_corsa`,`dati_corsa`.`andata` order by `dati_corsa`.`orario_corsa`)
