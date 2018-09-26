# simple-dao-framework

## 项目介绍

本项目以尽量简洁的方式实现一个数据访问框架，包括dao/orm/sqlbuilder/jdbc/分库分表等核心技术栈的实现。

- dao/entity 抽象逻辑数据库，实现数据实体的基本增删改查、批量和事务操作
- sqlbuilder 动态的sqlbuilder，支持灵活的sql语句生成
- orm 兼容JPA，并为sqlbuilder提供字段定义，方便使用sqlbuilder构建
- jdbc 实现分库，对接数据库连接池。

整个架构，简单清晰、可扩展，框架易用性好。更多和业界其它知名数据库访问中间件架构的对比，见这里。

## 项目结构

整个项目目录结构如下，

```
- pom.xml 整个Maven项目的pom文件，这是项目的根目录
+ dao-core 核心抽象模块
  - pom.xml
  + src
    + main/java/com/pphh/dfw/core
      + ds 数据库访问源配置
      + table 表实体结构定义
      + IDao 数据访问对象
+ dao-framework 数据库访问框架
  - pom.xml
  + src
    + main/java/com/pphh/dfw
      + config 配置
      - DAO 数据访问对象
+ dao-test 测试项目
  - pom.xml
  + src
    + main/java
    + test/java 单元测试
```
