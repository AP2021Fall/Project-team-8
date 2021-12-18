--
-- Table structure for table `old_pass`
--

DROP TABLE IF EXISTS `old_pass`;
CREATE TABLE IF NOT EXISTS `old_pass`
(
    `user_id`  int(10) UNSIGNED                                              NOT NULL,
    `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;