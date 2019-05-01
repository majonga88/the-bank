package com.bank.account;

import com.bank.account.dao.AccountDao;
import com.bank.account.dao.AccountDaoImpl;
import com.bank.account.model.Account;
import com.bank.account.model.Transfer;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    private AccountDao accountDao = new AccountDaoImpl();

    @GET
    public Response getAll() {
        try {
            return Response.ok(accountDao.getAll()).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        try {
            return Response.ok(accountDao.get(id)).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @POST
    public Response create(Account account) {

        try {
            return Response.ok(accountDao.create(account)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response delete(@PathParam("username") long username) {
        try {
            return Response.ok(accountDao.delete(username)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}/withdraw/{amount}")
    public Response withdraw(@PathParam("id") long id, @PathParam("amount") long amount) {
        try {
            return Response.ok(accountDao.withdraw(id, BigDecimal.valueOf(amount))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}/deposit/{amount}")
    public Response deposit(@PathParam("id") long id, @PathParam("amount") long amount) {
        try {
            return Response.ok(accountDao.deposit(id, BigDecimal.valueOf(amount))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/transfer")
    public Response transfer(Transfer transfer) {
        try {
            return Response.ok(accountDao.transfer(transfer)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
