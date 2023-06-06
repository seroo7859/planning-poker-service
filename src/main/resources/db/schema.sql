CREATE DATABASE IF NOT EXISTS `planningpoker`;
USE `planningpoker`;


CREATE TABLE IF NOT EXISTS `team`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(32) NOT NULL,
    CONSTRAINT `pk_user` PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `user`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `team_id`    BIGINT      NULL,
    `username`   VARCHAR(32) NOT NULL,
    `active`     BIT(1)      NOT NULL,
    `role`       VARCHAR(64) NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `pk_user` PRIMARY KEY (`id`),
    CONSTRAINT `fk_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
);


CREATE TABLE IF NOT EXISTS `team_member`
(
    `team_id`    BIGINT NOT NULL,
    `user_id`    BIGINT NOT NULL UNIQUE,
    `list_index` INT    NOT NULL DEFAULT 0,
    CONSTRAINT `pk_team_member` PRIMARY KEY (`team_id`, `list_index`),
    CONSTRAINT `fk_team_member_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_team_member_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `deck`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(32) NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `pk_deck` PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `card`
(
    `id`               BIGINT      NOT NULL AUTO_INCREMENT,
    `label`            VARCHAR(32) NOT NULL,
    `value`            VARCHAR(3)  NOT NULL,
    `front_text_color` VARCHAR(6)  NOT NULL,
    `back_text_color`  VARCHAR(6)  NOT NULL,
    `background_color` VARCHAR(6)  NOT NULL,
    `hover_color`      VARCHAR(6)  NOT NULL,
    `created_at`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `pk_card` PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `deck_card`
(
    `deck_id`    BIGINT NOT NULL,
    `card_id`    BIGINT NOT NULL UNIQUE,
    `list_index` INT    NOT NULL DEFAULT 0,
    CONSTRAINT `pk_deck_card` PRIMARY KEY (`deck_id`, `list_index`),
    CONSTRAINT `fk_deck` FOREIGN KEY (`deck_id`) REFERENCES `deck` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_card` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `session`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `public_id`  VARCHAR(36) NOT NULL UNIQUE,
    `team_id`    BIGINT      NULL,
    `deck_id`    BIGINT      NULL,
    `created_by` BIGINT      NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `pk_session` PRIMARY KEY (`id`),
    CONSTRAINT `fk_session_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_session_deck` FOREIGN KEY (`deck_id`) REFERENCES `deck` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_session_user` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
);
