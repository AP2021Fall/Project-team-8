--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification`
(
    `to_id`   int(10) UNSIGNED NOT NULL,
    `from_id` int(10) UNSIGNED NOT NULL,
    `text`    text             NOT NULL,
    `send_at` timestamp        NOT NULL,
    KEY `to_id` (`to_id`),
    KEY `from_id` (`from_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;