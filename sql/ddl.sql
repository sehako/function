CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) NOT NULL,
    `auth_provider` VARCHAR(10) NOT NULL,
    `deleted` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_time` DATETIME(6) NOT NULL,
    `modified_time` DATETIME(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;