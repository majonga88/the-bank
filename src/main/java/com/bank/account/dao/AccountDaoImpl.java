package com.bank.account.dao;

import com.bank.account.model.Account;
import com.bank.account.model.Transfer;
import com.bank.infrastructure.db.DbUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private Connection connection = DbUtils.getConnection();

    @Override
    public List<Account> getAll() throws SQLException {

        final String SELECT_ALL_ACCOUNTS = "SELECT id, username, name, balance, currency FROM account";

        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ACCOUNTS)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account account = new Account(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getBigDecimal("balance"),
                        rs.getString("currency")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            // log
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return accounts;
    }

    @Override
    public Account get(final long id) throws SQLException {

        final String SELECT_ACCOUNT = "SELECT id, username, name, balance, currency FROM account WHERE id = ?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ACCOUNT)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getBigDecimal("balance"),
                        rs.getString("currency")
                );
            }
        } catch (SQLException e) {
            // log
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    @Override
    public int withdraw(final long id, final BigDecimal amount) throws SQLException {

        final String SELECT_ACCOUNT = "SELECT id, balance FROM account WHERE id = ?";
        final String SET_BALANCE_ACCOUNT = "UPDATE account SET balance = ? WHERE id = ?";

        PreparedStatement ps;

        try (Connection connection = DbUtils.getConnection()) {

            ps = connection.prepareStatement(SELECT_ACCOUNT);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                long withdrawerAccountId = rs.getLong("id");
                BigDecimal currentBalance = rs.getBigDecimal("balance");

                ps = connection.prepareStatement(SET_BALANCE_ACCOUNT);
                ps.setBigDecimal(1, currentBalance.subtract(amount));
                ps.setLong(2, withdrawerAccountId);

                int update = ps.executeUpdate();

                connection.commit();

                return update;
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public int deposit(final long id, final BigDecimal amount) throws SQLException {

        final String SELECT_ACCOUNT = "SELECT id, balance FROM account WHERE id = ?";
        final String SET_BALANCE_ACCOUNT = "UPDATE account SET balance = ? WHERE id = ?";

        PreparedStatement ps;

        try (Connection connection = DbUtils.getConnection()) {

            ps = connection.prepareStatement(SELECT_ACCOUNT);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                long withdrawerAccountId = rs.getLong("id");
                BigDecimal currentBalance = rs.getBigDecimal("balance");

                ps = connection.prepareStatement(SET_BALANCE_ACCOUNT);
                ps.setBigDecimal(1, currentBalance.add(amount));
                ps.setLong(2, withdrawerAccountId);

                int update = ps.executeUpdate();

                connection.commit();

                return update;
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public int create(final Account account) throws SQLException {

        final String CREATE_AN_ACCOUNT = "INSERT INTO account (username, name, balance, currency) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_AN_ACCOUNT)) {

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getName());
            ps.setBigDecimal(3, account.getBalance());
            ps.setString(4, account.getCurrency());

            int update = ps.executeUpdate();

            connection.commit();

            return update;
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public int delete(final long id) throws SQLException {

        final String DELETE_AN_ACCOUNT = "DELETE FROM account WHERE id = ?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_AN_ACCOUNT)) {

            ps.setLong(1, id);

            int update = ps.executeUpdate();

            connection.commit();

            return update;
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public int transfer(Transfer transfer) throws SQLException {

        final String SELECT_ACCOUNT = "SELECT id, username, name, balance, currency FROM account WHERE id = ?";
        final String UPDATE_BALANCE_ACCOUNT = "UPDATE account SET balance = ? WHERE id = ? ";

        ResultSet rs;
        PreparedStatement ps;

        try (Connection connection = DbUtils.getConnection()) {

            ps = connection.prepareStatement(SELECT_ACCOUNT);
            ps.setLong(1, transfer.getTo());
            rs = ps.executeQuery();

            if (rs.next()) {

                Account requesterAccount = new Account(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getBigDecimal("balance"),
                        rs.getString("currency")
                );

                ps = connection.prepareStatement(SELECT_ACCOUNT);
                ps.setLong(1, transfer.getFrom());
                rs = ps.executeQuery();

                if (rs.next()) {

                    Account beneficiaryAccount = new Account(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getBigDecimal("balance"),
                            rs.getString("currency")
                    );

                    ps = connection.prepareStatement(UPDATE_BALANCE_ACCOUNT);
                    ps.setBigDecimal(1, requesterAccount.getBalance().subtract(transfer.getAmount()));
                    ps.setLong(2, requesterAccount.getId());

                    ps.executeUpdate();

                    ps = connection.prepareStatement(UPDATE_BALANCE_ACCOUNT);
                    ps.setBigDecimal(1, beneficiaryAccount.getBalance().add(transfer.getAmount()));
                    ps.setLong(2, beneficiaryAccount.getId());

                    ps.executeUpdate();

                    connection.commit();

                    return 1;
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }
}
