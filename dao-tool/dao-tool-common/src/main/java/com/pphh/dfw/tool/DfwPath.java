package com.pphh.dfw.tool;

/**
 * Please add description here.
 *
 * @author huangyinhuang
 * @date 2019/5/5
 */
public class DfwPath {

    public static String getBasePath() throws Exception {
        String basePath = null;
        if (System.getProperty("os.name").startsWith("Windows")) {
            basePath = "c:/tmp/";
        } else if (System.getProperty("os.name").startsWith("Linux") ||
                System.getProperty("os.name").startsWith("Mac")) {
            basePath = "/tmp/";
        } else {
            throw new Exception("failed to decide current OS platform.");
        }
        return basePath;
    }

}
