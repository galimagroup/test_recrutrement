# Frontend - Projet Alten E-commerce

Application frontend Angular pour l'e-commerce Alten, offrant une interface utilisateur moderne et responsive pour la gestion des produits, du panier et de la liste de souhaits.

## Technologies Utilisées

- Angular 18
- PrimeNG 17.18.0 (Bibliothèque de composants UI)
- PrimeFlex 3.3.1 (Système de grille CSS flexible)
- Chart.js 3.3.2 (Pour les visualisations de données)
- Service Workers (Pour le support PWA)

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
npm start
```
L'application sera accessible à l'adresse `http://localhost:4200/`

## Structure du Projet

```
src/
├── app/
│   ├── components/     # Composants réutilisables
│   ├── pages/         # Pages principales de l'application
│   ├── services/      # Services pour la gestion des données
│   ├── guards/        # Guards pour la protection des routes
│   ├── models/        # Interfaces et types
│   └── shared/        # Composants, pipes et directives partagés
├── assets/           # Images, icônes et autres ressources statiques
├── environments/     # Configuration des environnements
└── styles/          # Styles globaux et variables
```

## Scripts Disponibles

- `npm start` : Démarre le serveur de développement
- `npm run build` : Compile l'application pour la production
- `npm run test` : Lance les tests unitaires
- `npm run lint` : Vérifie le code avec le linter

## Fonctionnalités Détaillées

### Authentification
- Système de login/register
- Protection des routes avec guards
- Gestion des tokens JWT

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
- Synchronisation avec le compte utilisateur

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

## Bonnes Pratiques

- Architecture modulaire
- Lazy loading des modules
- Gestion d'état centralisée
- Tests unitaires
- Composants réutilisables
- Code documenté

## Contribution

1. Forker le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Ouvrir une Pull Request

## Notes de Développement

- L'application utilise une architecture modulaire pour une meilleure maintenabilité
- Les composants sont conçus pour être réutilisables
- L'application suit les meilleures pratiques Angular
- Support PWA intégré pour une meilleure expérience utilisateur
