package com.bank.account;

import com.bank.account.model.Account;
import com.bank.account.model.Transfer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountController_Should extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountController.class);
    }

    @Test
    public void get_account() {

        Response response = target("/accounts/1").request().get();

        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return user list", response.getEntity().toString());
    }

    @Test
    public void get_all_account() {

        Response response = target("/accounts").request().get();

        assertEquals("should return status 200", 200, response.getStatus());
        assertNotNull("Should return user list", response.getEntity().toString());
    }

    @Test
    public void create_account() {

        Account account = new Account("", "PEL", BigDecimal.TEN, "EURO");

        Response response = target("/accounts")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void delete_account() {

        Response response = target("/accounts/1").request().delete();

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void withdraw_account() {

        Entity<?> empty = Entity.text("");

        Response response = target("/accounts/1/withdraw/100")
                .request().put(empty);

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void withdraw_account_with_wrong_signature() {

        Entity<?> empty = Entity.text("");

        Response response = target("/accounts/1/withdraws/100")
                .request().put(empty);

        assertEquals("should return status 404", 404, response.getStatus());
    }

    @Test
    public void deposit_account() {

        Entity<?> empty = Entity.text("");

        Response response = target("/accounts/1/deposit/100")
                .request().put(empty);

        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void transfer_money() {

        Transfer transfer = new Transfer(2,3, BigDecimal.TEN, "EURO");

        Response response = target("/accounts/transfer")
                .request().post(Entity.entity(transfer, MediaType.APPLICATION_JSON));

        assertEquals("should return status 200", 200, response.getStatus());
    }
}
