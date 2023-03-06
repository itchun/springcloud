package com.itchun.dbAutomatic.dm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// 达梦 版本
// 下划线改驼峰
// 数据库代码生成 配合 flyway
// V1.1 配合 @ParamValidator 注解版
public class Shmec_age06_data_platformV2 {

    protected static String root_path = "C:\\工作\\数据之家\\项目档案\\[学前教育]数据中台\\";
    protected static String db_excel_path = root_path + "项目\\数据库表结构\\数据对接\\通用数据接口--v1.1.xlsx";
    protected static String java_path = root_path + "代码\\shmec_age06_data_platform\\src\\main\\";
    protected static String java_home_prefix = "com/datahome";
    protected static String java_home_path = java_path + "java/" + java_home_prefix + "/";
    protected static String java_model_prefix = "api";
    protected static String java_entity_path = java_home_path + "entity/" + java_model_prefix;
    protected static String java_mapper_path = java_home_path + "mapper/" + java_model_prefix;
    protected static String java_group_path = java_home_path + "group/" + java_model_prefix;
    protected static String java_mapper_xml_path = java_path + "resources/mapper/" + java_model_prefix;
    protected static String java_service_path = java_home_path + "service/" + java_model_prefix;
    protected static String java_service_impl_path = java_home_path + "service/" + java_model_prefix + "/impl";
    protected static String sql_version_path = java_path + "resources/db_migration";
    protected static String sql_db_name = "data_centre";
    protected static String sql_version_name = "V0.2.3__init_table";
    protected static boolean file_ifexsits_cover = false;
    protected static boolean field_is_underline = true;
    protected static String[] sheetname_include = {};
    protected static String[] sheetname_excluded = {};
    protected static String pagckage_import_root = java_home_prefix.replace("/", ".");
    protected static String pagckage_import_entity = pagckage_import_root + "." + java_entity_path.replace(java_home_path, "").replace("/", ".");
    protected static String pagckage_import_mapper = pagckage_import_root + "." + java_mapper_path.replace(java_home_path, "").replace("/", ".");
    protected static String pagckage_import_group = pagckage_import_root + "." + java_group_path.replace(java_home_path, "").replace("/", ".");
    protected static String pagckage_import_service = pagckage_import_root + "." + java_service_path.replace(java_home_path, "").replace("/", ".");
    protected static String pagckage_import_service_impl = pagckage_import_root + "." + java_service_impl_path.replace(java_home_path, "").replace("/", ".");
    protected static String package_entity_prefix = "package " + pagckage_import_entity + ";\n\nimport com.baomidou.mybatisplus.annotation.IdType;\nimport com.baomidou.mybatisplus.annotation.TableId;\nimport com.baomidou.mybatisplus.annotation.TableName;\nimport com.datahome.annotation.ParamValidator;\nimport " + pagckage_import_entity.replace(java_model_prefix, "") + "BaseEntity;\nimport com.datahome.util.RegexpUtil;\nimport lombok.Data;\nimport lombok.EqualsAndHashCode;\n\nimport java.util.Date;\n";
    protected static String package_mapper_prefix = "package " + pagckage_import_mapper + ";\nimport com.baomidou.mybatisplus.core.mapper.BaseMapper;\nimport org.springframework.stereotype.Repository;\n";
    protected static String package_group_prefix = "package " + pagckage_import_group + ";\n\nimport " + pagckage_import_group.replace(java_model_prefix, "") + "BaseGroup;\n";
    protected static String package_service_prefix = "package " + pagckage_import_service + ";";
    protected static String package_service_impl_prefix = "package " + pagckage_import_service_impl + ";";

    public static void main(String[] args) {

        // 表结构
        StringBuilder sb_sql = new StringBuilder("SET SCHEMA \"" + sql_db_name + "\";\n");
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
            String serviceName = getClazzName(table_name, "Service");
            String serviceImplName = getClazzName(table_name, "ServiceImpl");

            // 实体类
            StringBuilder sb_entity = new StringBuilder(package_entity_prefix + "\n//" + table_notes.replace("表", "对象") + "\n@Data\n@EqualsAndHashCode(callSuper = false)\n@TableName(value = \"" + table_name + "\")\npublic class " + clazzName + " extends BaseEntity{\n\n");
            StringBuilder sb_group = new StringBuilder(package_group_prefix + "\n//" + table_notes.replace("表", "分组") + "\npublic interface " + groupName + " extends BaseGroup {\n\n" +
                    "       interface save{}\n\n" +
                    "       interface find{}\n\n" +
                    "       interface finds{}\n\n" +
                    "       interface delete{}\n\n" +
                    "}\n");

            // 数据库
            StringBuilder sb_sql_field = new StringBuilder("DROP TABLE IF EXISTS \"" + sql_db_name + "\".\"" + table_name + "\";\nCREATE TABLE \"" + sql_db_name + "\".\"" + table_name + "\" (\n");
            StringBuilder sb_sql_index = new StringBuilder("");
            StringBuilder sb_sql_nextval = new StringBuilder("");
            StringBuilder sb_sql_comment = new StringBuilder("COMMENT ON TABLE \"" + sql_db_name + "\".\"" + table_name + "\" is '" + table_notes + "';\n");

            // 数据库交互mapper
            String mapperName = getClazzName(table_name, "Mapper");
            StringBuilder sb_mapper = new StringBuilder(package_mapper_prefix + "import " + pagckage_import_entity + "." + clazzName + ";\n\n// " + table_notes.replace("表", "mapper") + "\n@Repository\npublic interface " + mapperName + " extends BaseMapper<" + clazzName + "> {\n\n}");
            StringBuilder sb_mapper_xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\"" + pagckage_import_mapper + "." + mapperName + "\">\n    <resultMap id=\"" + clazzName + "Result\" type=\"" + pagckage_import_entity + "." + clazzName + "\">\n");

            // 遍历字段
            ArrayList fields = new ArrayList();
            String primary_key = "";
            for (List<Object> data : list) {
                if (data.size() < 3) break;
                String param = StringUtils.trimToEmpty((String) data.get(0));
                String field_name = field_is_underline ? param : getFieldName(param);
                String param_zh = StringUtils.trimToEmpty((String) data.get(1));
                if (StrUtil.isBlank(param) || StrUtil.isBlank(param_zh)) break;
                String db_type = StringUtils.trimToEmpty((String) data.get(2));
                String db_o = StringUtils.trimToEmpty((String) data.get(3));
                String db_index = StringUtils.trimToEmpty((String) data.get(4));
                String db_dic = StringUtils.trimToEmpty((String) data.get(5));
                String db_must = StringUtils.trimToEmpty((String) data.get(6));
                db_dic = StrUtil.isBlank(db_dic) ? "" : db_dic.replaceAll("\n", " ").replaceAll("\r", " ");

                // 实体类
                if ("主键".equals(param_zh)) {
                    if (db_type.toLowerCase().contains("int"))
                        sb_entity.append("    @TableId(value = \"" + param + "\", type = IdType.AUTO)\n");
                    else
                        sb_entity.append("    @TableId(value = \"" + param + "\", type = IdType.ASSIGN_UUID)\n");
                }
                String paramValidator = "@ParamValidator(";

                // 必填
                if ("必填".equals(db_must)) {
                    paramValidator += ",notNull = true";
                }

                // 符合必填
                if ("符合必填".equals(db_must)) {
                    String field = db_dic.split(",")[1].split("为")[0].trim();
                    String val = db_dic.split("\\[")[1].split("]")[0].trim();
                    paramValidator += ",conditionNotNull = true , conditionNotNullFieldValue = \"" + field + "_" + val + "\"";
                }

                // 格式
                if (StrUtil.isNotBlank(db_dic) && !"36位uuid".equals(db_dic)) {

                    // 日期正则
                    if ("yyyy-MM-dd HH:mm:ss.SSS".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.format_2";
                    }

                    // 日期正则
                    else if ("yyyy-MM-dd".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.format_1";
                    }

                    // 日期正则
                    else if ("yyyy-MM".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.format_3";
                    }

                    // 日期正则
                    else if ("yyyy".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.format_4";
                    }

                    // 手机号码
                    else if ("^1[0-9]{10}$".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.phone";
                    }

                    // 邮箱
                    else if ("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.eamil";
                    }

                    // uuid
                    else if ("^[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}$".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.uuid";
                    }

                    // 数据状态
                    else if ("^(删除|正常)$".equals(db_dic)) {
                        paramValidator += ",regexp = RegexpUtil.data_status";
                    }

                    // 字典
                    else if (db_dic.startsWith("dc_")) {
                        String dic = db_dic.split(",")[0].trim();
                        paramValidator += ",dic = \"" + dic + "\"";
                    }

                    // 长度限制
                    else if (db_dic.startsWith("长度限制")) {
                        String dic = db_dic.split("长度限制")[1].trim();
                        paramValidator += ",length_check = true , length_max = " + dic + "";
                    }

                    // int
                    else if (db_dic.startsWith("int")) {
                        paramValidator += ",long_check = true ";
                    }

                    // float
                    else if ("float".equals(db_dic)) {
                        paramValidator += ",float_check = true ";
                    }
                }
                paramValidator += ")";
                paramValidator = paramValidator.replace("(,", "(");
                if (!"@ParamValidator()".equals(paramValidator)) {
                    sb_entity.append("    " + paramValidator + "\n");
                }
                sb_entity.append("    private " + getFieldType(db_type) + " " + field_name + "; //" + param_zh + " " + db_dic + "\n\n");

                // sql
                if ("主键".equals(param_zh)) primary_key = param;
                if ("主键".equals(param_zh)) {
                    sb_sql_field.append("   " + param + " " + db_type.toUpperCase() + " " + db_o.toUpperCase() + " ,\n");
                } else {
                    sb_sql_field.append("   " + param + " " + db_type.toUpperCase() + " " + db_o.toUpperCase() + (db_type.toUpperCase().contains("VARCHAR") || db_type.toUpperCase().contains("TEXT") ? " DEFAULT ''" : "") + ",\n");
                }

                // 注释
                sb_sql_comment.append("COMMENT ON COLUMN \"" + table_name + "\".\"" + param + "\" IS '" + param_zh + " " + db_dic + "';\n");

                // 索引
                if (StrUtil.isNotBlank(db_index)) {
                }

                // xml
                sb_mapper_xml.append("        <result column=\"" + param + "\" property=\"" + field_name + "\"/> <!-- " + param_zh + " " + db_dic + " -->\n");
                fields.add(param);
            }
            sb_entity.append("}");
            sb_sql_field.append("   " + "primary key(\"" + primary_key + "\")\n");
            sb_sql_field.append(")\n");
            sb_sql_field.append("storage(initial 1, next 1, minextents 1, fillfactor 0);");
            sb_sql.append("-- " + table_notes + sb_sql_nextval.toString() + "\n" + sb_sql_field.toString() + "\n" + sb_sql_index.toString() + "\n" + sb_sql_comment + "\n");
            sb_mapper_xml.append("    </resultMap>\n\n");
            sb_mapper_xml.append("    <sql id=\"table_name\">\n        " + table_name + "\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"column\">\n        " + StringUtils.join(fields.toArray(), ",") + "\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"value\">\n        #{" + StringUtils.join(fields.toArray(), "},#{") + "}\n    </sql>\n");
            sb_mapper_xml.append("    <sql id=\"item_value\">\n        #{item." + StringUtils.join(fields.toArray(), "},#{item.") + "}\n    </sql>\n\n");
            sb_mapper_xml.append("</mapper>\n\n");

            // service
            StringBuilder service = new StringBuilder(package_service_prefix + "\n\nimport " + pagckage_import_entity + "." + clazzName + ";\n\n");
            service.append("public interface " + serviceName + " {\n\n");
            service.append("    Object save(" + clazzName + " " + getParamName(clazzName) + ");\n\n");
            service.append("    Object finds(" + clazzName + " " + getParamName(clazzName) + ");\n\n");
            service.append("    Object find(" + clazzName + " " + getParamName(clazzName) + ");\n\n");
            service.append("    Object delete(" + clazzName + " " + getParamName(clazzName) + ");\n\n");
            service.append("}");

            StringBuilder service_impl = new StringBuilder(package_service_impl_prefix + "\n\nimport " + pagckage_import_entity + "." + clazzName + ";\nimport " + pagckage_import_mapper + "." + mapperName + ";\n");
            service_impl.append("import " + pagckage_import_service + ".*;\n");
            service_impl.append("import org.slf4j.Logger;\n");
            service_impl.append("import org.slf4j.LoggerFactory;\n");
            service_impl.append("import org.springframework.beans.factory.annotation.Autowired;\n");
            service_impl.append("import org.springframework.stereotype.Service;\n");
            service_impl.append("import org.springframework.transaction.annotation.Transactional;\n\n");
            service_impl.append("@Service\n");
            service_impl.append("@Transactional\n");
            service_impl.append("public class " + serviceImplName + " implements " + serviceName + " {\n");
            service_impl.append("    private Logger logger = LoggerFactory.getLogger(this.getClass());\n\n");
            service_impl.append("    @Autowired\n");
            service_impl.append("    private " + mapperName + " " + getParamName(mapperName) + ";\n\n");
            service_impl.append("    @Transactional\n");
            service_impl.append("    public Object save(" + clazzName + " " + getParamName(clazzName) + ") {\n");
            service_impl.append("        return null;\n");
            service_impl.append("   }\n\n");
            service_impl.append("    public Object finds(" + clazzName + " " + getParamName(clazzName) + ") {\n");
            service_impl.append("        return null;\n");
            service_impl.append("   }\n\n");
            service_impl.append("    public Object find(" + clazzName + " " + getParamName(clazzName) + ") {\n");
            service_impl.append("        return null;\n");
            service_impl.append("   }\n\n");
            service_impl.append("    @Transactional\n");
            service_impl.append("    public Object delete(" + clazzName + " " + getParamName(clazzName) + ") {\n");
            service_impl.append("        return null;\n");
            service_impl.append("    }\n\n");
            service_impl.append("}");

//            System.out.println(sb_entity.toString());
            write(java_entity_path, clazzName + ".java", sb_entity.toString());
            write(java_mapper_path, mapperName + ".java", sb_mapper.toString());
            write(java_mapper_xml_path, mapperName + ".xml", sb_mapper_xml.toString());
            write(java_group_path, groupName + ".java", sb_group.toString());
            write(java_service_path, serviceName + ".java", service.toString());
            write(java_service_impl_path, serviceImplName + ".java", service_impl.toString());
        }

//            System.out.println(sb_sql.toString());
        write(sql_version_path, sql_version_name + ".sql", sb_sql.toString());
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

    private static String getParamName(String name) {
        String name_first = name.substring(0, 1);
        String name_next = name.substring(1);
        return name_first.toLowerCase() + name_next;
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
        if (db_type.toLowerCase().startsWith("txt")) type = "String";
        if (db_type.toLowerCase().startsWith("varchar")) type = "String";
        if (db_type.toLowerCase().startsWith("char")) type = "String";
        if (db_type.toLowerCase().startsWith("timestamp")) type = "Date";
        return type;
    }

    public static void write(String path, String fileName, String content) {
        if (!new File(path).exists()) new File(path).mkdirs();
        String absult_path = path + File.separator + fileName;
        File file = new File(absult_path);
        if (file.exists()) {
            if (file_ifexsits_cover)
                file.delete();
            else
                return;
        } else {
            new File(path).mkdirs();
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
