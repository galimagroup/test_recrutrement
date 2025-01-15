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
- `PUT /api/cart/update` - Mettre à jour la quantité
- `DELETE /api/cart/remove` - Retirer un produit du panier

### Liste de Souhaits
- `GET /api/wishlist` - Voir la liste de souhaits
- `POST /api/wishlist/add` - Ajouter à la liste de souhaits
- `DELETE /api/wishlist/remove` - Retirer de la liste de souhaits

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
