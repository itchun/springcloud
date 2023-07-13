package com.itchun.项目.入园系统.登记;

import com.itchun.数据库.jdbc.postgresql.JDBCUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// 入园登记系统登记日报导出
public class ExcelReport {

    public static void main(String[] args) {
        new ExcelReport().excelport();
    }

    public void excelport() {
        try {

            // 读取excel
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("C:\\工作\\数据之家\\项目档案\\[学前教育]入园登记系统\\五期\\项目\\登记系统\\日报\\报告模板\\程序模版\\日报访问情况_6月20日.xlsx"));

            // 数据库
            JDBCUtil jdbcUtil = new JDBCUtil();
            Connection connection = jdbcUtil.getSqlConnection("jdbc:postgresql://172.31.21.143/keirs_2023", "root", "datahome123", "org.postgresql.Driver");

            // 准备参数
            String start_day = "6月21日";
            while (!start_day.equals("6月26日")) {
                String befor_day = getDay("2023-0" + start_day.replace("月", "-").replace("日", ""), -1);
                String sheet_name_befor = befor_day.replace("2023-0", "").replace("-", "月") + "日";

                // sql
                String start_time = befor_day + " 17:00:00";
                String end_time = "2023-0" + (start_day).replace("月", "-").replace("日", "") + " 17:00:00";
                String pv_pc = "select count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3),(select count(*) from system_point_log where source = 'pc' and save_time <= 'end_time') as total from system_point_log where source = 'pc' and save_time between 'start_time' and 'end_time'".replace("start_time", start_time).replace("end_time", end_time);
                String pv_mobile = "select count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3),(select count(*) from system_point_log where source = 'mobile' and save_time <= 'end_time') as total from system_point_log where source = 'mobile' and save_time between 'start_time' and 'end_time'".replace("start_time", start_time).replace("end_time", end_time);
                String uv_pc = "select count(*) from (select aa from (select (case when user_id != '' then user_id else sessionid end) as aa from system_point_log where save_time between 'start_time' and 'end_time' and source = 'pc')tb1 group by aa)tb2".replace("start_time", start_time).replace("end_time", end_time);
                String uv_mobile = "select count(*) from (select aa from (select (case when user_id != '' then user_id else sessionid end) as aa from system_point_log where save_time between 'start_time' and 'end_time' and source = 'mobile')tb1 group by aa)tb2".replace("start_time", start_time).replace("end_time", end_time);
                String table = ("select ( case tba.resource  " +
                        "when '1' then '《上海市教育委员会关于做好2023年本市学前教育阶段适龄幼儿入园工作的通知》' " +
                        "when '2' then '《2023年上海市学前教育阶段适龄幼儿入园工作政策问答》' " +
                        "when '3' then '《一图读懂2023年上海市学前教育阶段适龄幼儿入园工作》' " +
                        "when '4' then '《本市各区幼儿入园工作咨询单位、电话一览表》' " +
                        "when '6' then '《告家长书》' " + "end " + ") as resource,tba.count,tba.round,tbb.count as total from ( " +
                        "select resource,count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3)  from system_point_log where flag = '2'  " +
                        "and save_time between 'start_time' and 'end_time' group by resource order by resource asc " +
                        ") tba left join (select resource,count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log),3)  from system_point_log where flag = '2' and save_time <= 'end_time' group by resource order by count desc)tbb on(tba.resource = tbb.resource) " +
                        "UNION all  " +
                        " " +
                        "select '【一图读懂】访问量' as resource,count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3),(select count(*) from system_point_log where flag = '3' and save_time <= 'end_time') as total from system_point_log where flag = '3' and save_time between 'start_time' and 'end_time' " +
                        "UNION all  " +
                        " " +
                        "select '【家长操作指引】访问量' as resource,count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3),(select count(*) from system_point_log where flag in('4','5') and save_time <= 'end_time') as total  from system_point_log where flag in('4','5') and save_time between 'start_time' and 'end_time' " +
                        "UNION all  " +
                        " " +
                        "select '【各区政策查询】访问量' as resource,count(*),round(cast(count(*) as numeric)/(select count(*) from system_point_log where save_time between 'start_time' and 'end_time'),3),(select count(*) from system_point_log where flag in('6','7') and save_time <= 'end_time') as total  from system_point_log where flag in('6','7') and save_time between 'start_time' and 'end_time'").replace("start_time", start_time).replace("end_time", end_time);

                // 查询结果
                List<LinkedHashMap<String, Object>> datas_pv_pc = jdbcUtil.findBySql(connection, pv_pc);
                List<LinkedHashMap<String, Object>> datas_pv_mobile = jdbcUtil.findBySql(connection, pv_mobile);
                List<LinkedHashMap<String, Object>> datas_uv_pc = jdbcUtil.findBySql(connection, uv_pc);
                List<LinkedHashMap<String, Object>> datas_uv_mobile = jdbcUtil.findBySql(connection, uv_mobile);
                List<LinkedHashMap<String, Object>> datas_table = jdbcUtil.findBySql(connection, table);

                // 创建新的一天
                Sheet sheet = workbook.cloneSheet(0, start_day);
                sheet.getRow(3).getCell(4).setCellValue(Integer.valueOf(datas_uv_pc.get(0).get("count").toString()));
                sheet.getRow(3).getCell(6).setCellValue(Integer.valueOf(datas_uv_mobile.get(0).get("count").toString()));
                sheet.getRow(3).getCell(2).setCellFormula("E4+G4");

                sheet.getRow(6).getCell(2).setCellValue(Integer.valueOf(datas_pv_mobile.get(0).get("count").toString()));
                sheet.getRow(6).getCell(3).setCellValue(formatDouble(datas_pv_mobile.get(0).get("round").toString()) + "%");
                sheet.getRow(6).getCell(4).setCellValue(Integer.valueOf(datas_pv_mobile.get(0).get("total").toString()));
                sheet.getRow(6).getCell(5).setCellFormula(String.format("'%s'!C7", sheet_name_befor));
                sheet.getRow(6).getCell(6).setCellFormula("C7-F7");

                sheet.getRow(7).getCell(2).setCellValue(Integer.valueOf(datas_pv_pc.get(0).get("count").toString()));
                sheet.getRow(7).getCell(3).setCellValue(formatDouble(datas_pv_pc.get(0).get("round").toString()) + "%");
                sheet.getRow(7).getCell(4).setCellValue(Integer.valueOf(datas_pv_pc.get(0).get("total").toString()));
                sheet.getRow(7).getCell(5).setCellFormula(String.format("'%s'!C8", sheet_name_befor));
                sheet.getRow(7).getCell(6).setCellFormula("C8-F8");

                sheet.getRow(2).getCell(2).setCellFormula("C7+C8");

                sheet.getRow(0).getCell(0).setCellFormula("\"系统运行正常，今日访问量：\"&C3&\"次，其中移动端\"&C7&\"次，PC端\"&C8&\"次。\"");

                int row = 10;
                for (int i = 0; i < 8; i++) {
                    sheet.getRow(row).getCell(1).setCellValue((String) datas_table.get(i).get("resource"));
                    sheet.getRow(row).getCell(2).setCellValue(Integer.valueOf(datas_table.get(i).get("count").toString()));
                    sheet.getRow(row).getCell(3).setCellValue(formatDouble(datas_table.get(i).get("round").toString()) + "%");
                    sheet.getRow(row++).getCell(4).setCellValue(Integer.valueOf(datas_table.get(i).get("total").toString()));
                }

                List<Map<String, String>> array = new ArrayList<>();
                int index = 0;
                array.add(init_map("1", "《上海市教育委员会关于…….适龄幼儿入园工作的通知》", "C11", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("2", "《2023年上海市学前教育阶段适龄幼儿入园工作政策问答》", "C12", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("3", "《一图读懂2023年上海市学前教育阶段适龄幼儿入园工作》", "C13", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("4", "《本市各区幼儿入园工作咨询单位、电话一览表》", "C14", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("5", "《告家长书》", "C15", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("6", "【一图读懂】访问量", "C16", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("7", "【家长操作指引】访问量", "C17", sheet_name_befor, datas_table.get(index++).get("count").toString()));
                array.add(init_map("8", "【各区政策查询】访问量", "C18", sheet_name_befor, datas_table.get(index++).get("count").toString()));

                Collections.sort(array, new Comparator<Map<String, String>>() {
                    // 升序排序
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        return Integer.valueOf(o1.get("index")).compareTo(Integer.valueOf(o2.get("index")));
                    }
                });
                row = 24;
                for (int i = 0; i < 8; i++) {
                    Map<String, String> map = array.get(i);
                    sheet.getRow(row).getCell(0).setCellValue(map.get("index"));
                    sheet.getRow(row).getCell(1).setCellValue(map.get("title"));
                    sheet.getRow(row).getCell(2).setCellFormula(map.get("now"));
                    sheet.getRow(row++).getCell(3).setCellFormula(map.get("befor"));
                }

                Collections.sort(array, new Comparator<Map<String, String>>() {
                    // 升序排序
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        return Integer.valueOf(o1.get("count")).compareTo(Integer.valueOf(o2.get("count")));
                    }
                });
                row = 47;
                for (int i = 0; i < 8; i++) {
                    Map<String, String> map = array.get(i);
                    sheet.getRow(row).getCell(0).setCellValue(map.get("index"));
                    sheet.getRow(row).getCell(1).setCellValue(map.get("title"));
                    sheet.getRow(row).getCell(2).setCellFormula(map.get("now"));
                    sheet.getRow(row++).getCell(3).setCellFormula(map.get("befor"));
                }

                // 准备生成word
                Map<String, Object> params = new HashMap<>();
                params.put("statistical_time_range", String.format("2023年0%s17:00-2023年0%s17:00", sheet_name_befor, start_day));
                params.put("summarize_info", String.format("今日访问量：%s次，其中移动端%s次，PC端%s次", Integer.parseInt(getCellValue(sheet, 6, 2)) + Integer.parseInt(getCellValue(sheet, 7, 2)), getCellValue(sheet, 6, 2), getCellValue(sheet, 7, 2)));
                params.put("pv_count", Integer.parseInt(getCellValue(sheet, 6, 2)) + Integer.parseInt(getCellValue(sheet, 7, 2)));
                params.put("uv_count", Integer.parseInt(getCellValue(sheet, 3, 4)) + Integer.parseInt(getCellValue(sheet, 3, 6)));
                params.put("uv_pc_count", getCellValue(sheet, 3, 4));
                params.put("uv_mobile_count", getCellValue(sheet, 3, 6));

                // 表格一
                Sheet sheet_befor = workbook.getSheet(sheet_name_befor);
                params.put("t_1_1", getCellValue(sheet, 6, 2));
                params.put("t_1_2", getCellValue(sheet, 6, 3));
                params.put("t_1_3", getCellValue(sheet_befor, 6, 2));
                params.put("t_1_4", Integer.valueOf(getCellValue(sheet, 6, 2)) - Integer.valueOf(getCellValue(sheet_befor, 6, 2)));
                params.put("t_1_5", getCellValue(sheet, 6, 4));
                params.put("t_2_1", getCellValue(sheet, 7, 2));
                params.put("t_2_2", getCellValue(sheet, 7, 3));
                params.put("t_2_3", getCellValue(sheet_befor, 7, 2));
                params.put("t_2_4", Integer.valueOf(getCellValue(sheet, 7, 2)) - Integer.valueOf(getCellValue(sheet_befor, 7, 2)));
                params.put("t_2_5", getCellValue(sheet, 7, 4));
                params.put("visit_info", String.format("移动端浏览量较昨日%s，页面PC端浏览量较昨日%s，但是移动端的访问占比仍是主要渠道", sheet.getRow(6).getCell(6).getNumericCellValue() > 0 ? "升高" : "较低", sheet.getRow(7).getCell(6).getNumericCellValue() > 0 ? "升高" : "较低"));

                // 表格二
                params.put("t2_1_1", getCellValue(sheet, 10, 2));
                params.put("t2_1_2", getCellValue(sheet, 10, 3));
                params.put("t2_1_3", getCellValue(sheet, 10, 4));
                params.put("t2_2_1", getCellValue(sheet, 11, 2));
                params.put("t2_2_2", getCellValue(sheet, 11, 3));
                params.put("t2_2_3", getCellValue(sheet, 11, 4));
                params.put("t2_3_1", getCellValue(sheet, 12, 2));
                params.put("t2_3_2", getCellValue(sheet, 12, 3));
                params.put("t2_3_3", getCellValue(sheet, 12, 4));
                params.put("t2_4_1", getCellValue(sheet, 13, 2));
                params.put("t2_4_2", getCellValue(sheet, 13, 3));
                params.put("t2_4_3", getCellValue(sheet, 13, 4));
                params.put("t2_5_1", getCellValue(sheet, 14, 2));
                params.put("t2_5_2", getCellValue(sheet, 14, 3));
                params.put("t2_5_3", getCellValue(sheet, 14, 4));
                params.put("t2_6_1", getCellValue(sheet, 15, 2));
                params.put("t2_6_2", getCellValue(sheet, 15, 3));
                params.put("t2_6_3", getCellValue(sheet, 15, 4));
                params.put("t2_7_1", getCellValue(sheet, 16, 2));
                params.put("t2_7_2", getCellValue(sheet, 16, 3));
                params.put("t2_7_3", getCellValue(sheet, 16, 4));
                params.put("t2_8_1", getCellValue(sheet, 17, 2));
                params.put("t2_8_2", getCellValue(sheet, 17, 3));
                params.put("t2_8_3", getCellValue(sheet, 17, 4));

                // 升序排序
                Collections.sort(array, new Comparator<Map<String, String>>() {

                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        return Integer.valueOf(o2.get("count")).compareTo(Integer.valueOf(o1.get("count")));
                    }
                });
                String index0 = array.get(0).get("title").replace("【", "").replace("】", "");
                String index1 = array.get(1).get("title").replace("【", "").replace("】", "");
                String index2 = array.get(2).get("title").replace("【", "").replace("】", "");
                params.put("module_info", String.format("今日家长们对%s有更多关注；相比其他，可以看出家长们对%s、%s关注度也更高", index0, index1, index2));
                WordReport.ry(params, "C:\\工作\\数据之家\\项目档案\\[学前教育]入园登记系统\\五期\\项目\\登记系统\\日报\\报告模板\\日报数据(图表未更新)-v1.2\\2023年上海市适龄幼儿入园信息登记系统-运营数据统计报告-20230" + start_day.replace("月", "").replace("日", "") + ".doc");

                System.out.println(String.format("%s日报生成成功", start_day));

                // 下一日
                start_day = getDay("2023-0" + start_day.replace("月", "-").replace("日", ""), 1).replace("2023-0", "").replace("-", "月") + "日";
            }

            // 输出excel
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\工作\\数据之家\\项目档案\\[学前教育]入园登记系统\\五期\\项目\\登记系统\\日报\\报告模板\\日报数据(图表未更新)-v1.2\\日报访问情况_" + start_day + ".xlsx");
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> init_map(String index, String title, String now, String sheet_name_befor, String count) {
        Map<String, String> map = new HashMap<>();
        map.put("index", index);
        map.put("title", title);
        map.put("now", now);
        map.put("befor", String.format("'%s'!%s", sheet_name_befor, now));
        map.put("count", count);
        return map;
    }

    public static String getDay(String dateString, Integer type) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, type);//1表示明天,-1表示昨天
        date = calendar.getTime(); //这个时间就是明天
        return formatter.format(date);
    }

    public static String getCellValue(Sheet sheet, int row, int cell) {
        String val;
        try {
            val = Double.valueOf(sheet.getRow(row).getCell(cell).getNumericCellValue()).toString();
        } catch (Exception e) {
            val = sheet.getRow(row).getCell(cell).getStringCellValue();
        }
        if (val.endsWith(".0")) val = val.replace(".0", "");
        if (val.contains(".")) {
            String val1 = val.split("\\.")[0];
            String val2 = val.split("\\.")[1];
            val2 = val2.length() > 4 ? val2.substring(0, 4) : val2;
            val = val1 + "." + val2;
        }
        return val;
    }

    public static double formatDouble(String val) {
        BigDecimal bd = new BigDecimal(val).setScale(3, RoundingMode.UP);
        return bd.multiply(new BigDecimal("100")).doubleValue();
    }

}
