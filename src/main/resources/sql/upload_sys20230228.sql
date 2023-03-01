INSERT INTO `menu` VALUES (117, '之家-高质数据上传', '/upload/qczj/hq/list', 'hq', 2, NULL, NULL, 'zj', 1, 1, 1);

DROP TABLE IF EXISTS `qczj_hq`;
CREATE TABLE `qczj_hq` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `mobile` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `countyid` int DEFAULT NULL,
  `brandid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `seriesid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `specid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `firstregtime` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `platenum` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `mileage` decimal(6,2) DEFAULT NULL,
  `appid` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `cclid` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;