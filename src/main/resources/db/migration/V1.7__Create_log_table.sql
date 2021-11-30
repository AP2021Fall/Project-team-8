--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE IF NOT EXISTS `log`
(
    `user_id`   int(10) UNSIGNED NOT NULL,
    `log_in_at` timestamp        NOT NULL,
    KEY `user_id` (`user_id`
        )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;