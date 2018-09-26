-- -----------------------------------------------------
-- Schema `database0`
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `database0` DEFAULT CHARACTER SET utf8 ;
USE `database0` ;

-- -----------------------------------------------------
-- Table `database0`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database0`.`order` ;
DROP TABLE IF EXISTS `database0`.`order_item` ;

CREATE TABLE IF NOT EXISTS `database0`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

INSERT INTO `database0`.`order` (`name`, `city_id`, `country_id`) VALUES ('apple', '1', '1');
INSERT INTO `database0`.`order` (`name`, `city_id`, `country_id`) VALUES ('banana', '1', '1');

CREATE TABLE IF NOT EXISTS `database0`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '物品ID',
  `order_id` VARCHAR(45) NULL COMMENT '订单ID',
  `user_id` INT NULL COMMENT '用户',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单物品';

-- -----------------------------------------------------
-- Table `database0`.`order_0`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database0`.`order_0` ;

CREATE TABLE IF NOT EXISTS `database0`.`order_0` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


-- -----------------------------------------------------
-- Table `database0`.`order_1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database0`.`order_1` ;

CREATE TABLE IF NOT EXISTS `database0`.`order_1` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


-- -----------------------------------------------------
-- Table `database0`.`order_2`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database0`.`order_2` ;

CREATE TABLE IF NOT EXISTS `database0`.`order_2` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

-- -----------------------------------------------------
-- Schema `database1`
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `database1` DEFAULT CHARACTER SET utf8 ;
USE `database1` ;

-- -----------------------------------------------------
-- Table `database1`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database1`.`order` ;
DROP TABLE IF EXISTS `database1`.`order_item` ;

CREATE TABLE IF NOT EXISTS `database1`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


CREATE TABLE IF NOT EXISTS `database1`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '物品ID',
  `order_id` VARCHAR(45) NULL COMMENT '订单ID',
  `user_id` INT NULL COMMENT '用户',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单物品';

-- -----------------------------------------------------
-- Table `database1`.`order_0`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database1`.`order_0` ;

CREATE TABLE IF NOT EXISTS `database1`.`order_0` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


-- -----------------------------------------------------
-- Table `database1`.`order_1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database1`.`order_1` ;

CREATE TABLE IF NOT EXISTS `database1`.`order_1` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


-- -----------------------------------------------------
-- Table `database1`.`order_2`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `database1`.`order_2` ;

CREATE TABLE IF NOT EXISTS `database1`.`order_2` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `name` VARCHAR(45) NULL COMMENT '订单名字（描述）',
  `city_id` INT NULL COMMENT '城市编号',
  `country_id` INT NULL COMMENT '国际编号',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';