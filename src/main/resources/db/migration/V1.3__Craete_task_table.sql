--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
CREATE TABLE IF NOT EXISTS `task`
(
    `id`        int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `board_id`  int(10) UNSIGNED NOT NULL,
    `list_id`   int(10) UNSIGNED NOT NULL,
    `title`     int(11)          NOT NULL,
    `priority`  int(10)          NOT NULL,
    `create_at` timestamp        NOT NULL,
    `deadline`  timestamp        NOT NULL,
    PRIMARY KEY (`id`),
    KEY `board_id` (`board_id`
        ),
    KEY `list_id` (`list_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;