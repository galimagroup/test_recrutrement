# Projet_Alten_Angular_Node
# Backend API - Projet Alten E-commerce

Ce backend fournit une API RESTful pour une application e-commerce, permettant la gestion des produits, des paniers, des listes de souhaits et l'authentification des utilisateurs.

## Technologies Utilisées

- Node.js
- Express.js
- MongoDB avec Mongoose (creation d'un cluster sur atlas)
- JWT pour l'authentification
- bcryptjs pour le hachage des mots de passe
- CORS pour la gestion des requêtes cross-origin

## Configuration

1. Installer les dépendances :
```bash
npm install
```

2. Configurer les variables d'environnement :
Créer un fichier `.env` à la racine du projet avec les variables suivantes :
```env
MONGODB_URI=votre_uri_mongodb
JWT_SECRET=Alten
PORT=3000 (ou autre port de votre choix)
```

## Structure du Projet

```
src/
├── controllers/      # Logique métier
├── middlewares/     # Middlewares d'authentification et d'autorisation
├── models/          # Modèles Mongoose
└── routes/          # Routes de l'API
```

## Points d'API

### Authentification
- `POST /api/auth/account` - Inscription d'un nouvel utilisateur
- `POST /api/auth/token` - Connexion utilisateur

### Produits
- `GET /api/products` - Récupérer tous les produits
- `GET /api/products/:id` - Récupérer un produit spécifique
- `POST /api/products` - Créer un nouveau produit (Admin)
- `PUT /api/products/:id` - Mettre à jour un produit (Admin)
- `DELETE /api/products/:id` - Supprimer un produit (Admin)

### Panier
- `GET /api/cart` - Voir le panier
- `POST /api/cart/add` - Ajouter un produit au panier
- `PUT /api/cart/:id` - Mettre à jour la quantité
- `DELETE /api/cart/:id` - Retirer un produit du panier

### Liste de Souhaits
- `GET /api/wishlist` - Voir la liste de souhaits
- `POST /api/wishlist/add` - Ajouter à la liste de souhaits
- `DELETE /api/wishlist/:id` - Retirer de la liste de souhaits

## Sécurité

- L'authentification est gérée via JWT (JSON Web Tokens)
- Les routes protégées nécessitent un token valide
- Les routes admin sont restreintes à l'utilisateur avec l'email 'admin@admin.com'
- Les mots de passe sont hashés avec bcryptjs

## Démarrage

Pour lancer le serveur en mode développement :
```bash
node server.js
```

## Collection Postman

Une collection Postman est disponible dans le fichier `Projet_Alten.postman_collection.json` pour tester toutes les routes de l'API.

## Gestion des Erreurs

L'API utilise des codes de statut HTTP standards :
- 200 : Succès
- 201 : Création réussie
- 400 : Erreur de requête
- 401 : Non authentifié
- 403 : Non autorisé
- 404 : Ressource non trouvée
- 500 : Erreur serveur

## Contribution

Pour contribuer au projet :
1. Forker le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Ouvrir une Pull Request



# Frontend - Projet Alten E-commerce

Application frontend Angular pour l'e-commerce Alten, offrant une interface utilisateur moderne et responsive pour la gestion des produits, du panier et de la liste de souhaits.

## Technologies Utilisées

- Angular 18
- PrimeNG 17.18.0 (Bibliothèque de composants UI)
- PrimeFlex 3.3.1 (Système de grille CSS flexible)


## Fonctionnalités Principales

- Authentification (Login/Register)
- Catalogue de produits avec recherche et filtres
- Gestion du panier d'achat
- Liste de souhaits
- Interface administrateur pour la gestion des produits
- Design responsive (mobile-first)
- Support PWA (Progressive Web App)

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
L'application sera accessible à l'adresse `http://localhost:4200/`

## Structure du Projet

```
src/
├── app/
│   ├── components/     # Composants réutilisables
│   ├── services/      # Services pour la gestion des données
│   ├── models/        # Interfaces et types
│   └── shared/        # Composants, pipes et directives partagés
├── assets/           # Images, icônes et autres ressources statiques
├── environments/     # Configuration des environnements
└── styles/          # Styles globaux et variables
```


## Fonctionnalités Détaillées

### Catalogue Produits
- Liste des produits avec pagination
- Filtres par catégorie
- Barre de recherche
- Vue détaillée des produits

### Panier
- Ajout/Suppression de produits
- Modification des quantités
- Calcul automatique du total
- Persistance du panier

### Liste de Souhaits
- Ajout/Suppression de produits

### Interface Administrateur
- Gestion CRUD des produits
- Interface de gestion dédiée
- Tableau de bord avec statistiques

## Style et Design

- Utilisation de PrimeNG pour les composants UI
- Thème personnalisé
- Design responsive
- Animations fluides
- Support des thèmes clair/sombre





