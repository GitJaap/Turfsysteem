-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 30, 2014 at 09:57 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `turf_db`
--
CREATE DATABASE IF NOT EXISTS `turf_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `turf_db`;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
`account_id` mediumint(8) unsigned NOT NULL,
  `account_name` varchar(40) NOT NULL,
  `balance` mediumint(9) NOT NULL,
  `pincode` char(40) DEFAULT NULL,
  `account_creation_date` datetime NOT NULL,
  `credit_limit` mediumint(8) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_id`, `account_name`, `balance`, `pincode`, `account_creation_date`, `credit_limit`) VALUES
(1, 'Jaap', 10000, '39dfa55283318d31afe5a3ff4a0e3253e2045e43', '2014-09-27 13:22:33', 0);

-- --------------------------------------------------------

--
-- Table structure for table `admin_changes`
--

CREATE TABLE IF NOT EXISTS `admin_changes` (
`admin_change_id` mediumint(8) unsigned NOT NULL,
  `current_product_price_class_id` mediumint(8) unsigned NOT NULL,
  `admin_id` mediumint(8) unsigned NOT NULL,
  `admin_change_date` datetime NOT NULL,
  `admin_change_description` varchar(45) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin_changes`
--

INSERT INTO `admin_changes` (`admin_change_id`, `current_product_price_class_id`, `admin_id`, `admin_change_date`, `admin_change_description`) VALUES
(1, 1, 1, '2014-09-29 22:35:29', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `admin_logs`
--

CREATE TABLE IF NOT EXISTS `admin_logs` (
`admin_log_id` mediumint(8) unsigned NOT NULL,
  `admin_log_date` datetime NOT NULL,
  `admin_log_type` tinyint(1) NOT NULL DEFAULT '1',
  `bar_id` mediumint(8) unsigned NOT NULL,
  `flow_meter_readout` float NOT NULL,
  `admin_id` mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin_logs`
--

INSERT INTO `admin_logs` (`admin_log_id`, `admin_log_date`, `admin_log_type`, `bar_id`, `flow_meter_readout`, `admin_id`) VALUES
(1, '2014-09-28 01:27:31', 1, 1, 1000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE IF NOT EXISTS `admins` (
`admin_id` mediumint(8) unsigned NOT NULL,
  `user_id` mediumint(8) unsigned DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `pass` char(40) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`admin_id`, `user_id`, `login`, `pass`) VALUES
(1, NULL, 'default', '7505d64a54e061b7acd54ccd58b49dc43500b635'),
(2, NULL, 'kasper', '789b49606c321c8cf228d17942608eff0ccc4171');

-- --------------------------------------------------------

--
-- Table structure for table `bars`
--

CREATE TABLE IF NOT EXISTS `bars` (
`bar_id` mediumint(8) unsigned NOT NULL,
  `bar_name` varchar(45) NOT NULL,
  `current_bar_cash` mediumint(8) unsigned NOT NULL,
  `bar_visibility` tinyint(1) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `bars`
--

INSERT INTO `bars` (`bar_id`, `bar_name`, `current_bar_cash`, `bar_visibility`) VALUES
(1, 'Bovenbar', 0, 1),
(2, 'Benedenbar', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `cards`
--

CREATE TABLE IF NOT EXISTS `cards` (
`card_id` mediumint(8) unsigned NOT NULL,
  `account_id` mediumint(8) unsigned NOT NULL,
  `card_uid` varchar(50) NOT NULL,
  `card_atr` varchar(50) DEFAULT NULL,
  `card_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `client_logs`
--

CREATE TABLE IF NOT EXISTS `client_logs` (
`client_log_id` mediumint(8) unsigned NOT NULL,
  `client_id` mediumint(8) unsigned NOT NULL,
  `client_log_type` tinyint(1) DEFAULT NULL,
  `log_date` datetime NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=129 ;

--
-- Dumping data for table `client_logs`
--

INSERT INTO `client_logs` (`client_log_id`, `client_id`, `client_log_type`, `log_date`) VALUES
(3, 1, 1, '2014-09-30 12:39:52'),
(4, 2, 1, '2014-09-30 12:42:27'),
(5, 2, 0, '2014-09-30 12:42:42'),
(6, 5, 1, '2014-09-30 12:43:31'),
(7, 1, 0, '2014-09-30 13:50:29'),
(8, 1, 0, '2014-09-30 13:59:12'),
(9, 1, 0, '2014-09-30 14:03:13'),
(10, 1, 0, '2014-09-30 14:06:11'),
(11, 1, 0, '2014-09-30 14:09:40'),
(12, 1, 0, '2014-09-30 14:10:24'),
(13, 1, 1, '2014-09-30 14:30:25'),
(14, 1, 0, '2014-09-30 14:30:29'),
(15, 3, 1, '2014-09-30 14:31:49'),
(16, 3, 0, '2014-09-30 14:32:15'),
(17, 1, 1, '2014-09-30 14:32:22'),
(18, 1, 0, '2014-09-30 14:33:00'),
(19, 1, 1, '2014-09-30 15:13:42'),
(20, 1, 0, '2014-09-30 15:13:43'),
(21, 5, 1, '2014-09-30 15:14:23'),
(22, 5, 0, '2014-09-30 15:15:18'),
(23, 3, 1, '2014-09-30 15:15:34'),
(24, 3, 0, '2014-09-30 15:15:35'),
(25, 3, 1, '2014-09-30 15:17:50'),
(26, 3, 0, '2014-09-30 15:18:09'),
(27, 2, 1, '2014-09-30 15:18:26'),
(28, 2, 1, '2014-09-30 15:18:32'),
(29, 2, 0, '2014-09-30 15:18:34'),
(30, 2, 0, '2014-09-30 15:18:35'),
(31, 1, 1, '2014-09-30 15:40:29'),
(32, 3, 1, '2014-09-30 15:40:50'),
(33, 1, 0, '2014-09-30 15:40:56'),
(34, 2, 1, '2014-09-30 15:41:14'),
(35, 2, 0, '2014-09-30 15:41:16'),
(36, 3, 0, '2014-09-30 15:41:17'),
(37, 1, 1, '2014-09-30 17:09:32'),
(38, 1, 0, '2014-09-30 17:09:33'),
(39, 1, 1, '2014-09-30 17:09:38'),
(40, 1, 0, '2014-09-30 17:09:40'),
(41, 4, 1, '2014-09-30 17:10:25'),
(42, 4, 0, '2014-09-30 17:10:27'),
(43, 1, 1, '2014-09-30 17:10:32'),
(44, 1, 0, '2014-09-30 17:10:33'),
(45, 4, 1, '2014-09-30 17:11:24'),
(46, 1, 1, '2014-09-30 17:11:33'),
(47, 1, 0, '2014-09-30 17:11:34'),
(48, 1, 1, '2014-09-30 17:19:02'),
(49, 1, 0, '2014-09-30 17:19:04'),
(50, 1, 1, '2014-09-30 17:20:12'),
(51, 3, 1, '2014-09-30 17:20:19'),
(52, 6, 1, '2014-09-30 17:20:25'),
(53, 5, 1, '2014-09-30 17:20:37'),
(54, 5, 0, '2014-09-30 17:20:38'),
(55, 6, 0, '2014-09-30 17:20:41'),
(56, 3, 0, '2014-09-30 17:21:07'),
(57, 1, 0, '2014-09-30 17:21:09'),
(58, 1, 1, '2014-09-30 17:21:15'),
(59, 3, 1, '2014-09-30 17:21:23'),
(60, 3, 0, '2014-09-30 17:21:24'),
(61, 1, 0, '2014-09-30 17:21:30'),
(62, 1, 1, '2014-09-30 20:47:33'),
(63, 3, 1, '2014-09-30 20:47:43'),
(64, 3, 0, '2014-09-30 20:47:44'),
(65, 1, 0, '2014-09-30 20:47:53'),
(66, 1, 1, '2014-09-30 20:48:42'),
(67, 4, 1, '2014-09-30 20:48:57'),
(68, 1, 0, '2014-09-30 20:49:09'),
(69, 4, 0, '2014-09-30 20:49:57'),
(70, 1, 1, '2014-09-30 20:50:14'),
(71, 1, 0, '2014-09-30 20:50:27'),
(72, 1, 1, '2014-09-30 20:53:39'),
(73, 1, 0, '2014-09-30 20:54:09'),
(74, 1, 1, '2014-09-30 20:54:15'),
(75, 1, 0, '2014-09-30 20:57:18'),
(76, 1, 1, '2014-09-30 20:58:35'),
(77, 2, 1, '2014-09-30 21:00:07'),
(78, 2, 0, '2014-09-30 21:00:10'),
(79, 2, 1, '2014-09-30 21:01:21'),
(80, 2, 0, '2014-09-30 21:01:23'),
(81, 2, 1, '2014-09-30 21:01:52'),
(82, 2, 0, '2014-09-30 21:01:54'),
(83, 2, 1, '2014-09-30 21:03:18'),
(84, 2, 0, '2014-09-30 21:03:19'),
(85, 2, 1, '2014-09-30 21:03:27'),
(86, 2, 0, '2014-09-30 21:03:28'),
(87, 2, 1, '2014-09-30 21:03:58'),
(88, 2, 0, '2014-09-30 21:04:54'),
(89, 2, 1, '2014-09-30 21:05:01'),
(90, 3, 1, '2014-09-30 21:07:31'),
(91, 3, 0, '2014-09-30 21:10:32'),
(92, 2, 0, '2014-09-30 21:10:34'),
(93, 2, 1, '2014-09-30 21:10:38'),
(94, 2, 0, '2014-09-30 21:10:40'),
(95, 2, 1, '2014-09-30 21:13:14'),
(96, 3, 1, '2014-09-30 21:14:39'),
(97, 3, 0, '2014-09-30 21:14:40'),
(98, 2, 0, '2014-09-30 21:14:50'),
(99, 2, 1, '2014-09-30 21:19:42'),
(100, 2, 0, '2014-09-30 21:19:43'),
(101, 2, 1, '2014-09-30 21:26:13'),
(102, 2, 0, '2014-09-30 21:26:14'),
(103, 1, 1, '2014-09-30 21:29:52'),
(104, 1, 0, '2014-09-30 21:29:53'),
(105, 1, 1, '2014-09-30 21:31:55'),
(106, 1, 0, '2014-09-30 21:31:58'),
(107, 1, 1, '2014-09-30 21:32:35'),
(108, 1, 0, '2014-09-30 21:32:37'),
(109, 1, 1, '2014-09-30 21:33:52'),
(110, 1, 0, '2014-09-30 21:34:45'),
(111, 1, 1, '2014-09-30 21:44:02'),
(112, 1, 0, '2014-09-30 21:44:04'),
(113, 1, 1, '2014-09-30 21:44:29'),
(114, 1, 0, '2014-09-30 21:45:14'),
(115, 1, 1, '2014-09-30 21:45:58'),
(116, 1, 0, '2014-09-30 21:46:14'),
(117, 1, 1, '2014-09-30 21:46:22'),
(118, 1, 0, '2014-09-30 21:46:30'),
(119, 1, 1, '2014-09-30 21:46:48'),
(120, 1, 0, '2014-09-30 21:46:58'),
(121, 1, 1, '2014-09-30 21:47:04'),
(122, 1, 0, '2014-09-30 21:47:06'),
(123, 1, 1, '2014-09-30 21:47:17'),
(124, 1, 0, '2014-09-30 21:47:19'),
(125, 1, 1, '2014-09-30 21:47:30'),
(126, 1, 0, '2014-09-30 21:47:46'),
(127, 1, 1, '2014-09-30 21:48:45'),
(128, 1, 0, '2014-09-30 21:48:58');

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
`client_id` mediumint(8) unsigned NOT NULL,
  `client_name` varchar(45) NOT NULL,
  `client_visibility` tinyint(1) NOT NULL,
  `bar_id` mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`client_id`, `client_name`, `client_visibility`, `bar_id`) VALUES
(1, 'PClinks', 1, 1),
(2, 'PCrechts', 1, 1),
(3, 'PCbeheer', 1, 1),
(4, 'PClinks', 1, 2),
(5, 'PCrechts', 1, 2),
(6, 'PCBeheer', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `event_dates`
--

CREATE TABLE IF NOT EXISTS `event_dates` (
`event_id` mediumint(8) unsigned NOT NULL,
  `event_name` varchar(20) NOT NULL,
  `event_start` datetime NOT NULL,
  `event_ending` datetime NOT NULL,
  `event_description` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `product_bar_visibility`
--

CREATE TABLE IF NOT EXISTS `product_bar_visibility` (
  `product_version_id` mediumint(8) unsigned NOT NULL,
  `product_type_id` mediumint(8) unsigned NOT NULL,
  `bar_id` mediumint(8) unsigned NOT NULL,
  `product_bar_visibility` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_bar_visibility`
--

INSERT INTO `product_bar_visibility` (`product_version_id`, `product_type_id`, `bar_id`, `product_bar_visibility`) VALUES
(1, 1, 1, 1),
(1, 2, 1, 1),
(1, 3, 1, 1),
(1, 4, 1, 1),
(1, 5, 1, 1),
(1, 6, 1, 1),
(1, 7, 1, 1),
(1, 8, 1, 1),
(1, 9, 1, 1),
(1, 10, 1, 1),
(1, 11, 1, 1),
(1, 14, 1, 1),
(1, 15, 1, 1),
(1, 16, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `product_class`
--

CREATE TABLE IF NOT EXISTS `product_class` (
`product_class_id` mediumint(8) unsigned NOT NULL,
  `class_name` varchar(45) NOT NULL,
  `class_color_rgb` varchar(11) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `product_class`
--

INSERT INTO `product_class` (`product_class_id`, `class_name`, `class_color_rgb`) VALUES
(1, 'Drinken', '255,000,000'),
(2, 'Eten', '000,255,000'),
(3, 'Shift', '000,000,255');

-- --------------------------------------------------------

--
-- Table structure for table `product_price_class`
--

CREATE TABLE IF NOT EXISTS `product_price_class` (
`product_price_class_id` mediumint(8) unsigned NOT NULL,
  `class_name` varchar(45) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `product_price_class`
--

INSERT INTO `product_price_class` (`product_price_class_id`, `class_name`) VALUES
(1, 'Normaal'),
(2, 'Evenement'),
(3, 'Extern');

-- --------------------------------------------------------

--
-- Table structure for table `product_transactions`
--

CREATE TABLE IF NOT EXISTS `product_transactions` (
`product_transaction_id` mediumint(8) unsigned NOT NULL,
  `product_version_id` mediumint(8) unsigned NOT NULL,
  `product_type_id` mediumint(8) unsigned NOT NULL,
  `transaction_credit_id` mediumint(8) unsigned NOT NULL,
  `product_amount` smallint(5) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `product_types`
--

CREATE TABLE IF NOT EXISTS `product_types` (
`product_version_id` mediumint(8) unsigned NOT NULL,
  `product_type_id` mediumint(8) unsigned NOT NULL,
  `product_class_id` mediumint(8) unsigned NOT NULL,
  `product_price_class_id` mediumint(8) unsigned NOT NULL,
  `product_price` mediumint(8) unsigned NOT NULL,
  `product_name` varchar(20) NOT NULL,
  `creation_date` datetime NOT NULL,
  `supply_product_id` mediumint(8) unsigned NOT NULL,
  `supply_product_percentage` float unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `product_types`
--

INSERT INTO `product_types` (`product_version_id`, `product_type_id`, `product_class_id`, `product_price_class_id`, `product_price`, `product_name`, `creation_date`, `supply_product_id`, `supply_product_percentage`) VALUES
(1, 1, 1, 1, 90, 'Cola', '2014-09-14 17:19:33', 1, 100),
(1, 2, 1, 1, 90, 'Fanta', '2014-09-14 17:19:33', 1, 100),
(1, 3, 1, 1, 100, 'Bier', '2014-09-14 17:13:34', 1, 100),
(1, 4, 1, 1, 90, 'Cassis', '2014-09-14 17:19:33', 1, 100),
(1, 5, 1, 1, 500, 'Wijn Fles', '2014-09-14 17:19:56', 1, 100),
(1, 6, 1, 1, 190, 'Hoegaarden', '2014-09-14 17:20:18', 1, 100),
(1, 7, 1, 1, 160, 'HJ Pils', '2014-09-14 17:20:36', 1, 100),
(1, 8, 1, 1, 260, 'Kwakje', '2014-09-14 17:21:00', 1, 100),
(1, 9, 2, 1, 150, 'Biba''s', '2014-09-16 00:31:59', 1, 100),
(1, 10, 2, 1, 150, 'Loempia''s', '2014-09-16 00:32:38', 1, 100),
(1, 11, 2, 1, 200, 'Vlammetjes', '2014-09-16 00:32:55', 1, 100),
(1, 14, 2, 1, 150, 'Chips', '2014-09-16 13:05:38', 1, 100),
(1, 15, 2, 1, 50, 'Koek', '2014-09-16 21:08:17', 1, 100),
(1, 16, 1, 1, 90, 'Icetea', '2014-09-16 21:08:17', 1, 100);

-- --------------------------------------------------------

--
-- Table structure for table `supply_order_list`
--

CREATE TABLE IF NOT EXISTS `supply_order_list` (
`supply_order_list_id` mediumint(8) unsigned NOT NULL,
  `supply_order_id` mediumint(8) unsigned NOT NULL,
  `supply_product_id` mediumint(8) unsigned NOT NULL,
  `supply_product_amount` smallint(6) NOT NULL,
  `current_supply_product_price` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `supply_orders`
--

CREATE TABLE IF NOT EXISTS `supply_orders` (
  `supply_order_id` mediumint(8) unsigned NOT NULL,
  `order_date` datetime NOT NULL,
  `order_price` mediumint(8) unsigned NOT NULL,
  `admin_id` mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supply_products`
--

CREATE TABLE IF NOT EXISTS `supply_products` (
`supply_product_id` mediumint(8) unsigned NOT NULL,
  `product_name` varchar(45) NOT NULL,
  `current_supply_amount` float unsigned NOT NULL,
  `critical_supply_amount` float unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `supply_products`
--

INSERT INTO `supply_products` (`supply_product_id`, `product_name`, `current_supply_amount`, `critical_supply_amount`) VALUES
(1, 'TEST', 10000, 10);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_types`
--

CREATE TABLE IF NOT EXISTS `transaction_types` (
`transaction_type_id` mediumint(8) unsigned NOT NULL,
  `transaction_type_name` varchar(45) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `transaction_types`
--

INSERT INTO `transaction_types` (`transaction_type_id`, `transaction_type_name`) VALUES
(1, 'barpas'),
(2, 'pin'),
(3, 'contant'),
(4, 'schrapkaart');

-- --------------------------------------------------------

--
-- Table structure for table `transactions_credit`
--

CREATE TABLE IF NOT EXISTS `transactions_credit` (
`transaction_credit_id` mediumint(8) unsigned NOT NULL,
  `account_id` mediumint(8) unsigned NOT NULL,
  `admin_id` mediumint(8) unsigned NOT NULL,
  `client_id` mediumint(8) unsigned NOT NULL,
  `transaction_type_id` mediumint(8) unsigned NOT NULL,
  `transaction_date` datetime NOT NULL,
  `transaction_amount` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `transactions_debit`
--

CREATE TABLE IF NOT EXISTS `transactions_debit` (
`transaction_debit_id` mediumint(8) unsigned NOT NULL,
  `account_id` mediumint(8) unsigned NOT NULL,
  `admin_id` mediumint(8) unsigned NOT NULL,
  `client_id` mediumint(8) unsigned NOT NULL,
  `transaction_type_id` mediumint(8) unsigned NOT NULL,
  `transaction_date` datetime NOT NULL,
  `transaction_amount` mediumint(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`user_id` mediumint(8) unsigned NOT NULL,
  `account_id` mediumint(8) unsigned NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `pass` char(40) NOT NULL,
  `registration_date` datetime NOT NULL,
  `birth_date` date NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `account_id`, `first_name`, `last_name`, `email`, `pass`, `registration_date`, `birth_date`) VALUES
(1, 1, 'Jaap', 'Wesdorp', 'Jaapwesdorp@gmail.com', '789b49606c321c8cf228d17942608eff0ccc4171', '2014-09-20 23:51:17', '1993-03-25');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
 ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `admin_changes`
--
ALTER TABLE `admin_changes`
 ADD PRIMARY KEY (`admin_change_id`), ADD KEY `current_product_price_class_id` (`current_product_price_class_id`), ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `admin_logs`
--
ALTER TABLE `admin_logs`
 ADD PRIMARY KEY (`admin_log_id`), ADD KEY `bar_id` (`bar_id`), ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
 ADD PRIMARY KEY (`admin_id`), ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `bars`
--
ALTER TABLE `bars`
 ADD PRIMARY KEY (`bar_id`);

--
-- Indexes for table `cards`
--
ALTER TABLE `cards`
 ADD PRIMARY KEY (`card_id`), ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `client_logs`
--
ALTER TABLE `client_logs`
 ADD PRIMARY KEY (`client_log_id`), ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
 ADD PRIMARY KEY (`client_id`), ADD KEY `bar_id` (`bar_id`);

--
-- Indexes for table `event_dates`
--
ALTER TABLE `event_dates`
 ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `product_bar_visibility`
--
ALTER TABLE `product_bar_visibility`
 ADD PRIMARY KEY (`product_version_id`,`product_type_id`,`bar_id`), ADD KEY `bar_id` (`bar_id`);

--
-- Indexes for table `product_class`
--
ALTER TABLE `product_class`
 ADD PRIMARY KEY (`product_class_id`);

--
-- Indexes for table `product_price_class`
--
ALTER TABLE `product_price_class`
 ADD PRIMARY KEY (`product_price_class_id`);

--
-- Indexes for table `product_transactions`
--
ALTER TABLE `product_transactions`
 ADD PRIMARY KEY (`product_transaction_id`), ADD KEY `product_version_id` (`product_version_id`,`product_type_id`), ADD KEY `transaction_credit_id` (`transaction_credit_id`);

--
-- Indexes for table `product_types`
--
ALTER TABLE `product_types`
 ADD PRIMARY KEY (`product_version_id`,`product_type_id`), ADD KEY `product_class_id` (`product_class_id`), ADD KEY `product_price_class_id` (`product_price_class_id`), ADD KEY `supply_product_id` (`supply_product_id`);

--
-- Indexes for table `supply_order_list`
--
ALTER TABLE `supply_order_list`
 ADD PRIMARY KEY (`supply_order_list_id`), ADD KEY `supply_order_id` (`supply_order_id`), ADD KEY `supply_product_id` (`supply_product_id`);

--
-- Indexes for table `supply_orders`
--
ALTER TABLE `supply_orders`
 ADD PRIMARY KEY (`supply_order_id`), ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `supply_products`
--
ALTER TABLE `supply_products`
 ADD PRIMARY KEY (`supply_product_id`);

--
-- Indexes for table `transaction_types`
--
ALTER TABLE `transaction_types`
 ADD PRIMARY KEY (`transaction_type_id`);

--
-- Indexes for table `transactions_credit`
--
ALTER TABLE `transactions_credit`
 ADD PRIMARY KEY (`transaction_credit_id`), ADD KEY `account_id` (`account_id`), ADD KEY `admin_id` (`admin_id`), ADD KEY `client_id` (`client_id`), ADD KEY `transaction_type_id` (`transaction_type_id`);

--
-- Indexes for table `transactions_debit`
--
ALTER TABLE `transactions_debit`
 ADD PRIMARY KEY (`transaction_debit_id`), ADD KEY `account_id` (`account_id`), ADD KEY `admin_id` (`admin_id`), ADD KEY `client_id` (`client_id`), ADD KEY `transaction_type_id` (`transaction_type_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`user_id`), ADD KEY `account_id` (`account_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
MODIFY `account_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `admin_changes`
--
ALTER TABLE `admin_changes`
MODIFY `admin_change_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `admin_logs`
--
ALTER TABLE `admin_logs`
MODIFY `admin_log_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
MODIFY `admin_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `bars`
--
ALTER TABLE `bars`
MODIFY `bar_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `cards`
--
ALTER TABLE `cards`
MODIFY `card_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `client_logs`
--
ALTER TABLE `client_logs`
MODIFY `client_log_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=129;
--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
MODIFY `client_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `event_dates`
--
ALTER TABLE `event_dates`
MODIFY `event_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_class`
--
ALTER TABLE `product_class`
MODIFY `product_class_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `product_price_class`
--
ALTER TABLE `product_price_class`
MODIFY `product_price_class_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `product_transactions`
--
ALTER TABLE `product_transactions`
MODIFY `product_transaction_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_types`
--
ALTER TABLE `product_types`
MODIFY `product_version_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `supply_order_list`
--
ALTER TABLE `supply_order_list`
MODIFY `supply_order_list_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `supply_products`
--
ALTER TABLE `supply_products`
MODIFY `supply_product_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `transaction_types`
--
ALTER TABLE `transaction_types`
MODIFY `transaction_type_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `transactions_credit`
--
ALTER TABLE `transactions_credit`
MODIFY `transaction_credit_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `transactions_debit`
--
ALTER TABLE `transactions_debit`
MODIFY `transaction_debit_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `user_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin_changes`
--
ALTER TABLE `admin_changes`
ADD CONSTRAINT `admin_changes_ibfk_1` FOREIGN KEY (`current_product_price_class_id`) REFERENCES `product_price_class` (`product_price_class_id`),
ADD CONSTRAINT `admin_changes_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`);

--
-- Constraints for table `admin_logs`
--
ALTER TABLE `admin_logs`
ADD CONSTRAINT `admin_logs_ibfk_1` FOREIGN KEY (`bar_id`) REFERENCES `bars` (`bar_id`),
ADD CONSTRAINT `admin_logs_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`);

--
-- Constraints for table `admins`
--
ALTER TABLE `admins`
ADD CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `cards`
--
ALTER TABLE `cards`
ADD CONSTRAINT `cards_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);

--
-- Constraints for table `client_logs`
--
ALTER TABLE `client_logs`
ADD CONSTRAINT `client_logs_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`);

--
-- Constraints for table `clients`
--
ALTER TABLE `clients`
ADD CONSTRAINT `clients_ibfk_1` FOREIGN KEY (`bar_id`) REFERENCES `bars` (`bar_id`);

--
-- Constraints for table `product_bar_visibility`
--
ALTER TABLE `product_bar_visibility`
ADD CONSTRAINT `product_bar_visibility_ibfk_1` FOREIGN KEY (`product_version_id`, `product_type_id`) REFERENCES `product_types` (`product_version_id`, `product_type_id`),
ADD CONSTRAINT `product_bar_visibility_ibfk_2` FOREIGN KEY (`bar_id`) REFERENCES `bars` (`bar_id`);

--
-- Constraints for table `product_transactions`
--
ALTER TABLE `product_transactions`
ADD CONSTRAINT `product_transactions_ibfk_1` FOREIGN KEY (`product_version_id`, `product_type_id`) REFERENCES `product_types` (`product_version_id`, `product_type_id`),
ADD CONSTRAINT `product_transactions_ibfk_2` FOREIGN KEY (`transaction_credit_id`) REFERENCES `transactions_credit` (`transaction_credit_id`);

--
-- Constraints for table `product_types`
--
ALTER TABLE `product_types`
ADD CONSTRAINT `product_types_ibfk_1` FOREIGN KEY (`product_class_id`) REFERENCES `product_class` (`product_class_id`),
ADD CONSTRAINT `product_types_ibfk_2` FOREIGN KEY (`product_price_class_id`) REFERENCES `product_price_class` (`product_price_class_id`),
ADD CONSTRAINT `product_types_ibfk_3` FOREIGN KEY (`supply_product_id`) REFERENCES `supply_products` (`supply_product_id`);

--
-- Constraints for table `supply_order_list`
--
ALTER TABLE `supply_order_list`
ADD CONSTRAINT `supply_order_list_ibfk_1` FOREIGN KEY (`supply_order_id`) REFERENCES `supply_orders` (`supply_order_id`),
ADD CONSTRAINT `supply_order_list_ibfk_2` FOREIGN KEY (`supply_product_id`) REFERENCES `supply_products` (`supply_product_id`);

--
-- Constraints for table `supply_orders`
--
ALTER TABLE `supply_orders`
ADD CONSTRAINT `supply_orders_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`);

--
-- Constraints for table `transactions_credit`
--
ALTER TABLE `transactions_credit`
ADD CONSTRAINT `transactions_credit_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
ADD CONSTRAINT `transactions_credit_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`),
ADD CONSTRAINT `transactions_credit_ibfk_3` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`),
ADD CONSTRAINT `transactions_credit_ibfk_4` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_types` (`transaction_type_id`);

--
-- Constraints for table `transactions_debit`
--
ALTER TABLE `transactions_debit`
ADD CONSTRAINT `transactions_debit_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
ADD CONSTRAINT `transactions_debit_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`),
ADD CONSTRAINT `transactions_debit_ibfk_3` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`),
ADD CONSTRAINT `transactions_debit_ibfk_4` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_types` (`transaction_type_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
