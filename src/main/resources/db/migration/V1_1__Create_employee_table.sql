DROP TABLE IF EXISTS `annual_balance_leave`;
CREATE TABLE `annual_balance_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `leave_days_allowed` int(11) DEFAULT NULL,
  `leave_days_remaining` int(11) DEFAULT NULL,
  `validity_date` datetime DEFAULT NULL,
  `year` int(11) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `leave_type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4x52ddkq9gt29f9nishl12ox1` (`employee_id`),
  KEY `FK_hqqp34xjvf3i0xpoaw1o4m60i` (`leave_type_id`),
  CONSTRAINT `FK_4x52ddkq9gt29f9nishl12ox1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_hqqp34xjvf3i0xpoaw1o4m60i` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `approver`;
CREATE TABLE `approver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `employee_role_id` bigint(20) NOT NULL,
  `approver_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hxplmgqxqjulyc4x400bq7kl5` (`employee_role_id`),
  KEY `FK_e4hxwmn9lpwludv46ex9oht8r` (`approver_id`),
  CONSTRAINT `FK_e4hxwmn9lpwludv46ex9oht8r` FOREIGN KEY (`approver_id`) REFERENCES `approver` (`id`),
  CONSTRAINT `FK_hxplmgqxqjulyc4x400bq7kl5` FOREIGN KEY (`employee_role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `leave_status`;
CREATE TABLE `leave_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `status_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `leave_type`;
CREATE TABLE `leave_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `default_days_allowed` int(11) DEFAULT NULL,
  `leave_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `role_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `taken_leave`;
CREATE TABLE `taken_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `leave_days_taken` int(11) DEFAULT NULL,
  `leave_from` datetime DEFAULT NULL,
  `leave_to` datetime DEFAULT NULL,
  `annual_balance_leave_id` bigint(20) NOT NULL,
  `approver_id` bigint(20) DEFAULT NULL,
  `leave_status_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_erg5ky2sjucno1n3itpif1ug4` (`annual_balance_leave_id`),
  KEY `FK_mo575f7rd22fqy5fihqqtgyv` (`approver_id`),
  KEY `FK_4dutjriblpo4dwwcrexdd8y4e` (`leave_status_id`),
  CONSTRAINT `FK_4dutjriblpo4dwwcrexdd8y4e` FOREIGN KEY (`leave_status_id`) REFERENCES `leave_status` (`id`),
  CONSTRAINT `FK_erg5ky2sjucno1n3itpif1ug4` FOREIGN KEY (`annual_balance_leave_id`) REFERENCES `annual_balance_leave` (`id`),
  CONSTRAINT `FK_mo575f7rd22fqy5fihqqtgyv` FOREIGN KEY (`approver_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
