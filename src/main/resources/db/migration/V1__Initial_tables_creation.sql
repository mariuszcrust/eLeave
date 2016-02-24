-- -----------------------------------------------------
-- Table `eleavedb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eleavedb`.`role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` BIGINT(20) NULL DEFAULT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `role_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_role_role_name` (`role_name` ASC))
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
  INDEX `fk_employee_employee_role_id` (`employee_role_id` ASC),
  CONSTRAINT `fk_employee_employee_role_id`
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
  PRIMARY KEY (`id`),
  INDEX `fk_approver_employee_id` (`employee_id` ASC),
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