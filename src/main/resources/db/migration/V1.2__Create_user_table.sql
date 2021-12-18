--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    `id`       int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `username` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `is_admin` tinyint(1)                                                    NOT NULL DEFAULT '0',
    `is_ban`   tinyint(1)                                                    NOT NULL DEFAULT '0',
    `name`     varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `email`    varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `birthday` date                                                          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `is_admin`, `is_ban`, `name`, `email`, `birthday`)
VALUES (0, 'SystemAdmin', 'Administrator', 1, 0, 'Admin', 'jira@info', '1380-03-06');