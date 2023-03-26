ALTER TABLE IF EXISTS parent_assist.users
    ADD COLUMN enabled boolean;

-- Table: parent_assist.verification_token

-- DROP TABLE IF EXISTS parent_assist.verification_token;

CREATE TABLE IF NOT EXISTS parent_assist.verification_token
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    expiry_date timestamp(6) without time zone,
    token character varying(255) COLLATE pg_catalog."default",
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT verification_token_pkey PRIMARY KEY (id),
    CONSTRAINT fk3asw9wnv76uxu3kr1ekq4i1ld FOREIGN KEY (user_id)
        REFERENCES parent_assist.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS parent_assist.verification_token
    OWNER to parent_assist;