package com.itchun.monitor;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
@Component
public class DBmonitor {

    private HikariPoolMXBean proxy;
    private boolean hasStarted;

    @Resource
    private HikariDataSource hikariDataSource;

    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void monitor() {
        try {
            if (proxy == null)
                proxy = hikariDataSource.getHikariPoolMXBean();
            else
                log.info("TotalConnections:{}, ActiveConnections:{}, IdleConnections:{}, ThreadsAwaitingConnection:{}",
                        proxy.getTotalConnections(), proxy.getActiveConnections(), proxy.getIdleConnections(), proxy.getThreadsAwaitingConnection());
        } catch (Throwable cause) {
            log.error("", cause);
        }
    }


    //    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void useDatabaseConnectionPool_v1() throws Exception {
        if (!hasStarted) {
            hasStarted = true;
            Connection connection = hikariDataSource.getConnection();
            String sql = "select 1 as aaa";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            resultSet.next();
            log.info(resultSet.getString("aaa"));
        }
    }


    //    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void useDatabaseConnectionPool_v2() throws Exception {
        if (!hasStarted) {
            hasStarted = true;
            Connection connection = hikariDataSource.getConnection();
            String sql = "select 1 as aaa";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            log.info(resultSet.getString("aaa"));
            Thread.sleep(2000);
            statement.close();
            resultSet.close();
            connection.close();
        }
    }


    // 此种版本: 每次生成的连接都活跃在，累积达到最大连接数之后就等待了，等待久了就超时报错了
//    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void useDatabaseConnectionPool_v3() throws Exception {
        Connection connection = hikariDataSource.getConnection();
        String sql = "select 1 as aaa";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        Thread.sleep(2000);
    }


    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void useDatabaseConnectionPool_v4() throws Exception {
        Connection connection = hikariDataSource.getConnection();
        String sql = "select 1 as aaa";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
//        log.info(resultSet.getString("aaa"));
//        Thread.sleep(2000);
        statement.close();
        resultSet.close();
        connection.close();
    }

}
