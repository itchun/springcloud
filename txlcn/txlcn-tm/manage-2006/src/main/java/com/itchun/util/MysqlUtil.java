package com.itchun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlUtil {

    public static void main(String[] args)throws Exception {
        Connection connection = get_connection("com.mysql.jdbc.Driver","jdbc:mysql://192.168.1.160:3306/tx-manager?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false","root","datahome123");
        System.out.println("ok");
    }

    public static Connection get_connection(String driver, String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }
}
