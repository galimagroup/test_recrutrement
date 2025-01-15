# Frontend - Projet Alten E-commerce

Application frontend Angular pour l'e-commerce Alten, offrant une interface utilisateur moderne et responsive pour la gestion des produits, du panier et de la liste de souhaits.

## Technologies Utilisées

- Angular 18
- PrimeNG 17.18.0 (Bibliothèque de composants UI)


## Fonctionnalités Principales

- Authentification (Login/Register)
- Catalogue de produits avec recherche et filtres
- Gestion du panier d'achat
- Liste de souhaits
- Interface administrateur pour la gestion des produits

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





