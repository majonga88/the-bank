package com.bank.user.dao;

import com.bank.user.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    List<User> getAll() throws SQLException;

    User get(final String username) throws SQLException;

    int set(final User user) throws SQLException;

    int create(final User user) throws SQLException;

    int delete(final String username) throws SQLException;

}
