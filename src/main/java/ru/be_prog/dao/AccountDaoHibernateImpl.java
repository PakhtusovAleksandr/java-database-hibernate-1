package ru.be_prog.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.be_prog.Account;
import ru.be_prog.util.DatabaseHibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDaoHibernateImpl implements AccountDao {

    @Override
    public void createAccountTable() {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Create table in Db");
            String SQL = """
                    CREATE TABLE IF NOT EXISTS Account
                    (id UUID not NULL,login VARCHAR(50),name VARCHAR (50),lastName VARCHAR (50),email VARCHAR (50),PRIMARY KEY (id))
                    """;
            session.createNativeQuery(SQL, Account.class);
            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void dropAccountTable() {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Delete table from Db ");
            String SQL = "DROP TABLE IF EXISTS Account";
            session.createNativeQuery(SQL, Account.class);
            System.out.println("Table deleted");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void deleteAllAccounts() {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Deleting all accounts");
            Transaction transaction = session.beginTransaction();
            List<Account> query = session.createQuery("FROM Account", Account.class).list();
            System.out.println("Accounts in DB : " + query.size());
            for (Account account : query) {
                session.remove(account);
            }
            transaction.commit();
            System.out.println("All accounts deleted");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void createAccount(Account account) {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Create account");
            Transaction transaction = session.beginTransaction();
            session.persist(account);
            transaction.commit();
            System.out.println("Account is created");
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Change account for  =" + account.toString());
            Transaction transaction = session.beginTransaction();
            Account oldAccount = session.get(Account.class, account.getId());
            System.out.println("Check DB for ID: " + oldAccount);
            if (oldAccount!=null) {
                oldAccount.setLogin(account.getLogin());
                oldAccount.setName(account.getName());
                oldAccount.setLastName(account.getLastName());
                oldAccount.setEmail(account.getEmail());
                session.persist(oldAccount);
                System.out.println(oldAccount + "  changed");
                transaction.commit();
            }
            else {
                System.out.println("Account not found");
            }
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public void deleteAccount(UUID id) {
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Find account in Db for his UUID =" + id);
            Transaction transaction = session.beginTransaction();
            Account account = session.get(Account.class, id);
            session.remove(account);
            transaction.commit();
            System.out.println(account + " is Deleted");
        } catch (
                Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    @Override
    public Account findAccount(UUID id) {
        Account account = new Account();
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Find account in Db for his UUID =" + id);
            account = session.get(Account.class, id);
            System.out.println(account + " is founded");
            return account;
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Session session = DatabaseHibernateUtil.getSession()) {
            System.out.println("Find ALL account in Db ");
            Transaction transaction = session.beginTransaction();
            accounts = session.createQuery("from Account", Account.class).list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
        if (accounts.isEmpty()) {
            System.out.println("Account don't created");
        }
        return accounts;
    }
}