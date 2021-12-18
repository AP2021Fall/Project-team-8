--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
CREATE TABLE IF NOT EXISTS `chat`
(
    `board_id` int(10) UNSIGNED NOT NULL,
    `send_by`  int(10) UNSIGNED NOT NULL,
    `send_at`  timestamp        NOT NULL,
    `text`     text             NOT NULL,
    KEY `board_id` (`board_id`),
    KEY `send_by` (`send_by`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
