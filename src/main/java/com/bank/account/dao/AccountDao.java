package com.bank.account.dao;

import com.bank.account.model.Account;
import com.bank.account.model.Transfer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    List<Account> getAll() throws SQLException;

    Account get(final long id) throws SQLException;

    int withdraw(final long id, final BigDecimal newBalance) throws SQLException;

    int deposit(final long id, final BigDecimal newBalance) throws SQLException;

    int create(final Account account) throws SQLException;

    int delete(final long id) throws SQLException;

    int transfer(Transfer transfer) throws SQLException;
}
