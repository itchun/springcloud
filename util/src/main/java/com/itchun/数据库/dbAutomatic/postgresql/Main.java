package com.itchun.数据库.dbAutomatic.postgresql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void start(String db_excel_path, String[] sheetname_include, String[] sheetname_excluded,
                             String package_entity_prefix, String package_group_prefix, String package_mapper_prefix,
                             String pagckage_root, boolean field_is_underline, boolean field_is_LOWER_UNDERSCORE,
                             String java_entity_path, String java_mapper_path,
                             String java_mapper_xml_path, String java_group_path, String sql_version_path, String sql_version_name,
                             boolean file_ifexsits_cover) {

        // 表结构
        StringBuilder sb_sql = new StringBuilder("");
        ExcelReader reader_index0 = new ExcelReader(new File(db_excel_path), 0);
        int sheet_number = reader_index0.getSheetCount();

        // 遍历excel中的sheet
        for (int i = 0; i < sheet_number; i++) {
            ExcelReader reader = new ExcelReader(new File(db_excel_path), i);
            Sheet sheet = reader.getSheet();
            String sheet_name = sheet.getSheetName();
            if (sheetname_include.length != 0 && !Arrays.asList(sheetname_include).contains(sheet_name)) continue;
            if (sheetname_excluded.length != 0 && Arrays.asList(sheetname_excluded).contains(sheet_name)) continue;
            String table_name = sheet_name.split("\\(")[1].replace(")", "");
            String table_notes = sheet_name.split("\\(")[0];
            List<List<Object>> list = reader.read(1);
            String clazzName = getClazzName(table_name, "Entity");
            String groupName = getClazzName(table_name, "Group");

            // 实体类
            StringBuilder sb_entity = new StringBuilder(package_entity_prefix + "\n//" + table_notes.replace("表", "对象") + "\n@Data\n@EqualsAndHashCode(callSuper = false)\n@TableName(value = \"" + table_name + "\")\npublic class " + clazzName + " extends BaseEntity{\n\n");
            StringBuilder sb_group = new StringBuilder(package_group_prefix + "\n//" + table_notes.replace("表", "分组") + "\npublic interface " + groupName + " extends BaseGroup {\n\n" +
                    "       interface save{}\n\n" +
                    "       interface find{}\n\n" +
                    "       interface finds{}\n\n" +
                    "       interface delete{}\n\n" +
                    "}\n");

            // 数据库
            StringBuilder sb_sql_field = new StringBuilder("DROP TABLE IF EXISTS " + table_name + ";\nCREATE TABLE " + table_name + " (\n");
            StringBuilder sb_sql_index = new StringBuilder("");
            StringBuilder sb_sql_nextval = new StringBuilder("");
            StringBuilder sb_sql_comment = new StringBuilder("");

            // 数据库交互mapper
            String mapperName = getClazzName(table_name, "Mapper");
            StringBuilder sb_mapper = new StringBuilder(package_mapper_prefix + "import " + pagckage_root + ".entity." + clazzName + ";\n\n// " + table_notes.replace("表", "mapper") + "\n@Repository\npublic interface " + mapperName + " extends BaseMapper<" + clazzName + "> {\n\n}");
            StringBuilder sb_mapper_xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\"" + pagckage_root + ".dao." + mapperName + "\">\n    <resultMap id=\"" + clazzName + "Result\" type=\"" + pagckage_root + ".entity." + clazzName + "\">\n");

            // 遍历字段
            ArrayList fields = new ArrayList();
            String primary_key = "";
            for (List<Object> data : list) {
                if (data.size() < 3) break;
                String param_sql = StringUtils.trimToEmpty((String) data.get(0));
                param_sql = field_is_LOWER_UNDERSCORE ? CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, param_sql) : param_sql;
                String field_name = field_is_underline ? param_sql : getFieldName(param_sql);
                String param_zh = StringUtils.trimToEmpty((String) data.get(1));
                if (StrUtil.isBlank(param_sql) || StrUtil.isBlank(param_zh)) break;
                String db_type = StringUtils.trimToEmpty((String) data.get(2));
                String db_o = StringUtils.trimToEmpty((String) data.get(3));
                String db_index = StringUtils.trimToEmpty((String) data.get(4));
                String db_dic = StringUtils.trimToEmpty((String) data.get(5));
                db_dic = StrUtil.isBlank(db_dic) ? "" : db_dic.replaceAll("\n", " ").replaceAll("\r", " ");

                // 实体类
                if ("主键".equals(param_zh)) {
                    if (db_type.contains("int"))
                        sb_entity.append("    @TableId(value = \"" + param_sql + "\", type = IdType.AUTO)\n");
                    else
                        sb_entity.append("    @TableId(value = \"" + param_sql + "\", type = IdType.ASSIGN_UUID)\n");
                }
                sb_entity.append("    private " + getFieldType(db_type) + " " + field_name + "; //" + param_zh + " " + db_dic + "\n\n");

                // sql
                if ("主键".equals(param_zh)) primary_key = param_sql;
                if ("主键".equals(param_zh) && db_type.contains("int")) {
                    sb_sql_field.append("   " + param_sql + " " + db_type.toUpperCase() + " " + db_o.toUpperCase() + " DEFAULT nextval('" + table_name + "_id_seq'::regclass),\n");
                    sb_sql_nextval.append("\nDROP SEQUENCE IF EXISTS " + table_name + "_id_seq;\nCREATE SEQUENCE " + table_name + "_id_seq\nSTART WITH 1\nINCREMENT BY 1\nNO MINVALUE\nNO MAXVALUE\nCACHE 1;\n");
                } else {
                    sb_sql_field.append("   " + param_sql + " " + db_type.toUpperCase() + " " + db_o.toUpperCase() + (db_type.toUpperCase().contains("VARCHAR") || db_type.toUpperCase().contains("TEXT") ? " DEFAULT ''" : "") + ",\n");
                }

                // 注释
                sb_sql_comment.append("COMMENT ON COLUMN \"" + table_name + "\".\"" + param_sql + "\" IS '" + param_zh + "';\n");

                // 索引
                if (StrUtil.isNotBlank(db_index)) {
                    sb_sql_index.append("CREATE INDEX " + getRandomString(28) + " ON " + table_name + " USING btree (" + param_sql + ");\n");
                }

                // xml
                sb_mapper_xml.append("        <result column=\"" + param_sql + "\" property=\"" + field_name + "\"/> <!-- " + param_zh + " " + db_dic + " -->\n");
                fields.add(param_sql);
            }
            sb_entity.append("}");
            sb_sql_field.append("   " + "CONSTRAINT " + table_name + "_pkey PRIMARY KEY ( " + primary_key + " )\n");
            sb_sql_field.append(");\n");
            sb_sql.append("-- " + table_notes + sb_sql_nextval.toString() + "\n" + sb_sql_field.toString() + "\n" + sb_sql_index.toString() + "\n" + sb_sql_comment + "\n");
            sb_mapper_xml.append("    </resultMap>\n\n");
            sb_mapper_xml.append("    <sql id=\"table_name\">\n        " + table_name + "\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"column\">\n        " + StringUtils.join(fields.toArray(), ",") + "\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"value\">\n        #{" + StringUtils.join(fields.toArray(), "},#{") + "}\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"item_value\">\n        #{item." + StringUtils.join(fields.toArray(), "},#{item.") + "}\n    </sql>\n\n");
            sb_mapper_xml.append("</mapper>\n\n");

//            System.out.println(sb_entity.toString());
            write(java_entity_path, clazzName + ".java", sb_entity.toString(), file_ifexsits_cover);
            write(java_mapper_path, mapperName + ".java", sb_mapper.toString(), file_ifexsits_cover);
            write(java_mapper_xml_path, mapperName + ".xml", sb_mapper_xml.toString(), file_ifexsits_cover);
            write(java_group_path, groupName + ".java", sb_group.toString(), file_ifexsits_cover);
        }

//            System.out.println(sb_sql.toString());
        write(sql_version_path, sql_version_name + ".sql", sb_sql.toString(), file_ifexsits_cover);
    }

    private static String getClazzName(String str, String prefx) {
        String[] names = str.split("_");
        String clazz_name = "";
        for (String name : names) {
            String name_first = name.substring(0, 1);
            String name_next = name.substring(1);
            clazz_name += name_first.toUpperCase() + name_next;
        }
        return clazz_name + prefx;
    }

    private static String getFieldName(String str) {
        String[] names = str.split("_");
        String clazz_name = "";
        for (String name : names) {
            String name_first = name.substring(0, 1);
            String name_next = name.substring(1);
            clazz_name += name_first.toUpperCase() + name_next;
        }
        String name_first = clazz_name.substring(0, 1);
        String name_next = clazz_name.substring(1);
        return name_first.toLowerCase() + name_next;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        String value = sb.toString();
        if (Character.isDigit(value.charAt(0)))
            return getRandomString(length);
        else
            return value;
    }

    public static String getFieldType(String db_type) {
        String type = "";
        if ("int4".equals(db_type.toLowerCase())) type = "Integer";
        if ("int8".equals(db_type.toLowerCase())) type = "Long";
        if ("integer".equals(db_type.toLowerCase())) type = "Integer";
        if (db_type.toLowerCase().startsWith("text")) type = "String";
        if (db_type.toLowerCase().startsWith("varchar")) type = "String";
        if (db_type.toLowerCase().startsWith("char")) type = "String";
        if (db_type.toLowerCase().startsWith("timestamp")) type = "Date";
        if (db_type.toLowerCase().startsWith("float")) type = "Float";
        return type;
    }

    public static void write(String path, String fileName, String content, boolean file_ifexsits_cover) {
        if (!new File(path).exists()) new File(path).mkdirs();
        String absult_path = path + File.separator + fileName;
        File file = new File(absult_path);
        if (file.exists()) {
            if (file_ifexsits_cover)
                file.delete();
            else
                return;
        }
        try {
            //将写入转化为流的形式
            BufferedWriter bw = new BufferedWriter(new FileWriter(absult_path));
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
