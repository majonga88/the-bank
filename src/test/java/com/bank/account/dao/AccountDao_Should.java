package com.bank.account.dao;

import com.bank.account.model.Account;
import com.bank.account.model.Transfer;
import com.bank.infrastructure.db.DbUtils;
import com.bank.user.dao.UserDao;
import com.bank.user.dao.UserDaoImpl;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AccountDao_Should {

    private static final AccountDao accountDao = new AccountDaoImpl();

    @Before
    public void setUp() throws Exception {

        RunScript.execute(DbUtils.getConnection(), new FileReader("src/test/resources/data.sql"));

    }

    @Test
    public void get_all_accounts() throws SQLException {

        List<Account> accounts = accountDao.getAll();

        assertNotNull(accounts);
        assertEquals(5, accounts.size());
    }

    @Test
    public void get() throws SQLException {

        Account account = accountDao.get(1);

        assertNotNull(account);
        assertEquals("100.0000", account.getBalance().toString());
    }

    @Test
    public void withdraw() throws SQLException {

        int withdrawed = accountDao.withdraw(1, BigDecimal.TEN);

        assertEquals(1, withdrawed);

        Account account = accountDao.get(1);

        assertEquals("90.0000", account.getBalance().toString());
    }

    @Test
    public void deposit() throws SQLException {

        int deposited = accountDao.deposit(1, BigDecimal.TEN);

        assertEquals(1, deposited);

        Account account = accountDao.get(1);

        assertEquals("110.0000", account.getBalance().toString());
    }

    @Test
    public void create() throws SQLException {

        Account account = new Account("mfinchley", "Bank Account", BigDecimal.ZERO, "EUR");

        int accountCreated = accountDao.create(account);

        assertEquals(1, accountCreated);

        Account getCreatedAccount = accountDao.get(6);

        assertEquals("0.0000", getCreatedAccount.getBalance().toString());
    }

    @Test
    public void delete() throws SQLException {

        int accountDeleted = accountDao.delete(1);

        assertEquals(1, accountDeleted);

        Account getDeletedAccount = accountDao.get(1);

        assertNull(getDeletedAccount);
    }

    @Test
    public void transfer() throws SQLException {

        Transfer transfer = new Transfer(2, 3, BigDecimal.TEN, "EUR");

        int transferMoney = accountDao.transfer(transfer);

        assertEquals(1, transferMoney);

        Account getDebitedAccount = accountDao.get(2);
        Account getChargedAccount = accountDao.get(3);

        assertEquals("190.0000", getDebitedAccount.getBalance().toString());
        assertEquals("110.0000", getChargedAccount.getBalance().toString());
    }
}
