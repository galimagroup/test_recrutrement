# Projet_Alten_SpringBoot_Angular

# Backend API - Projet Alten E-commerce

Ce backend fournit une API RESTful pour une application E-commerce.

## Technologies Utilisées

- Spring Boot
- Angular
- Mysql
- JWT pour l'authentification
- bcryptjs pour le hachage des mots de passe
- CORS pour la gestion des requêtes cross-origin

## Configuration

1. Installer les dépendances :

```bash
mvn clean install
```

## Points d'API

### Authentification

- `POST /api/auth/account` - Inscription d'un nouvel utilisateur
- `POST /api/auth/token` - Connexion utilisateur et génération token

### Produits

- `GET /api/products` - Récupérer tous les produits
- `GET /api/products/{productId}` - Récupérer un produit spécifique
- `POST /api/products` - Créer un nouveau produit (Admin)
- `PATCH /api/products/{productId}` - Mettre à jour un produit (Admin)
- `DELETE /api/products/{productId}` - Supprimer un produit (Admin)

### Panier

- `GET /api/cart` - Voir le panier
- `POST /api/cart` - Ajouter un produit au panier
- `DELETE /api/cart/:id` - Retirer un produit du panier

### Liste de Souhaits

- `GET /api/wishlist` - Voir la liste de souhaits
- `POST /api/wishlist` - Ajouter à la liste de souhaits
- `DELETE /api/wishlist/{productId}` - Retirer de la liste de souhaits

## Sécurité

- L'authentification est gérée via JWT (JSON Web Tokens)
- Les routes protégées nécessitent un token valide
- Les routes admin sont restreintes à l'utilisateur avec l'email 'admin@admin.com'
- Les restrictions se font avec de l'AOP (Aspect-Oriented Programming)
- L'annotation '@Authorization' est créée pour gérer les permissions de l'utilisateur
- Les mots de passe sont hashés avec bcryptjs

## Démarrage

Pour lancer le serveur en mode développement :

```bash
- mvn spring-boot:run
```

## Documentation

- La documentation complete de L'API est disponible sur `http://localhost:9595/api/swagger-ui/index.html`
- Une collection Postman est disponible dans le fichier `Documentation.json` pour tester toutes les routes de l'API.

# Frontend - Projet Alten E-commerce

Application frontend Angular pour l'e-commerce Alten, offrant une interface utilisateur moderne et responsive pour la gestion des produits, du panier et de la liste de souhaits.

## Technologies Utilisées

- Angular 18
- PrimeNG 17.18.0 (Bibliothèque de composants UI)
- PrimeFlex 3.3.1 (Système de grille CSS flexible)

## Prérequis

- Node.js (version LTS recommandée)
- npm (gestionnaire de paquets Node.js)
- Angular CLI (version 18.0.3)

## Installation

1. Installer les dépendances :

```bash
npm install
```

2. Démarrer le serveur de développement :

```bash
ng serve --open
```
