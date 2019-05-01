package com.bank.user.dao;

import com.bank.infrastructure.db.DbUtils;
import com.bank.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private Connection connection = DbUtils.getConnection();

    @Override
    public List<User> getAll() throws SQLException {

        final String SELECT_ALL_USERS = "SELECT id, username, firstName, lastName, email FROM user";

        List<User> users = new ArrayList<>();

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            // log
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return users;
    }

    @Override
    public User get(final String username) throws SQLException {

        final String SELECT_USER = "SELECT id, username, firstName, lastName, email FROM user WHERE username=?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            // log
            System.out.println();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    @Override
    public int set(final User user) throws SQLException {

        final String SET_USER = "UPDATE user SET firstName = ?, lastName = ? WHERE username = ? ";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(SET_USER)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());

            int update = ps.executeUpdate();

            connection.commit();

            return update;
        } catch (SQLException e) {
            // log
            System.out.println();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    @Override
    public int create(final User user) throws SQLException {

        final String CREATE_A_USER = "INSERT INTO user (username, firstName, lastName, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_A_USER)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());

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
    public int delete(final String username) throws SQLException {

        final String DELETE_A_USER= "DELETE FROM user WHERE username = ?";

        try (Connection connection = DbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_A_USER)) {

            ps.setString(1, username);

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
}
