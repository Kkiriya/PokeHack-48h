-- Run this script in psql using the following command:
-- psql -U postgres -d postgres -f path/to/schema.sql

DROP DATABASE IF EXISTS PokeHacks;

CREATE DATABASE PokeHacks;

DROP TABLE IF EXISTS ability;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS type;
DROP TABLE IF EXISTS move;
DROP TABLE IF EXISTS pokemon_moves;
DROP TABLE IF EXISTS pokemon_types;

DROP TYPE IF EXISTS move_target;
DROP TYPE IF EXISTS move_dmg_class;

-- ENUMS --
-- would be cool to dynamically generate the enums w/ a script later on

CREATE TYPE move_dmg_class AS ENUM (
    'STATUS',
    'PHYSICAL',
    'SPECIAL'
    );

CREATE TYPE move_target AS ENUM (
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
CREATE TABLE ability
(
    id                  INT PRIMARY KEY NOT NULL,
    effect_entries      TEXT,
    flavor_text_entries TEXT,
    name                TEXT            NOT NULL UNIQUE
);

CREATE TABLE pokemon
(
    id              INT PRIMARY KEY  NOT NULL,
    ability_id      int,
    base_experience int              NOT NULL,
    cries           TEXT, -- contains the cries sound url
    height          DOUBLE PRECISION NOT NULL,
    name            TEXT             NOT NULL UNIQUE,
    species         TEXT             NOT NULL,
    sprites         TEXT, -- contains the sprite img url
    hp              INT              NOT NULL,
    attack          INT              NOT NULL,
    defense         INT              NOT NULL,
    special_attack  INT              NOT NULL,
    special_defense INT              NOT NUlL,
    speed           INT              NOT NULL,
    weight          DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_pokemon_ability
        FOREIGN KEY (ability_id)
            REFERENCES ability (id)
            ON DELETE SET NULL
);

CREATE TABLE type
(
    id                 INT PRIMARY KEY NOT NULL,
    name               TEXT            NOT NULL UNIQUE,
    double_damage_from TEXT            NOT NULL,
    double_damage_to   TEXT            NOT NULL,
    half_damage_from   TEXT            NOT NULL,
    half_damage_to     TEXT            NOT NULL,
    no_damage_from     TEXT            NOT NULL,
    no_damage_to       TEXT            NOT NULL,
    move_damage_class  move_dmg_class  NOT NULL,
    sprites            TEXT            NOT NULL -- contains the sprite img url
);

CREATE TABLE move
(
    id                  INT PRIMARY KEY NOT NULL,
    accuracy            INT             NOT NULL,
    damage_class        move_dmg_class  NOT NULL,
    effect_change       TEXT,
    effect_entries      TEXT,
    flavor_text_entries TEXT,
    name                TEXT            NOT NULL UNIQUE,
    power               INT             NOT NULL,
    pp                  INT             NOT NULL,
    priority            INT             NOT NULL,
    stat_changes        TEXT,
    target              move_target     NOT NULL,
    type_id             INT             NOT NULL,

    CONSTRAINT fk_type
        FOREIGN KEY (type_id)
            REFERENCES type (id)
            ON DELETE RESTRICT
);

-- JUNCTION TABLES --

CREATE TABLE pokemon_moves
(
    pokemon_id INT NOT NULL,
    move_id    INT NOT NULL,

    PRIMARY KEY (pokemon_id, move_id),

    CONSTRAINT fK_pokemon_moves
        FOREIGN KEY (pokemon_id)
            REFERENCES pokemon (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_move
        FOREIGN KEY (move_id)
            REFERENCES move (id)
            ON DELETE CASCADE
);

CREATE TABLE pokemon_types
(
    pokemon_id INT NOT NULL,
    type_id    INT NOT NULL,

    PRIMARY KEY (pokemon_id, type_id),

    CONSTRAINT fk_pokemon_types
        FOREIGN KEY (pokemon_id)
            REFERENCES pokemon (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_move_types
        FOREIGN KEY (type_id)
            REFERENCES type (id)
            ON DELETE CASCADE
);

