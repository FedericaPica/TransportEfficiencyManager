-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 19, 2021 alle 00:16
-- Versione del server: 10.4.17-MariaDB
-- Versione PHP: 7.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tem`
--


--
-- Dump dei dati per la tabella `dettaglio_utente`
--

INSERT INTO `dettaglio_utente` (`id`, `cap`, `citta`, `denominazione`, `fax`, `indirizzo`, `partitaiva`, `telefono`) VALUES
(2, '89024', 'Fisciano', 'IntArtificiale srl', '12345678912345', 'via Progetto, 15', '12345678912', '082494875');




--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id`, `email`, `password`, `username`, `id_dettaglio`, `id_profilo`) VALUES
(5, 'test@fia.com', '$2a$10$Fa9gU1Pk9jHxkAbboUPS3uJk1C8VW/.Fy6sDy/PshYb7.gEFqWdOe', 'FedericaPFia', 2, 2);


--
-- Dump dei dati per la tabella `conducente`
--

INSERT INTO `conducente` (`id`, `codice_fiscale`, `cognome`, `nome`, `azienda_id`) VALUES
(2, 'PCIFDR95A60H501H', 'Pica', 'Federica', 5),
(3, 'MSCFRN96B61B293G', 'Moschella', 'Francesca', 5),
(4, 'TTNFDR94C62O555F', 'Attianese', 'Federica', 5),
(5, 'RSSMRA89D23Z123E', 'Rossi', 'Mario', 5),
(6, 'BNCGDU71E24T456D', 'Bianchi', 'Guido', 5),
(7, 'PRKMLE83F65Q009C', 'Parker', 'Emily', 5),
(8, 'RSSRRA90G66S828B', 'Rossi', 'Aurora', 5),
(9, 'BNCDNL95H67R530A', 'Bianchi', 'Daniela', 5);

--
-- Dump dei dati per la tabella `dati_corsa`
--

INSERT INTO `dati_corsa` (`id`, `andata`, `linea_corsa`, `numero_posti`, `orario_corsa`, `passeggeri_non_saliti`, `passeggeri_saliti`, `traffico`, `azienda_id`) VALUES
(4, b'1', 'FS', 40, '07:00:00', 15, 40, b'1', 5),
(5, b'1', 'FS', 40, '07:00:00', 20, 40, b'0', 5),
(6, b'1', 'FS', 40, '07:00:00', 0, 39, b'1', 5),
(7, b'1', 'BF', 15, '07:50:00', 3, 15, b'0', 5),
(8, b'1', 'BF', 15, '07:50:00', 0, 14, b'0', 5),
(9, b'1', 'BF', 55, '07:50:00', 0, 5, b'0', 5),
(10, b'1', 'SB', 85, '08:30:00', 0, 50, b'0', 5),
(11, b'1', 'SB', 85, '08:30:00', 0, 52, b'0', 5),
(12, b'1', 'SB', 85, '08:30:00', 0, 51, b'1', 5),
(13, b'1', 'BP', 35, '08:00:00', 1, 35, b'0', 5),
(14, b'1', 'BP', 40, '08:00:00', 0, 38, b'0', 5),
(15, b'1', 'BP', 35, '08:00:00', 0, 22, b'0', 5),
(16, b'1', 'PS', 55, '09:00:00', 0, 30, b'0', 5),
(17, b'1', 'PS', 55, '09:00:00', 0, 25, b'0', 5),
(18, b'1', 'PS', 55, '09:00:00', 0, 33, b'0', 5),
(19, b'0', 'FS', 35, '09:00:00', 40, 35, b'0', 5),
(20, b'0', 'FS', 35, '09:00:00', 30, 35, b'1', 5),
(21, b'0', 'BF', 85, '09:45:00', 0, 25, b'0', 5),
(22, b'0', 'BF', 85, '09:45:00', 0, 45, b'0', 5),
(23, b'0', 'SB', 35, '10:00:00', 0, 18, b'0', 5),
(24, b'0', 'SB', 35, '10:00:00', 0, 18, b'0', 5),
(25, b'0', 'BP', 55, '10:10:00', 0, 55, b'1', 5),
(26, b'0', 'BP', 85, '10:10:00', 0, 45, b'1', 5),
(27, b'1', 'FS', 15, '11:10:00', 5, 15, b'0', 5),
(28, b'1', 'FS', 15, '11:10:00', 0, 7, b'0', 5),
(29, b'1', 'SB', 85, '11:55:00', 0, 35, b'0', 5),
(30, b'1', 'SB', 85, '11:55:00', 0, 34, b'0', 5),
(31, b'1', 'SB', 85, '11:55:00', 0, 33, b'1', 5),
(32, b'1', 'BP', 35, '12:10:00', 16, 35, b'1', 5),
(33, b'1', 'BP', 55, '12:10:00', 0, 55, b'1', 5),
(34, b'0', 'SB', 85, '13:10:00', 0, 55, b'0', 5),
(35, b'0', 'SB', 85, '13:10:00', 0, 53, b'0', 5),
(36, b'1', 'BF', 35, '14:30:00', 15, 35, b'0', 5),
(37, b'1', 'BF', 15, '14:30:00', 40, 15, b'1', 5),
(38, b'1', 'BF', 85, '14:30:00', 0, 60, b'0', 5),
(39, b'0', 'PS', 35, '14:35:00', 50, 35, b'1', 5),
(40, b'0', 'PS', 55, '14:35:00', 30, 55, b'1', 5),
(41, b'0', 'PS', 85, '14:35:00', 0, 85, b'1', 5);



--
-- Dump dei dati per la tabella `linea`
--

INSERT INTO `linea` (`id`, `destinazione`, `durata`, `nome`, `partenza`, `azienda_id`) VALUES
(3, 'Salerno', 30, 'FS', 'Fisciano', 5),
(4, 'Fisciano', 60, 'BF', 'Benevento', 5),
(5, 'Benevento', 90, 'SB', 'Salerno', 5),
(6, 'Potenza', 120, 'BP', 'Benevento', 5),
(8, 'Salerno', 90, 'PS', 'Potenza', 5);

--
-- Dump dei dati per la tabella `mezzo`
--

INSERT INTO `mezzo` (`id`, `capienza`, `targa`, `tipo`, `azienda_id`) VALUES
(2, 15, 'BP114GN', 'Minibus', 5),
(3, 35, 'AZ223OQ', 'AutobusA', 5),
(4, 35, 'ZA223QO', 'AutobusB', 5),
(5, 40, 'HG568GE', 'PullmanSA', 5),
(6, 40, 'GH568EG', 'PullmanSB', 5),
(7, 55, 'OU535UO', 'PullmanMA', 5),
(8, 55, 'UO535OU', 'PullmanMB', 5),
(9, 85, 'SZ412CD', 'PullmanLA', 5),
(10, 85, 'ZS412DC', 'PullmanLB', 5);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
