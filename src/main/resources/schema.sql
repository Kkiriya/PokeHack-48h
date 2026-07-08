-- Run this script in psql using the following command:
-- psql -U postgres -d postgres -f path/to/schema.sql

DROP DATABASE IF EXISTS PokeHacks;

CREATE DATABASE PokeHacks;

DROP TYPE IF EXISTS MoveTarget;
DROP TYPE IF EXISTS MoveDmgClass;

DROP TABLE IF EXISTS ability;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS type;
DROP TABLE IF EXISTS move;
DROP TABLE IF EXISTS pokemonMoves;
DROP TABLE IF EXISTS pokemonTypes;

-- ENUMS --
-- would be cool to dynamically generate the enums w/ a script later on

CREATE TYPE MoveDmgClass AS ENUM (
    'STATUS',
    'PHYSICAL',
    'SPECIAL'
    );

CREATE TYPE MoveTarget AS ENUM (
    'SPECIFIC_MOVE',
    'SELECTED_POKEMON_ME_FIRST',
    'ALLY',
    'USER_FIELD',
    'OPPONENTS_FIELD',
    'USER',
    'RANDOM_OPPONENT',
    'ALL_OTHER_POKEMON',
    'ENTIRE_FIELD',
    'USER_AND_ALLIES',
    'ALL_POKEMON',
    'ALL_ALLIES',
    'FAINTING_POKEMON'
    );

-- TABLES --
CREATE TABLE ability (
                         id INT PRIMARY KEY NOT NULL,
                         effect_entries TEXT,
                         flavor_text_entries TEXT,
                         name TEXT NOT NULL
);

CREATE TABLE pokemon (
    id INT PRIMARY KEY NOT NULL,
    ability_id int NOT NULL,
    base_experience int NOT NULL,
    cries TEXT, -- contains the cries sound url
    height FLOAT NOT NULL ,
    name TEXT NOT NULL,
    species TEXT NOT NULL,
    sprites TEXT, -- contains the sprite img url
    stats jsonb NOT NULL, -- { "hp": 20, "attack": 4, ... }
    weight FLOAT NOT NULL,

    CONSTRAINT fk_ability
                     FOREIGN KEY (ability_id)
                     REFERENCES ability(id)
);

CREATE TABLE type (
                      id INT PRIMARY KEY NOT NULL,
                      name TEXT NOT NULL,
                      double_damage_from jsonb NOT NULL, -- { "grass": true, "water": false, ... }
                      double_damage_to jsonb NOT NULL,
                      half_damage_from jsonb NOT NULL,
                      half_damage_to jsonb NOT NULL,
                      no_damage_from jsonb NOT NULL,
                      no_damage_to jsonb NOT NULL,
                      move_damage_class MoveDmgClass NOT NULL,
                      sprites TEXT NOT NULL -- contains the sprite img url
);

CREATE TABLE move (
    id INT PRIMARY KEY NOT NULL,
    accuracy INT NOT NULL,
    damage_class MoveDmgClass NOT NULL,
    effect_chance TEXT,
    effect_change TEXT,
    effect_entries TEXT,
    flavor_text_entries TEXT,
    learned_by_pokemon jsonb NOT NULL, -- { "1" : { "pokemon": "bulbasaur"}... }
    name TEXT NOT NULL,
    power INT NOT NULL,
    pp INT NOT NULL,
    priority INT NOT NULL,
    stat_changes jsonb, -- { "hp": 5 // represents the stat change, "attack": -5, ... }
    target MoveTarget NOT NULL,
    typeId int NOT NULL,

    CONSTRAINT fk_type
                  FOREIGN KEY (typeId)
                  REFERENCES type(id)
);

-- JUNCTION TABLES --

CREATE TABLE pokemonMoves (
                              id INT PRIMARY KEY NOT NULL,
                              pokemonId INT NOT NULL,
                              moveId INT NOT NULL,

                              CONSTRAINT fK_pokemon_moves
                                  FOREIGN KEY (pokemonId)
                                      REFERENCES pokemon(id),

                              CONSTRAINT fk_move
                                  FOREIGN KEY (moveId)
                                      REFERENCES move(id)
);

CREATE TABLE pokemonTypes (
                              id INT PRIMARY KEY NOT NULL,
                              pokemonId INT NOT NULL,
                              typeId INT NOT NULL,

                              CONSTRAINT fk_pokemon_types
                                  FOREIGN KEY (pokemonId)
                                      REFERENCES pokemon(id),

                              CONSTRAINT  fk_move_types
                                  FOREIGN KEY (typeId)
                                      REFERENCES type(id)
);

