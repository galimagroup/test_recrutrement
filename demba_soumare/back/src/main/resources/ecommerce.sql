-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 13 jan. 2025 à 11:31
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `ecommerce`
--

-- --------------------------------------------------------

--
-- Structure de la table `desired_products`
--

CREATE TABLE `desired_products` (
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `desired_products`
--

INSERT INTO `desired_products` (`user_id`, `product_id`) VALUES
(1, 6);

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`id`, `user_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `order_products`
--

CREATE TABLE `order_products` (
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `order_products`
--

INSERT INTO `order_products` (`order_id`, `product_id`) VALUES
(1, 6),
(1, 7);

-- --------------------------------------------------------

--
-- Structure de la table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `internal_reference` varchar(255) DEFAULT NULL,
  `inventory_status` enum('INSTOCK','LOWSTOCK','OUTOFSTOCK') DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `updated_at` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `products`
--

INSERT INTO `products` (`id`, `category`, `code`, `created_at`, `description`, `image`, `internal_reference`, `inventory_status`, `name`, `price`, `quantity`, `rating`, `updated_at`) VALUES
(6, 'Accessories', 'C001', '2025-01-12 11:26:22.000000', 'Ordinateur portable', NULL, NULL, 'INSTOCK', 'PC', 24800, 24, 3, '2025-01-13 09:08:28.000000'),
(7, 'Accessories', 'C002', '2025-01-12 15:30:38.000000', 'phone', NULL, NULL, NULL, 'PHONE', 5000, 0, 0, '2025-01-13 09:08:28.000000');

-- --------------------------------------------------------

--
-- Structure de la table `tokens`
--

CREATE TABLE `tokens` (
  `id` bigint(20) NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `tokens`
--

INSERT INTO `tokens` (`id`, `expired`, `revoked`, `token`, `token_type`, `user_id`) VALUES
(2, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MzYzNjAzOTEsImV4cCI6MTczNjQ0Njc5MX0.hvzxlaHMZ4nmKRFMgcBOevOvcrwJAC79hu60iBptKNI', 'BEARER', 1),
(3, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW1iYS5zb3VtYXJlQGdhbGltYS1ncm91cC5jb20iLCJpYXQiOjE3MzYzNzMxODEsImV4cCI6MTczNjQ1OTU4MX0.YMV0oTeE5te9kNRduFWbsdx0pRvBleoDHhSUuvRanYY', 'BEARER', 4),
(4, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpYXQiOjE3MzY2Nzc5NDcsImV4cCI6MTczNjc2NDM0N30.gD-FvOC23cYdA8p6L9RGj6MLKd59_fJtmYNu2l_c1oI', 'BEARER', 1),
(5, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW1iYS5zb3VtYXJlQGdhbGltYS1ncm91cC5jb20iLCJpYXQiOjE3MzY3NjQxMDIsImV4cCI6MTczNjg1MDUwMn0.Wrc7eTWBkyMizj3SfyBHW65CO2C6-5eSs3CYnhyCgH8', 'BEARER', 4);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `firstname`, `password`, `username`) VALUES
(1, 'admin@admin.com', 'Administrateur', '$2a$10$nXmOhnElfmLmYGr1YJB7IOvi5T.ONDmrFeqpdo.pkfxlN.KowCLy2', 'admin'),
(4, 'demba.soumare@galima-group.com', 'Demba', '$2a$10$i5Zwma59x873TBaGrvU25eX/s4esf6FJPEDoakSIw2vlJQqC/AiC6', 'dembas');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `desired_products`
--
ALTER TABLE `desired_products`
  ADD KEY `FKd2my8b022mrsdyol6yrquuk4g` (`product_id`),
  ADD KEY `FK192624tms6uus1ngsdg21kjrx` (`user_id`);

--
-- Index pour la table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Index pour la table `order_products`
--
ALTER TABLE `order_products`
  ADD KEY `FKdxjduvg7991r4qja26fsckxv8` (`product_id`),
  ADD KEY `FKawxpt1ns1sr7al76nvjkv21of` (`order_id`);

--
-- Index pour la table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKna3v9f8s7ucnj16tylrs822qj` (`token`),
  ADD KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `desired_products`
--
ALTER TABLE `desired_products`
  ADD CONSTRAINT `FK192624tms6uus1ngsdg21kjrx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKd2my8b022mrsdyol6yrquuk4g` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Contraintes pour la table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `order_products`
--
ALTER TABLE `order_products`
  ADD CONSTRAINT `FKawxpt1ns1sr7al76nvjkv21of` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKdxjduvg7991r4qja26fsckxv8` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Contraintes pour la table `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
