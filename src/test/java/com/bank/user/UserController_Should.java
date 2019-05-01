package com.bank.user;

import com.bank.user.model.User;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserController_Should extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(UserController.class);
    }

    @Test
    public void getAll() {

        Response response = target("/users").request().get();

        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return user list", response.getEntity().toString());
    }

    @Test
    public void get() {

        Response response = target("/users/1").request().get();

        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return user list", response.getEntity().toString());
    }

    @Test
    public void set() {

        User user = new User(1, "sdo", "Sloane", "Do", "s.do@mail.com");

        Response response = target("/users")
                .request()
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void create() {

        User user = new User("jdo", "John", "Do", "j.do@mail.com");

        Response response = target("/users")
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void delete() {

        Response response = target("/users/jdo").request().delete();

        assertEquals("should return status 200", 200, response.getStatus());
    }
}
