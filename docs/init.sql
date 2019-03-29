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


-- -----------------------------------------------------
-- Table `database1`.`full_type`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `database1`.`full_type_mysql`;

CREATE TABLE IF NOT EXISTS `database1`.`full_type_mysql` (
  `id` INT NOT NULL COMMENT '整型ID',
  `medium_int` MEDIUMINT NULL COMMENT 'medium整型',
  `integer_val` INTEGER NULL COMMENT '整型',
  `tiny_int` TINYINT NULL COMMENT 'tiny整型',
  `small_int` SMALLINT NULL COMMENT 'small整型',
  `big_int` BIGINT NULL COMMENT 'big整型',
  `float_val` FLOAT NULL COMMENT '浮点型',
  `double_val` DOUBLE NULL COMMENT '双精度浮点型',
  `numric_val` NUMERIC NULL COMMENT '数值型',
  `decimal_val` DECIMAL NULL COMMENT 'decimal',
  `char_val` CHAR NULL COMMENT '字符串',
  `varchar_45` VARCHAR(45) NULL COMMENT '变长字符串',
  `tiny_blob` TINYBLOB NULL COMMENT 'tiny_blob',
  `blob_val` BLOB NULL COMMENT 'blob',
  `long_blob` LONGBLOB NULL COMMENT 'long_blob',
  `tiny_text` TINYTEXT NULL COMMENT 'tiny_text',
  `text_val` TEXT NULL COMMENT 'text',
  `medium_text` MEDIUMTEXT NULL COMMENT 'medium_text',
  `long_text` LONGTEXT NULL COMMENT 'long_text',
  `date_val` DATE NULL COMMENT 'DATE',
  `year_val` YEAR NULL COMMENT 'YEAR',
  `time_val` TIME NULL COMMENT 'TIME',
  `datetime_val` DATETIME NULL COMMENT 'DATETIME',
  `timestamp_val` TIMESTAMP NULL COMMENT 'timestamp',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='数据库字段类型测试';

INSERT INTO `database1`.`full_type_mysql`
(`id`, `medium_int`, `integer_val`, `tiny_int`, `small_int`, `big_int`, `float_val`, `double_val`, `numric_val`, `decimal_val`,
 `char_val`, `varchar_45`, `tiny_blob`, `blob_val`, `long_blob`, `tiny_text`, `text_val`, `medium_text`,`long_text`,
 `date_val`, `year_val`, `time_val`, `datetime_val`, `timestamp_val`)
VALUES
  (1, 2, 3, 4, 5, 6, 7.0, 8.0, 9, 10, '1', '12','13', '14', '15', '16', '17', '18','19',
                                           '2019-01-01', '2019', '', '2019-01-01 00:00:00', NULL ),
  (2, 2, 3, 4, 5, 6, 7.0, 8.0, 9, 10, '1', '12','13', '14', '15', '16', '17', '18', '19',
                                           '2019-01-01', '2019', '', '2019-01-01 00:00:00', '2019-01-01 00:00:00');