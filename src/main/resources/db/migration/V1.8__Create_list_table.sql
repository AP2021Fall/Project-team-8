--
-- Table structure for table `list`
--

DROP TABLE IF EXISTS `list`;
CREATE TABLE IF NOT EXISTS `list`
(
    `id`       int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `board_id` int(10) UNSIGNED                                              NOT NULL,
    `name`     varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `order`    int(5) UNSIGNED                                               NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    KEY `board_id` (`board_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
