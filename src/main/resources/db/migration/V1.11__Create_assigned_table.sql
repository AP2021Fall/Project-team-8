--
-- Table structure for table `assigned`
--

DROP TABLE IF EXISTS `assigned`;
CREATE TABLE IF NOT EXISTS `assigned`
(
    `user_id`     int(10) UNSIGNED NOT NULL,
    `task_id`     int(10) UNSIGNED NOT NULL,
    `assigned_at` timestamp        NOT NULL,
    KEY `user_id` (`user_id`),
    KEY `task_id` (`task_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
