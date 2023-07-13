package com.itchun.数据库.jdbc.postgresql;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class JDBCUtil {

    public Connection getSqlConnection(String url, String user, String password, String driver) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    public void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
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

    // 查询
    public List<LinkedHashMap<String, Object>> findBySql(Connection con, String sql) throws Exception {
        PreparedStatement pre = con.prepareStatement(sql);
        ResultSet resultSet = pre.executeQuery();
        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                Object value = resultSet.getObject(i);
                map.put(name, value == null ? "" : value.toString().trim());
            }
            list.add(map);
        }
        closeConnection(null, pre, resultSet);
        return list;
    }

    // 插入表数据
    public int insert(Connection con, LinkedHashMap<String, Object> map, String tableName) throws Exception {
        String sql = "insert into " + tableName + " (";

        // 遍历
        Set<String> keySet = map.keySet();
        List<String> keyList = new ArrayList<>();
        for (String name : keySet) {
            sql += name.toLowerCase() + ",";
            keyList.add(name);
        }
        sql = sql.substring(0, sql.length() - 1) + ") values(";
        for (String name : keyList) {
            sql += "?,";
        }
        sql = sql.substring(0, sql.length() - 1) + ");";
        PreparedStatement pre = con.prepareStatement(sql);
        for (int i = 1; i <= keySet.size(); i++) {
            String key = keyList.get(i - 1);
            Object value = map.get(key);
            pre.setObject(i, value == null ? "" : value);
        }
        int i = pre.executeUpdate();
        closeConnection(null, pre, null);
        return i;
    }
}
