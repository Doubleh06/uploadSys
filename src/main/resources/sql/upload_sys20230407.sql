
INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (118, '天天拍车', '#', 'tt', 0, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (119, '天天拍车-上传', '/upload/ttpc/list', 'ttpc', 1, NULL, NULL, 'tt', 1, 1, 1);

DROP TABLE IF EXISTS `sign_up`;
CREATE TABLE `sign_up` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `brand` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '0:success 1:fail',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `message` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `response_id` int DEFAULT NULL,
  `invite` int DEFAULT NULL COMMENT '0:success 1:fail',
  `detection` int DEFAULT NULL COMMENT '0:success 1:fail',
  `auction` int DEFAULT NULL COMMENT '0:success 1:fail',
  `deal` int DEFAULT NULL COMMENT '0:success 1:fail',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;