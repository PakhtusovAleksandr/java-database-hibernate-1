package ru.be_prog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.be_prog.Account;
import ru.be_prog.util.DatabaseHibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDaoHibernateImpl implements AccountDao {

    @Override
    public void createAccountTable() {
        // Реализуем создание таблицы здесь
        try (Connection connection = DatabaseHibernateUtil.getMySQLConnection()) {
            System.out.println("Create table in Db");
            Statement statement = connection.createStatement();
//Создание таблицы, думал через билдер, но так более понятно по лоховски- полный запрос виден
            // Добавил exists чтобы создавалась таблица когда ее еще нет
            String SQL = "CREATE TABLE IF NOT EXISTS Account " +
                    "(id UUID not NULL, " +
                    " login VARCHAR(50), " +
                    " name VARCHAR (50), " +
                    " lastName VARCHAR (50), " +
                    " email VARCHAR (50), " +
                    " PRIMARY KEY (id))";

            statement.executeUpdate(SQL);
            System.out.println("Table created");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropAccountTable() {
        // Реализуем удаление таблицы здесь
        try (Connection connection = DatabaseHibernateUtil.getMySQLConnection()) {

            System.out.println("Delete table from Db");
            Statement statement = connection.createStatement();
// Добавил в запрос exists, чтобы удалялась таблица когда она создана
            String SQL = "DROP TABLE IF EXISTS Account";
            statement.executeUpdate(SQL);

            System.out.println("Table deleted");
            statement.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAllAccounts() {
        // Реализуем удаление всех Account
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            System.out.println("Delete all accounts");
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Account> query =  session.createQuery("FROM Account", Account.class).list();
            System.out.println("Accounts in DB: "+query.size());
            for (Account account : query) {
                session.remove(account);
            }
            transaction.commit();
            session.close();
            sessionFactory.close();
            System.out.println("All accounts deleted");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void createAccount(Account account) {
        // Реализуем создание нового Account

        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            System.out.println("Create account");
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(account);
            transaction.commit();
            session.close();
            sessionFactory.close();
            System.out.println("Account created");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        // Реализуем обновление Account
        try {
            System.out.println("Change account for  =" + account.toString());
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            System.out.println("Check DB" + session.get(Account.class, account.getId()));

            Account account1 = session.get(Account.class, account.getId());

            account1.setLogin(account.getLogin());
            account1.setName(account.getName());
            account1.setLastName(account.getLastName());
            account1.setEmail(account.getEmail());
            session.merge(account1);
            System.out.println(account1 + "  changed");
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void deleteAccount(UUID id) {
        // Реализуем удаление Account по id

        try {
            System.out.println("Find account in Db for his UUID =" + id);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            Account account = session.get(Account.class, id);
            session.remove(account);
            transaction.commit();
            System.out.println(account + " Deleted");

            session.close();
            sessionFactory.close();
        } catch (
                Exception e) {
            System.out.println("Exception!" + e);
        }

    }

    @Override
    public Account findAccount(UUID id) {
        // Реализуем получение Account по id
        Account account = new Account();
        try {
            System.out.println("Find account in Db for his UUID =" + id);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            account = session.get(Account.class, id);
            System.out.println(account + " founded");
            session.close();
            return account;
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        // Реализуем получение всех Account
        List<Account> accounts = new ArrayList<>();
        try {
            System.out.println("Find ALL account in Db ");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(DatabaseHibernateUtil.getConfiguration().getProperties());
            SessionFactory sessionFactory = DatabaseHibernateUtil.getConfiguration().buildSessionFactory(builder.build());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            accounts = session.createQuery("from Account", Account.class).list();
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
        if (!accounts.isEmpty()) {
            for (Account account : accounts) {
                System.out.println(account);
            }
        } else {
            System.out.println("Account don't created");
        }
        return accounts;
    }
}