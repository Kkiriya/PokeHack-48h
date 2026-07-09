# Travail présenté par : 
Jean-Simon Cyr (Frontend) et Emile Valade (Backend)

---

## Description du projet
Hackathon de 48 heures pour développer une application avec le langage Java. L'application consiste à créer un
pokédex qui permet de consulter les informations des Pokemon et de les ajouter à une liste de capturés.

### Fonctionalite du projet
- **Recherche** d'un Pokemon par nom ou id via l'API
- **Carte info** affichee au clic: artwork, nom, id, types, stats
- **CSS** avec couleurs officielles des 18 types Pokemon
- **Sauvegarde** automatique en BD apres la recherche (UPSERT)
- **Suppression** avec confirmation
- **Gestion d'erreur** propre (404, pas de connexion, BD down)
- **Liste** des Pokemon capturee dans un ListView
- **Multi-threading:** appels API en arriere-plan (Thread + Platform.runLater)

### Outils utilisee 
- Java 17+
- JavaFX 21
- PostgreSQL 15+
- Maven
- Jackson
- [PokeAPI](https://pokeapi.co/api/v2/) 
---

## Screenshots

---

## Instruction de setup

### Telechargement du projet
- **1. Telecharger le repo du projet**
```bash
git clone https://github.com/Kkiriya/PokeHack-48h.git & cd PokeHack-48h
```

### initialisation du projet + configuration .env

- **1. initialiser le projet + installation des dependences**
```bash
mvn clean install
```

- **2. Creation du fichier .env**
```bash
cat .env.template >> .env
```

- **3. Remplisser le fichier .env avec vos propre valeur**

### Creation de la base de donnees

- **1. Creer une base de donnees postgreSQL avec la methode de votre choix nommer `PokeHacksDB`**

- **2. Executer le script sql suivant pour remplir la BD**
`src/main/resources/schema.sql`

### Lancer le projet 
```bash
```

---

## Liste des bonus implémentés
- [ ] Compteur en bas
- [ ] Focus automatique
- [ ] Cri du Pokemon
- [ ] Bouton Random
- [ ] Filtre par type
- [ ] Tri intelligent
- [ ] Autocomplete
- [ ] Favoris
- [ ] Comparateur
- [ ] Evolutions
- [ ] Dark / Light mode
- [ ] Team Builder
---
