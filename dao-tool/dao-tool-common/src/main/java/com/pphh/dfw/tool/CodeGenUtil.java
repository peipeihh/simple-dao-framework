package com.pphh.dfw.tool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码生成工具
 *
 * @author huangyinhuang
 * @date 2019/3/29
 */
public class CodeGenUtil {

    private final static Logger log = LoggerFactory.getLogger(CodeGenUtil.class);

    /**
     * 通过freemarker模板生成table entity/dao代码
     *
     * @param author          作者
     * @param table           表名
     * @param tableFields     表字段列表
     * @param tableFieldPks   表主键列表
     * @param tableFieldsType 表字段类型
     * @throws Exception
     */
    public static void produceBy(String author, String table, List tableFields, List tableFieldPks, Map tableFieldsType) throws Exception {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        String templateDirectory = CodeGenUtil.class.getClassLoader().getResource("./template").getPath();
        cfg.setDirectoryForTemplateLoading(new File(templateDirectory));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map dataModel = new HashMap();
        dataModel.put("author", author);
        String camelTableName = StringUtil.toUpperCaseFirstOne(StringUtil.underlineToCamel(table));
        dataModel.put("table", camelTableName);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        dataModel.put("date", df.format(LocalDateTime.now()));

        dataModel.put("table_fields", tableFields);

        if (tableFieldPks.size() >= 1) {
            dataModel.put("table_fields_pk", tableFieldPks.get(0));
        }

        dataModel.put("table_field_type_map", tableFieldsType);

        /* generate the class by template + data model */
        String directory = DfwPath.getBasePath();
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }

        Template tableTemplate = cfg.getTemplate("table.ftl");
        log.info("\r\n---- print the dao class ----\r\n");
        Writer consoleOutput = new OutputStreamWriter(System.out, "utf-8");
        tableTemplate.process(dataModel, consoleOutput);
        File tableClsFile = new File(String.format("%s%sTable.java", directory, camelTableName));
        Writer tableClsFileOut = new OutputStreamWriter(new FileOutputStream(tableClsFile), "utf-8");
        tableTemplate.process(dataModel, tableClsFileOut);
        tableClsFileOut.close();
        log.info("the dao class file has been saved into {}", tableClsFile.getAbsolutePath());


        log.info("\r\n---- print the entity class ----\r\n");
        Template entityTemplate = cfg.getTemplate("entity.ftl");
        entityTemplate.process(dataModel, consoleOutput);
        File entityClsFile = new File(String.format("%s%sEntity.java", directory, camelTableName));
        Writer entityClsFileOut = new OutputStreamWriter(new FileOutputStream(entityClsFile), "utf-8");
        entityTemplate.process(dataModel, entityClsFileOut);
        entityClsFileOut.close();
        log.info("the entity class file has been saved into {}", entityClsFile.getAbsolutePath());

    }

    public static void main(String[] args) throws Exception {
        List tableFields = new ArrayList();
        tableFields.add("id");
        tableFields.add("name");
        tableFields.add("city_id");
        tableFields.add("country_id");
        tableFields.add("update_time");

        List tableFieldPks = new ArrayList();
        tableFieldPks.add("id");

        Map<String, String> tableFieldsType = new HashMap();
        tableFieldsType.put("id", "Integer");
        tableFieldsType.put("name", "String");
        tableFieldsType.put("city_id", "Integer");
        tableFieldsType.put("country_id", "Integer");
        tableFieldsType.put("update_time", "Date");

        produceBy("pphh", "order", tableFields, tableFieldPks, tableFieldsType);
    }

}
