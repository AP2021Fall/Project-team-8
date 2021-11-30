--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
CREATE TABLE IF NOT EXISTS `board`
(
    `id`        int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `leader_id` int(10) UNSIGNED                                              NOT NULL,
    `name`      varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_at` timestamp                                                     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `leader_id` (`leader_id`
        )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;