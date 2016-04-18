-- -----------------------------------------------------
-- Table `eleavedb`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `role_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_user_role_role_name` (`role_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`privilege`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `privilege_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)) 
ENGINE=InnoDB 
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`user_role_privilege`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`user_role_privilege` (
  `user_role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_role_id`,`privilege_id`),
  KEY `fk_user_role_privilege_privilege_id` (`privilege_id`),
  CONSTRAINT `fk_user_role_privilege_user_role_id`
    FOREIGN KEY (`user_role_id`) 
    REFERENCES `user_role` (`id`),
  CONSTRAINT `fk_user_role_privilege_privilege_id`
    FOREIGN KEY (`privilege_id`) 
    REFERENCES `privilege` (`id`))
  ENGINE=InnoDB 
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE=InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`user_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`user_user_role` (
  `user_id` bigint(20) NOT NULL,
  `user_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`user_role_id`),
  KEY `fk_user_user_role_user_role_id` (`user_role_id`),
  CONSTRAINT `fk_user_user_role_user_id` 
    FOREIGN KEY (`user_id`) 
    REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_user_role_user_role_id` 
    FOREIGN KEY (`user_role_id`) 
    REFERENCES `user_role` (`id`))
  ENGINE=InnoDB 
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
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_user_id` (`user_id` ASC),
  CONSTRAINT `fk_employee_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `eleavedb`.`user` (`id`))
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
  INDEX `fk_annual_balance_leave_employee_id` (`employee_id` ASC),
  INDEX `fk_annual_balance_leave_leave_type_id` (`leave_type_id` ASC),
  CONSTRAINT `fk_annual_balance_leave_employee_id`
    FOREIGN KEY (`employee_id`)
    REFERENCES `eleavedb`.`employee` (`id`),
  CONSTRAINT `fk_annual_balance_leave_leave_type_id`
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
  `employee_id` BIGINT(20) NULL DEFAULT NULL,
  `approver_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_approver_approver_id` (`approver_id` ASC),
  CONSTRAINT `fk_approver_approver_id`
    FOREIGN KEY (`approver_id`)
    REFERENCES `eleavedb`.`employee` (`id`),
  CONSTRAINT `fk_approver_employee_id`
    FOREIGN KEY (`employee_id`)
    REFERENCES `eleavedb`.`employee` (`id`))
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
  INDEX `fk_taken_leave_annual_balance_leave_id` (`annual_balance_leave_id` ASC),
  INDEX `fk_taken_leave_approver_id` (`approver_id` ASC),
  CONSTRAINT `fk_taken_leave_annual_balance_leave_id`
    FOREIGN KEY (`annual_balance_leave_id`)
    REFERENCES `eleavedb`.`annual_balance_leave` (`id`),
  CONSTRAINT `fk_taken_leave_approver_id`
    FOREIGN KEY (`approver_id`)
    REFERENCES `eleavedb`.`employee` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `eleavedb`.`holiday`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`holiday` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `year` INT(11) NOT NULL,
  `movable` boolean NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE=InnoDB
  DEFAULT CHARACTER SET = utf8;