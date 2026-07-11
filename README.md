# Travail présenté par
Jean-Simon Cyr (Frontend), Émile Valade (Backend)

---

## Description du projet

Hackathon de 48 heures visant à développer une application en Java. L'application consiste à créer un Pokédex permettant de consulter les informations des Pokémon et de les ajouter à une liste de Pokémon capturés.

### Fonctionnalités du projet

- **Recherche** d'un Pokémon par son nom ou son ID via l'API
- **Carte d'information** affichée au clic : artwork, nom, ID, types et statistiques
- **CSS** utilisant les couleurs officielles des 18 types de Pokémon
- **Sauvegarde** automatique en base de données après chaque recherche (UPSERT)
- **Suppression** avec confirmation
- **Gestion des erreurs** (404, absence de connexion, base de données indisponible)
- **Liste** des Pokémon capturés affichée dans un `ListView`
- **Multithreading** : appels à l'API exécutés en arrière-plan (`Thread` + `Platform.runLater`)

### Outils utilisés

- Java 17+
- JavaFX 21
- PostgreSQL 15+
- Maven
- Jackson
- [PokeAPI](https://pokeapi.co/api/v2/)

---

## Screenshots

---

## Lien vidéo démo

https://vimeo.com/1208968950?share=copy&fl=sv&fe=ci

---

## Instructions d'installation

### Téléchargement du projet

- **1. Cloner le dépôt du projet**

```bash
git clone https://github.com/Kkiriya/PokeHack-48h.git && cd PokeHack-48h
```

### Initialisation du projet et configuration du fichier `.env`

- **1. Initialiser le projet et installer les dépendances**

```bash
mvn clean install
```

- **2. Créer le fichier `.env`**

```bash
cp .env.template .env
```

- **3. Remplir le fichier `.env` avec vos propres valeurs**

### Création de la base de données

- **1. Créer une base de données PostgreSQL nommée `PokeHacksDB` à l'aide de la méthode de votre choix**

- **2. Exécuter le script SQL suivant afin d'initialiser la base de données**

`src/main/resources/schema.sql`

### Lancer avec IntelliJ

1. Ouvrir le projet dans IntelliJ IDEA.
2. Vérifier que le JDK utilisé est Java 21 ou plus.
3. Lancer la classe `Main`.

### Lancer avec Maven

Si Maven est installé :

```bash
mvn javafx:run
```

---

## Liste des bonus implémentés

- [X] Compteur en bas
- [ ] Focus automatique
- [X] Cri du Pokémon - En quelque sorte
- [X] Bouton **Random**
- [ ] Filtre par type
- [ ] Tri intelligent
- [ ] Autocomplétion
- [ ] Favoris
- [X] Comparateur
- [X] Évolutions
- [ ] Mode sombre / clair
- [ ] Team Builder

---
