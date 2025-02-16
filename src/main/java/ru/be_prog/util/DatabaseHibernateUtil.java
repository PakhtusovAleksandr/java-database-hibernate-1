package ru.be_prog.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.be_prog.Account;

public class DatabaseHibernateUtil {
    public static final Configuration cfg = new Configuration()
            .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
            .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/accounts")
            .setProperty("hibernate.connection.username", "account_service")
            .setProperty("hibernate.connection.password", "account_service")
            .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
            .setProperty("hibernate.order_updates", "true")
            .setProperty("hibernate.show_sql", "true")
            .setProperty("hibernate.hbm2ddl", "update")
            .addAnnotatedClass(Account.class);

    public static Session getSession() {
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        return sessionFactory.openSession();
    }
}
