# Rapport PokéHack

Jean-Simon Cyr (Frontend), Émile Valade (Backend)

---

## Setup & Architecture

- Création du dépôt Git (Émile)
- Configuration de Maven + JavaFX (Jean-Simon)
- Configuration de JDBC (Jean-Simon)
- Configuration de Jackson (Jean-Simon)
- Diagramme de relation d'entité (Émile)
- Création de la base de données (Émile)
- Création des tables (Émile)
- Squelette MVC (Émile)

## Backend & Service API

- Création des POJO de chaque classe (Émile)
- Création des DAO de chaque classe (Émile)
- Développement du `PokemonApiService` (Émile)
- Tests unitaires du service (Jean-Simon)

## Vue JavaFX

- Développement complet de la vue (Jean-Simon)
- `BorderPane` (Jean-Simon)
- `VBox` (Jean-Simon)
- `GridPane` (Jean-Simon)

## Controller & Multithreading

- Développement du contrôleur (Jean-Simon)
- Utilisation de `new Thread` pour les appels à l'API (Jean-Simon)
- Utilisation de `Platform.runLater` (Jean-Simon)
- Gestion des erreurs (Jean-Simon)
- Feedback visuel (Jean-Simon)

## Design & CSS

- CSS des 18 types de Pokémon (Jean-Simon)
- Animations (Jean-Simon)
- Icônes (Jean-Simon)
- Layout responsive (Jean-Simon)

## Qualité du code

- Revue des *pull requests* (Émile & Jean-Simon)
- Tests du code (Émile & Jean-Simon)

## Présentation & Git

- Rédaction du README (Émile & Jean-Simon)
- Rédaction du rapport (Émile & Jean-Simon)

### Choix techniques

**Diagramme de relation de la base de données PokeHacks**

<img width="1131" height="901" alt="PokeHack-ED drawio" src="https://github.com/user-attachments/assets/9c6b685b-0571-4ea6-a49e-574f77abe32a" />

**Explication du diagramme en fonction de ce que retourne l'API**

L'API PokeAPI retourne une grande quantité de données, à la fois simples (`String`, `int`, `float`, etc.) et complexes (JSON). Afin de conserver le plus d'informations possible tout en respectant les principes de normalisation d'une base de données relationnelle, nous avons séparé les différents objets et leurs valeurs dans plusieurs tables.

En suivant cette logique, toute information que nous souhaitions conserver et qui pouvait être partagée entre plusieurs Pokémon a été placée dans une table distincte avec une table de jonction.

C'est pourquoi nous avons créé des tables de jonction entre `pokemon` et `move`, `pokemon` et `ability`, ainsi qu'entre `pokemon` et `type`. Toutes les autres relations peuvent être représentées à l'aide de clés étrangères.

### Difficultés rencontrées

**Émile :**

> À plusieurs reprises durant le développement du backend, j'ai dû revenir sur la base de données afin de modifier le type de certaines colonnes, puis répercuter ces changements dans les modèles et les DAO. Ce problème s'est accentué lorsque j'ai commencé à développer le `PokemonApiService`, puisque l'API ne retournait pas toujours les données dans le format attendu ou ne les structurait pas comme je l'avais prévu.
>
> Avec le recul, je pense qu'il aurait été préférable de prendre davantage de temps pour bien analyser les données retournées par l'API avant de concevoir la base de données. Cela aurait probablement permis d'éviter plusieurs modifications en cours de développement.

> Une autre difficulté a été de ne pas réussir à utiliser efficacement les `enum` que j'avais créés. À aucun moment ils ne m'ont semblé offrir un réel avantage par rapport au stockage direct des chaînes de caractères ou à l'utilisation de tables semi-statiques dans la base de données.

**Jean-Simon :**

>

### Ajouts avec plus de temps

**Émile :**

> Avec davantage de temps, je repenserais légèrement la structure de la base de données afin d'améliorer certaines relations. J'intégrerais également plus proprement les données statiques, comme les `type` ou les `move_damage_class`, sous forme d'`enum` lorsque cela est pertinent.

**Jean-Simon :**

>
