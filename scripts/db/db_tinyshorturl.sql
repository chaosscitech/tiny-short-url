# Create Database
# ------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS db_tinyshorturl DEFAULT CHARACTER SET = utf8mb4;



Use db_tinyshorturl;


# Dump of table tb_raw_url
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_raw_url`;

-- 原始URL表
CREATE TABLE `tb_raw_url` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `url` varchar(5000) NOT NULL DEFAULT '' COMMENT '原始URL',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `created_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT '' COMMENT '修改人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_url` (`url`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='原始URL表';
