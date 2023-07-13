package com.itchun.word.freemarker;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import freemarker.template.Template;

import java.util.HashMap;
import java.util.Map;

public class TestMain {

    public static void main(String[] args) {
        freemarker();
        poi();
    }

    public static void freemarker() {

        // 准备模板地址
        String temp_path = "test.ftl";
        Template template = FreemarkerUtil.getTemplate(temp_path, false);

        // 准备模板参数
        Map<String, Object> params = new HashMap<>();
        params.put("test", 99999);

        // 输出新的文件
        String out_path = "D:/aa.docx";
        FreemarkerUtil.createWordByTemplate(template, out_path, params);
    }

    public static void poi() {

        // 准备模板地址
        String temp_path = "C:\\Users\\王春\\Desktop\\test.ftl";
        XWPFTemplate template = PoiUtil.getTemplate(temp_path, Configure.createDefault(), false);

        // 准备模板参数
        Map<String, Object> params = new HashMap<>();
        params.put("test", 99999);

        // 输出新的文件
        String out_path = "D:/aa.doc";
        PoiUtil.writeByTemplate(template, out_path, params);
    }

}
