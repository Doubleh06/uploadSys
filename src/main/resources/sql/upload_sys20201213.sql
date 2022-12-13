INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (115, '人人车', '#', 'rrc', 0, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (116, '人人车-数据上传', '/upload/rrc/common/list', 'common', 0, NULL, NULL, 'rrc', 1, 1, 1);



DROP TABLE IF EXISTS `rrc`;
CREATE TABLE `rrc` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `mobile` int DEFAULT NULL COMMENT '电话',
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '城市',
  `brand` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '品牌',
  `series` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '车系',
  `model` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '车型',
  `kilometer` float(11,2) DEFAULT NULL COMMENT '公里数',
  `licensed_date_year` int DEFAULT NULL COMMENT '上牌时间',
  `is_operation` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否营运',
  `seat_number` int DEFAULT NULL COMMENT '几座',
  `is_accidented` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否事故车',
  `renrenche_info_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '返回id',
  `create_time` datetime DEFAULT NULL,
  `is_repeat` int DEFAULT NULL COMMENT '是否重复 0：重复1：不重复',
  `status` int DEFAULT NULL COMMENT '0：上传成功 1：上传失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;