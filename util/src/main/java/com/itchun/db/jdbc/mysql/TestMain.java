package com.itchun.db.jdbc.mysql;

import java.sql.*;
import java.util.*;

public class TestMain {

    public static Connection getSqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false"
                , "root", "123456");
    }

    public static void main(String[] args) throws Exception {
        Connection conn = getSqlConnection();
        for (int i = 0; i < 1000; i++) {
            List<Map<String, Object>> list = new ArrayList<>();
            initi(i, list);
            insertbatch(conn, list, "test");
        }
    }

    public static void initi(int j, List<Map<String, Object>> list) {
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", j + "-" + i + "");
            map.put("name", j + "-" + "xiaoming-" + i);
            map.put("sex", j + "-" + "nan-" + i);
            list.add(map);
        }
    }

    public static void insertbatch(Connection conn, List<Map<String, Object>> datas, String tableName) throws Exception {
        if (datas == null || datas.size() == 0) return;

        // 遍历
        Set<String> keySet = datas.get(0).keySet();
        List<String> keyList = new ArrayList<>();
        String sql = "insert into " + tableName + " (";
        for (String name : keySet) {
            sql += name + ",";
            keyList.add(name);
        }
        sql = sql.substring(0, sql.length() - 1) + ") values(";
        for (String name : keyList) {
            sql += "?,";
        }
        sql = sql.substring(0, sql.length() - 1) + ");";
        conn.setAutoCommit(false);
        PreparedStatement pre = conn.prepareStatement(sql);
        for (Map<String, Object> map : datas) {
            for (int i = 1; i <= keySet.size(); i++) {
                String key = keyList.get(i - 1);
                Object value = map.get(key);
                pre.setObject(i, value == null ? "" : value.toString().trim().toLowerCase());
            }
            pre.addBatch();
        }
        pre.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        close(null, pre, null);
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
