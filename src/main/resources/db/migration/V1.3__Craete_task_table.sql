--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
CREATE TABLE IF NOT EXISTS `task`
(
    `id`          int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `board_id`    int(10) UNSIGNED                                              NOT NULL,
    `list_id`     int(10) UNSIGNED                                              NOT NULL,
    `title`       varchar(109) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `priority`    int(10)                                                       NOT NULL,
    `status`      enum ('in-progress','failed','done')                          NOT NULL DEFAULT 'in-progress',
    `created_at`  timestamp                                                     NOT NULL,
    `deadline`    timestamp                                                     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `board_id` (`board_id`),
    KEY `list_id` (`list_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
