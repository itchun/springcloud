package com.itchun.dataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Component
public class BeanInit {

    @Resource
    private HikariDataSource hikariDataSource;

    @Resource(name = "myDatasource")
    private DataSource dataSource;

    @Bean(name = "myDatasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @RefreshScope
    public DataSource refreshDatasource() {
        return DataSourceBuilder.create().build();
    }

    public Object init() throws Exception {
        Connection connection = hikariDataSource.getConnection();
        String sql = "select * from registration_baby limit 1";
        List<LinkedHashMap<String, Object>> list = findBySql(connection, sql);
        return list;
    }

    public Object init2() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "select * from registration_baby limit 1";
        List<LinkedHashMap<String, Object>> list = findBySql(connection, sql);
        return list;
    }


    public static List<LinkedHashMap<String, Object>> findBySql(Connection con, String sql) throws Exception {
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
        close(null, pre, resultSet);
        return list;
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
