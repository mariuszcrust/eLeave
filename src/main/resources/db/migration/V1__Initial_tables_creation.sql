-- -----------------------------------------------------
-- Table `eleavedb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `role_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`employee` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `first_name` VARCHAR(255) NULL DEFAULT NULL,
  `last_name` VARCHAR(255) NULL DEFAULT NULL,
  `employee_role_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_hxplmgqxqjulyc4x400bq7kl5` (`employee_role_id` ASC),
  CONSTRAINT `FK_hxplmgqxqjulyc4x400bq7kl5`
    FOREIGN KEY (`employee_role_id`)
    REFERENCES `eleavedb`.`role` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`leave_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`leave_type` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `default_days_allowed` INT(11) NULL DEFAULT NULL,
  `leave_type_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eleavedb`.`annual_balance_leave`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`annual_balance_leave` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `leave_days_allowed` INT(11) NULL DEFAULT NULL,
  `leave_days_remaining` INT(11) NULL DEFAULT NULL,
  `validity_date` DATETIME NULL DEFAULT NULL,
  `year` INT(11) NOT NULL,
  `employee_id` BIGINT(20) NOT NULL,
  `leave_type_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_4x52ddkq9gt29f9nishl12ox1` (`employee_id` ASC),
  INDEX `FK_hqqp34xjvf3i0xpoaw1o4m60i` (`leave_type_id` ASC),
  CONSTRAINT `FK_4x52ddkq9gt29f9nishl12ox1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `eleavedb`.`employee` (`id`),
  CONSTRAINT `FK_hqqp34xjvf3i0xpoaw1o4m60i`
    FOREIGN KEY (`leave_type_id`)
    REFERENCES `eleavedb`.`leave_type` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eleavedb`.`approver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`approver` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `end_date` DATETIME NULL DEFAULT NULL,
  `start_date` DATETIME NULL DEFAULT NULL,
  `approver_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_1syaehhq6jcymk7wukamqw0y6` (`approver_id` ASC),
  CONSTRAINT `FK_1syaehhq6jcymk7wukamqw0y6`
    FOREIGN KEY (`approver_id`)
    REFERENCES `eleavedb`.`employee` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eleavedb`.`schema_version`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`schema_version` (
  `version_rank` INT(11) NOT NULL,
  `installed_rank` INT(11) NOT NULL,
  `version` VARCHAR(50) NOT NULL,
  `description` VARCHAR(200) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `script` VARCHAR(1000) NOT NULL,
  `checksum` INT(11) NULL DEFAULT NULL,
  `installed_by` VARCHAR(100) NOT NULL,
  `installed_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` INT(11) NOT NULL,
  `success` TINYINT(1) NOT NULL,
  PRIMARY KEY (`version`),
  INDEX `schema_version_vr_idx` (`version_rank` ASC),
  INDEX `schema_version_ir_idx` (`installed_rank` ASC),
  INDEX `schema_version_s_idx` (`success` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eleavedb`.`taken_leave`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`taken_leave` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `leave_days_taken` INT(11) NULL DEFAULT NULL,
  `leave_from` DATETIME NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `status_name` VARCHAR(255) NULL DEFAULT NULL,
  `leave_to` DATETIME NULL DEFAULT NULL,
  `annual_balance_leave_id` BIGINT(20) NOT NULL,
  `approver_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_erg5ky2sjucno1n3itpif1ug4` (`annual_balance_leave_id` ASC),
  INDEX `FK_mo575f7rd22fqy5fihqqtgyv` (`approver_id` ASC),
  CONSTRAINT `FK_erg5ky2sjucno1n3itpif1ug4`
    FOREIGN KEY (`annual_balance_leave_id`)
    REFERENCES `eleavedb`.`annual_balance_leave` (`id`),
  CONSTRAINT `FK_mo575f7rd22fqy5fihqqtgyv`
    FOREIGN KEY (`approver_id`)
    REFERENCES `eleavedb`.`employee` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;