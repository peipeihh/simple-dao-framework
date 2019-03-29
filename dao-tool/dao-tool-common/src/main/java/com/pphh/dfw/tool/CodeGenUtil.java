package com.pphh.dfw.tool;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
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
        table.put("author", "michael");
        table.put("date", "2019-03-29");
        table.put("table", "order");

        Writer out = new OutputStreamWriter(System.out);
        /* Get the template (uses cache internally) */
        Template tableTemplate = cfg.getTemplate("table.ftl");
        tableTemplate.process(table, out);

        System.out.println();

        Template entityTemplate = cfg.getTemplate("entity.ftl");
        entityTemplate.process(table, out);
    }

}
