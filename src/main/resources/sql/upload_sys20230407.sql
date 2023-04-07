
INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (118, '天天拍车', '#', 'tt', 0, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (119, '天天拍车-上传', '/upload/ttpc/list', 'ttpc', 1, NULL, NULL, 'tt', 1, 1, 1);

DROP TABLE IF EXISTS `sign_up`;
CREATE TABLE `sign_up` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `brand` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `family` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `source` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `utm_source` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `license` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `status` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;