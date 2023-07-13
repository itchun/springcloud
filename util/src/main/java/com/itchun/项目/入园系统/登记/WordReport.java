package com.itchun.项目.入园系统.登记;

import com.itchun.word.freemarker.FreemarkerUtil;
import freemarker.template.Template;

import java.util.HashMap;
import java.util.Map;

public class WordReport {

    public static void main(String[] args) {
        test();
    }

    // 入院登记系统日报
    public static void ry(Map<String, Object> params, String out_path) {

        // 准备模板地址
        String temp_path = "ruyuan.ftl";
        Template template = FreemarkerUtil.getTemplate(temp_path, false);

        // 输出新的文件
        FreemarkerUtil.createWordByTemplate(template, out_path, params);
    }

    public static void test() {
        Map<String, Object> params = new HashMap<>();
        params.put("statistical_time_range", "2023年04月27日17:00-2023年04月28日17:00");
        params.put("summarize_info", "今日访问量：41704次，其中移动端32470次，PC端9234次");
        params.put("pv_count", "41704");
        params.put("uv_count", "13793");
        params.put("uv_pc_count", "4803");
        params.put("uv_mobile_count", "8990");

        // 表格一
        params.put("t_1_1", "32470");
        params.put("t_1_2", "77.90%");
        params.put("t_1_3", "45619");
        params.put("t_1_4", "-13149");
        params.put("t_1_5", "2333486");
        params.put("t_2_1", "9234");
        params.put("t_2_2", "22.10%");
        params.put("t_2_3", "21584");
        params.put("t_2_4", "-12350");
        params.put("t_2_5", "777524");
        params.put("visit_info", "移动端浏览量较昨日降低，页面PC端浏览量较昨日升高，但是移动端的访问占比仍是主要渠道");

        // 表格二
        params.put("t2_1_1", "788");
        params.put("t2_1_2", "2%");
        params.put("t2_1_3", "83449");
        params.put("t2_2_1", "752");
        params.put("t2_2_2", "1.80%");
        params.put("t2_2_3", "68040");
        params.put("t2_3_1", "430");
        params.put("t2_3_2", "1.00%");
        params.put("t2_3_3", "40572");
        params.put("t2_4_1", "528");
        params.put("t2_4_2", "1%");
        params.put("t2_4_3", "46264");
        params.put("t2_5_1", "445");
        params.put("t2_5_2", "1%");
        params.put("t2_5_3", "39718");
        params.put("t2_6_1", "1782");
        params.put("t2_6_2", "4.30%");
        params.put("t2_6_3", "123945");
        params.put("t2_7_1", "3937");
        params.put("t2_7_2", "9.40%");
        params.put("t2_7_3", "407099");
        params.put("t2_8_1", "4081");
        params.put("t2_8_2", "10%");
        params.put("t2_8_3", "232322");
        params.put("module_info", "今日家长们对各区政策查询有更多关注；相比其他，可以看出家长们对操作指引、一图读懂关注度也更高");
        ry(params, "D:/out.doc");
    }
}
