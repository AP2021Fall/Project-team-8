--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment`
(
    `task_id` int(10) UNSIGNED                NOT NULL,
    `from`    int(10) UNSIGNED                NOT NULL,
    `text`    text COLLATE utf8mb4_unicode_ci NOT NULL,
    `send_at` timestamp                       NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
