package com.bank.user.dao;

import com.bank.infrastructure.db.DbUtils;
import com.bank.user.model.User;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDao_Should {

    private static final UserDao userDao = new UserDaoImpl();

    @Before
    public void setUp() throws Exception {

        RunScript.execute(DbUtils.getConnection(), new FileReader("src/test/resources/data.sql"));

    }

    @Test
    public void get_all_users() throws SQLException {

        List<User> users = userDao.getAll();

        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void get_user_by_username() throws SQLException {

        User user = userDao.get("mfinchley");

        assertNotNull(user);
        assertEquals("Michael", user.getFirstName());
        assertEquals("Finchley", user.getLastName());
    }

    @Test
    public void create_a_user() throws SQLException {

        User user = new User("fkingsley", "Fischer", "Kingsley", "f.kingsley@mail.com");

        int created = userDao.create(user);

        assertEquals(1, created);

        User fkingsley = userDao.get("fkingsley");

        assertNotNull(fkingsley);
        assertEquals("Fischer", fkingsley.getFirstName());
        assertEquals("Kingsley", fkingsley.getLastName());
    }

    @Test
    public void set_a_user() throws SQLException {

        User user = new User("mfinchley", "Michael", "Finchley.S.A.R");

        int updated = userDao.set(user);

        assertEquals(1, updated);

        User mfinchley = userDao.get("mfinchley");

        assertNotNull(mfinchley);
        assertEquals("Michael", mfinchley.getFirstName());
        assertEquals("Finchley.S.A.R", mfinchley.getLastName());
    }

    @Test
    public void delete_a_user() throws SQLException {

        userDao.delete("mfinchley");
    }
}
