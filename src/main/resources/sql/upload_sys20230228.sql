INSERT INTO `menu` VALUES (117, '之家-高质数据上传', '/upload/qczj/hq/list', 'hq', 2, NULL, NULL, 'zj', 1, 1, 1);

DROP TABLE IF EXISTS `qczj_hq`;
CREATE TABLE `qczj_hq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `countyid` int(11) DEFAULT NULL,
  `brandid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `seriesid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `specid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `firstregtime` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `platenum` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `mileage` decimal(6,2) DEFAULT NULL,
  `appid` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `cclid` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '//0:成功 1：失败\n',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `distribute_status` int(1) DEFAULT NULL COMMENT '1：已分发 0:为分发',
  `appeal_status` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '1:申诉成功 0:未申诉或申诉失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;