create table GAME
(
    ID identity primary key,
    GAME_ID varchar(50) unique not null,
    ADMIN_TOKEN varchar(50) not null,
    STARTED_AT timestamp not null,
    GAME_STATUS tinyint not null,
    CURRENT_SEASON tinyint not null,
    HOURGLASSES_REVEALED tinyint not null,

    UNIQUE KEY game_id_UNIQUE (GAME_ID) 
);

create index GAME_GAME_ID_INDEX on GAME (GAME_ID);

create table PLAYER
(
    ID identity primary key,
    GAME_ID varchar(50) references GAME(GAME_ID),
    PLAYER_ID varchar(50) not null,
    PLAYER_NAME varchar(100) not null,
    READY boolean not null
);

create index PLAYER_GAME_ID_PLAYER_ID_INDEX on PLAYER (GAME_ID, PLAYER_ID);

create table SCORE
(
    ID identity primary key,
    GAME_ID varchar(50) references GAME(GAME_ID),
    PLAYER_ID varchar(50) references PLAYER(PLAYER_ID),
    SPRING_MISSION1 int,
    SPRING_MISSION2 int,
    SPRING_GOLD int,
    SPRING_MONSTERS int,
    SUMMER_MISSION1 int,
    SUMMER_MISSION2 int,
    SUMMER_GOLD int,
    SUMMER_MONSTERS int,
    FALL_MISSION1 int,
    FALL_MISSION2 int,
    FALL_GOLD int,
    FALL_MONSTERS int,
    WINTER_MISSION1 int,
    WINTER_MISSION2 int,
    WINTER_GOLD int,
    WINTER_MONSTERS int
);

create index SCORE_GAME_ID_PLAYER_ID_INDEX on SCORE (GAME_ID, PLAYER_ID);

create table PLAYED_CARD
(
    ID identity primary key,
    GAME_ID varchar(50) references GAME(GAME_ID),
    CARD_ORDER int not null,
    CARD_ID int not null,
    WITH_RUINES boolean not null,
    PLAYED_IN_SEASON tinyint not null
);

create index PLAYEDCARD_GAME_ID_INDEX on PLAYED_CARD (GAME_ID);