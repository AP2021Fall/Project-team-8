--
-- Table structure for table `participant`
--

DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant`
(
    `user_id`         int(10) UNSIGNED NOT NULL,
    `board_id`        int(10) UNSIGNED NOT NULL,
    `is_suspend`      tinyint(1)       NOT NULL DEFAULT '0',
    `participated_at` timestamp        NOT NULL,
    KEY `user_id` (`user_id`),
    KEY `board_id` (`board_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
