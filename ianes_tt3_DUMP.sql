-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: ianes_tt3
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ambienteAtual`
--

DROP TABLE IF EXISTS `ambienteAtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ambienteAtual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6ojyx5xjo8d6m62o6025c4cs8` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ambienteAtual`
--

LOCK TABLES `ambienteAtual` WRITE;
/*!40000 ALTER TABLE `ambienteAtual` DISABLE KEYS */;
INSERT INTO `ambienteAtual` VALUES (10,'ambiente10'),(2,'ambiente2'),(3,'ambiente3'),(4,'ambiente4'),(5,'ambiente5'),(6,'ambiente6'),(8,'ambiente8'),(9,'ambiente9'),(1,'ambienteAlterado1');
/*!40000 ALTER TABLE `ambienteAtual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriapatrimonio`
--

DROP TABLE IF EXISTS `categoriapatrimonio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriapatrimonio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ax40iwj4kmpf4x2isx9exfuyy` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriapatrimonio`
--

LOCK TABLES `categoriapatrimonio` WRITE;
/*!40000 ALTER TABLE `categoriapatrimonio` DISABLE KEYS */;
INSERT INTO `categoriapatrimonio` VALUES (10,'categoria10'),(3,'categoria3'),(4,'categoria4'),(5,'categoria5'),(6,'categoria6'),(7,'categoria7'),(8,'categoria8'),(9,'categoria9'),(2,'categoriaAlterada2');
/*!40000 ALTER TABLE `categoriapatrimonio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itempatrimonio`
--

DROP TABLE IF EXISTS `itempatrimonio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itempatrimonio` (
  `id` bigint(20) NOT NULL,
  `ambiente_id` bigint(20) DEFAULT NULL,
  `patrimonio_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2esnwx8aqejwfuanr1vgsytc6` (`ambiente_id`),
  KEY `FK9ha8f4xu9yldarxp53l0d67c2` (`patrimonio_id`),
  KEY `FK6gq8esk6m9dncnoavi5dmh5tg` (`usuario_id`),
  CONSTRAINT `FK2esnwx8aqejwfuanr1vgsytc6` FOREIGN KEY (`ambiente_id`) REFERENCES `ambienteAtual` (`id`),
  CONSTRAINT `FK6gq8esk6m9dncnoavi5dmh5tg` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK9ha8f4xu9yldarxp53l0d67c2` FOREIGN KEY (`patrimonio_id`) REFERENCES `patrimonio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itempatrimonio`
--

LOCK TABLES `itempatrimonio` WRITE;
/*!40000 ALTER TABLE `itempatrimonio` DISABLE KEYS */;
/*!40000 ALTER TABLE `itempatrimonio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentacao`
--

DROP TABLE IF EXISTS `movimentacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimentacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_movimentacao` datetime NOT NULL,
  `ambiente_destino` bigint(20) NOT NULL,
  `ambiente_origem` bigint(20) NOT NULL,
  `patrimonio_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgrpkbntgmtbojo5lteu8i35r6` (`ambiente_destino`),
  KEY `FKdyh77dk73eilgdtduoyvlcwq8` (`ambiente_origem`),
  KEY `FK78494f793xpdk18010jxti97y` (`patrimonio_id`),
  KEY `FKn1b61heu0e9kvh1ej2wnnws61` (`usuario_id`),
  CONSTRAINT `FK78494f793xpdk18010jxti97y` FOREIGN KEY (`patrimonio_id`) REFERENCES `patrimonio` (`id`),
  CONSTRAINT `FKdyh77dk73eilgdtduoyvlcwq8` FOREIGN KEY (`ambiente_origem`) REFERENCES `ambienteAtual` (`id`),
  CONSTRAINT `FKgrpkbntgmtbojo5lteu8i35r6` FOREIGN KEY (`ambiente_destino`) REFERENCES `ambienteAtual` (`id`),
  CONSTRAINT `FKn1b61heu0e9kvh1ej2wnnws61` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacao`
--

LOCK TABLES `movimentacao` WRITE;
/*!40000 ALTER TABLE `movimentacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimentacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patrimonio`
--

DROP TABLE IF EXISTS `patrimonio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patrimonio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_cadastro` datetime NOT NULL,
  `nome` varchar(30) NOT NULL,
  `categoria_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7q73rsngejbew61sjrc5qjs0` (`categoria_id`),
  KEY `FKs4khig6sqqfiktgkg3reupt0y` (`usuario_id`),
  CONSTRAINT `FK7q73rsngejbew61sjrc5qjs0` FOREIGN KEY (`categoria_id`) REFERENCES `categoriapatrimonio` (`id`),
  CONSTRAINT `FKs4khig6sqqfiktgkg3reupt0y` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patrimonio`
--

LOCK TABLES `patrimonio` WRITE;
/*!40000 ALTER TABLE `patrimonio` DISABLE KEYS */;
INSERT INTO `patrimonio` VALUES (1,'2018-06-10 19:17:34','patrimonioAlterado1',2,1),(2,'2018-06-10 19:17:40','patrimonio2',3,1),(3,'2018-06-10 19:17:43','patrimonio3',3,1),(4,'2018-06-10 19:17:44','patrimonio4',3,1),(5,'2018-06-10 19:17:45','patrimonio5',3,1),(7,'2018-06-10 19:17:51','patrimonio7',4,1),(8,'2018-06-10 19:17:55','patrimonio8',5,1),(9,'2018-06-10 19:18:04','patrimonio9',6,1),(10,'2018-06-10 19:18:05','patrimonio10',6,1),(11,'2018-06-10 19:18:08','patrimonio11',6,1);
/*!40000 ALTER TABLE `patrimonio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(120) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `senha` varchar(33) NOT NULL,
  `sobrenome` varchar(40) NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4tdehxj7dh8ghfc68kbwbsbll` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin@email.com','Administrador','8db25b95a2e40543a07b2844aa811561','do Sistema',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-10 17:23:54
