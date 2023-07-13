package com.itchun.数据库.dbAutomatic.postgresql;

// Postgresql 版本
// 下划线改驼峰
// 数据库代码生成 配合 flyway
// V1.0
public class Activity_Xuhui {
    protected static String root_path = "C:\\工作\\数据之家\\项目档案\\[学前教育]03早教活动\\";
    protected static String db_excel_path = root_path + "需求\\出生一件事\\表结构\\对接服务 - v1.2.xlsx";
    protected static String java_path = root_path + "项目代码\\shmec_age06_activity_xuhui\\src\\main\\";
    protected static String java_home_path = java_path + "java/com/datahome/activity/";
    protected static String java_entity_path = java_home_path + "entity/";
    protected static String java_mapper_path = java_home_path + "dao/";
    protected static String java_group_path = java_home_path + "group/";
    protected static String java_mapper_xml_path = java_home_path + "dao/";
    protected static String sql_version_path = java_path + "resources/db/migration/";
    protected static String sql_version_name = "V0.5.1__init_table";
    protected static boolean file_ifexsits_cover = false;
    protected static boolean field_is_underline = false; // true 保持下划线  false改成驼峰
    protected static boolean field_is_LOWER_UNDERSCORE = true; // 改成下划线
    protected static String[] sheetname_include = {"托育机构数据(app_tyjg)"};
    protected static String[] sheetname_excluded = {};
    protected static String pagckage_root = "com.datahome.activity";
    protected static String package_entity_prefix = "package " + pagckage_root + ".entity;\n\nimport com.baomidou.mybatisplus.annotation.IdType;\nimport com.baomidou.mybatisplus.annotation.TableId;\nimport com.baomidou.mybatisplus.annotation.TableName;\nimport lombok.Data;\nimport lombok.EqualsAndHashCode;\n\nimport java.util.Date;\n";
    protected static String package_mapper_prefix = "package " + pagckage_root + ".dao;\nimport com.baomidou.mybatisplus.core.mapper.BaseMapper;\nimport org.springframework.stereotype.Repository;\n";
    protected static String package_group_prefix = "package " + pagckage_root + ".group;\n";

    public static void main(String[] args) {

        Main.start(db_excel_path,
                sheetname_include,
                sheetname_excluded,
                package_entity_prefix,
                package_group_prefix,
                package_mapper_prefix,
                pagckage_root,
                field_is_underline,
                field_is_LOWER_UNDERSCORE,
                java_entity_path,
                java_mapper_path,
                java_mapper_xml_path,
                java_group_path,
                sql_version_path,
                sql_version_name,
                file_ifexsits_cover);
    }

}
