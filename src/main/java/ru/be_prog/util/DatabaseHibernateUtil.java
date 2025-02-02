package ru.be_prog.util;


import org.hibernate.cfg.Configuration;
import ru.be_prog.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHibernateUtil {
    public static Configuration getConfiguration() {
        Configuration cfg = new Configuration()
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/customers")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "Kali4Kali")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
              //  .setProperty("hibernate.connection.datasource", "java:com/driver/db/entity")
                .setProperty("hibernate.order_updates", "true")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl", "update")
                .addAnnotatedClass(Account.class);
        return cfg;
    }
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "customers";
        String userName = "root";
        String password = "Kali4Kali";

        Class.forName("org.postgresql.Driver");

        String connectionURL = "jdbc:postgresql://" + hostName + ":5432/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
