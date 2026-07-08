DROP TABLE IF EXISTS ability;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS type;
DROP TABLE IF EXISTS move;
DROP TABLE IF EXISTS pokemon_moves;
DROP TABLE IF EXISTS pokemon_types;
DROP TABLE IF EXISTS pokemon_abilities;
DROP TABLE IF EXISTS type_effectiveness;

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
    weight          DOUBLE PRECISION NOT NULL
);

CREATE TABLE type
(
    id      INT PRIMARY KEY NOT NULL,
    name    TEXT            NOT NULL UNIQUE,
    sprites TEXT -- contains the sprite img url
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

CREATE TABLE pokemon_abilities
(
    pokemon_id INT  NOT NULL,
    ability_id INT  NOT NULL,
    is_hidden  bool NOT NULL DEFAULT false,

    PRIMARY KEY (pokemon_id, ability_id),

    CONSTRAINT fk_pokemon_abilities_pokemon
        FOREIGN KEY (pokemon_id)
            REFERENCES pokemon (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_pokemon_abilities_ability
        FOREIGN KEY (ability_id)
            REFERENCES ability (id)
            ON DELETE CASCADE
);

CREATE TABLE type_effectiveness
(
    attacking_type_id INT NOT NULL,
    defending_type_id INT NOT NULL,
    multiplier        NUMERIC(2, 1),

    PRIMARY KEY (attacking_type_id, defending_type_id),

    FOREIGN KEY (attacking_type_id)
        REFERENCES type (id),

    FOREIGN KEY (defending_type_id)
        REFERENCES type (id)
);

-- Inserts for table whose data is very stable

-- types
INSERT INTO type (id, name)
VALUES (1, 'normal'),
       (2, 'fighting'),
       (3, 'flying'),
       (4, 'poison'),
       (5, 'ground'),
       (6, 'rock'),
       (7, 'bug'),
       (8, 'ghost'),
       (9, 'steel'),
       (10, 'fire'),
       (11, 'water'),
       (12, 'grass'),
       (13, 'electric'),
       (14, 'psychic'),
       (15, 'ice'),
       (16, 'dragon'),
       (17, 'dark'),
       (18, 'fairy')

ON CONFLICT (id)
    DO UPDATE SET name = EXCLUDED.name;

-- type effectiveness
INSERT INTO type_effectiveness (attacking_type_id, defending_type_id, multiplier)
VALUES
-- Normal
(1, 6, 0.5),   -- rock
(1, 9, 0.5),   -- steel
(1, 8, 0.0),   -- ghost

-- Fighting
(2, 1, 2.0),   -- normal
(2, 6, 2.0),   -- rock
(2, 9, 2.0),   -- steel
(2, 15, 2.0),  -- ice
(2, 17, 2.0),  -- dark

(2, 3, 0.5),   -- flying
(2, 4, 0.5),   -- poison
(2, 7, 0.5),   -- bug
(2, 14, 0.5),  -- psychic
(2, 18, 0.5),  -- fairy

(2, 8, 0.0),   -- ghost

-- Flying
(3, 2, 2.0),   -- fighting
(3, 7, 2.0),   -- bug
(3, 12, 2.0),  -- grass

(3, 6, 0.5),   -- rock
(3, 9, 0.5),   -- steel
(3, 13, 0.5),  -- electric

-- Poison
(4, 12, 2.0),  -- grass
(4, 18, 2.0),  -- fairy

(4, 4, 0.5),   -- poison
(4, 5, 0.5),   -- ground
(4, 6, 0.5),   -- rock
(4, 8, 0.5),   -- ghost

(4, 9, 0.0),   -- steel

-- Ground
(5, 10, 2.0),  -- fire
(5, 13, 2.0),  -- electric
(5, 4, 2.0),   -- poison
(5, 6, 2.0),   -- rock
(5, 9, 2.0),   -- steel

(5, 12, 0.5),  -- grass
(5, 7, 0.5),   -- bug

(5, 3, 0.0),   -- flying

-- Rock
(6, 10, 2.0),  -- fire
(6, 15, 2.0),  -- ice
(6, 3, 2.0),   -- flying
(6, 7, 2.0),   -- bug

(6, 2, 0.5),   -- fighting
(6, 5, 0.5),   -- ground
(6, 9, 0.5),   -- steel

-- Bug
(7, 12, 2.0),  -- grass
(7, 14, 2.0),  -- psychic
(7, 17, 2.0),  -- dark

(7, 10, 0.5),  -- fire
(7, 2, 0.5),   -- fighting
(7, 4, 0.5),   -- poison
(7, 3, 0.5),   -- flying
(7, 8, 0.5),   -- ghost
(7, 9, 0.5),   -- steel
(7, 18, 0.5),  -- fairy

-- Ghost
(8, 14, 2.0),  -- psychic
(8, 8, 2.0),   -- ghost

(8, 17, 0.5),  -- dark

(8, 1, 0.0),   -- normal

-- Steel
(9, 15, 2.0),  -- ice
(9, 6, 2.0),   -- rock
(9, 18, 2.0),  -- fairy

(9, 10, 0.5),  -- fire
(9, 11, 0.5),  -- water
(9, 13, 0.5),  -- electric

-- Fire
(10, 12, 2.0), -- grass
(10, 15, 2.0), -- ice
(10, 7, 2.0),  -- bug
(10, 9, 2.0),  -- steel

(10, 10, 0.5), -- fire
(10, 11, 0.5), -- water
(10, 6, 0.5),  -- rock
(10, 16, 0.5), -- dragon

-- Water
(11, 10, 2.0), -- fire
(11, 5, 2.0),  -- ground
(11, 6, 2.0),  -- rock

(11, 11, 0.5), -- water
(11, 12, 0.5), -- grass
(11, 16, 0.5), -- dragon

-- Grass
(12, 11, 2.0), -- water
(12, 5, 2.0),  -- ground
(12, 6, 2.0),  -- rock

(12, 10, 0.5), -- fire
(12, 12, 0.5), -- grass
(12, 4, 0.5),  -- poison
(12, 3, 0.5),  -- flying
(12, 7, 0.5),  -- bug
(12, 16, 0.5), -- dragon
(12, 9, 0.5),  -- steel

-- Electric
(13, 11, 2.0), -- water
(13, 3, 2.0),  -- flying

(13, 12, 0.5), -- grass
(13, 13, 0.5), -- electric
(13, 16, 0.5), -- dragon

(13, 5, 0.0),  -- ground

-- Psychic
(14, 2, 2.0),  -- fighting
(14, 4, 2.0),  -- poison

(14, 14, 0.5), -- psychic
(14, 9, 0.5),  -- steel

(14, 17, 0.0), -- dark

-- Ice
(15, 12, 2.0), -- grass
(15, 5, 2.0),  -- ground
(15, 3, 2.0),  -- flying
(15, 16, 2.0), -- dragon

(15, 10, 0.5), -- fire
(15, 11, 0.5), -- water
(15, 15, 0.5), -- ice
(15, 9, 0.5),  -- steel

-- Dragon
(16, 16, 2.0), -- dragon

(16, 9, 0.5),  -- steel

(16, 18, 0.0), -- fairy

-- Dark
(17, 14, 2.0), -- psychic
(17, 8, 2.0),  -- ghost

(17, 2, 0.5),  -- fighting
(17, 17, 0.5), -- dark
(17, 18, 0.5), -- fairy

-- Fairy
(18, 2, 2.0),  -- fighting
(18, 16, 2.0), -- dragon
(18, 17, 2.0), -- dark

(18, 10, 0.5), -- fire
(18, 4, 0.5),  -- poison
(18, 9, 0.5); -- steel
