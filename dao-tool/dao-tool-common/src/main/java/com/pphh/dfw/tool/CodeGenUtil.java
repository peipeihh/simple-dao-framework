package com.pphh.dfw.tool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/3/29
 */
public class CodeGenUtil {

    public void produce(String db, String table, String configDir) {

    }

    public static void main(String[] args) throws Exception {
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
        Map table = new HashMap();
        table.put("author", "pphh");
        table.put("table", "order");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        table.put("date", df.format(LocalDateTime.now()));

        List tableFields = new ArrayList();
        tableFields.add("id");
        tableFields.add("name");
        tableFields.add("city_id");
        tableFields.add("country_id");
        tableFields.add("update_time");
        table.put("table_fields", tableFields);

        table.put("table_fields_pk", "id");

        Map tableFieldsType = new HashMap();
        tableFieldsType.put("id", "Integer");
        tableFieldsType.put("name", "String");
        tableFieldsType.put("city_id", "Integer");
        tableFieldsType.put("country_id", "Integer");
        tableFieldsType.put("update_time", "Date");
        table.put("table_field_type_map", tableFieldsType);

        Writer out = new OutputStreamWriter(System.out);
        /* Get the template (uses cache internally) */
        Template tableTemplate = cfg.getTemplate("table.ftl");
        tableTemplate.process(table, out);

        System.out.println();

        Template entityTemplate = cfg.getTemplate("entity.ftl");
        entityTemplate.process(table, out);
    }

}
