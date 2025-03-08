CREATE TABLE IF NOT EXISTS users (
   id               UUID                 NOT NULL CONSTRAINT users_pk PRIMARY KEY,
   display_name     VARCHAR              NOT NULL,
   user_name        VARCHAR              NOT NULL,
   password         VARCHAR              NOT NULL,
   roles            VARCHAR[]
);

CREATE INDEX IF NOT EXISTS users_user_name_index ON users(user_name);

CREATE TABLE IF NOT EXISTS message (
   id               UUID              NOT NULL CONSTRAINT message_pk PRIMARY KEY,
   sender           UUID              NOT NULL,
   room_id          UUID              NOT NULL,
   content          TEXT              NOT NULL,
   type             VARCHAR[]
);

CREATE INDEX IF NOT EXISTS message_sender_index ON message(sender);
CREATE INDEX IF NOT EXISTS message_room_id_index ON message(room_id);

CREATE TABLE IF NOT EXISTS message_group (
   id               UUID                 NOT NULL CONSTRAINT message_group_pk PRIMARY KEY,
   title            VARCHAR              NOT NULL,
   status           VARCHAR              NOT NULL,
   isSingle         BOOLEAN              NOT NULL
);