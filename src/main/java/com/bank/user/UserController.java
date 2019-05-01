package com.bank.user;

import com.bank.user.dao.UserDao;
import com.bank.user.dao.UserDaoImpl;
import com.bank.user.model.User;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserDao userDao = new UserDaoImpl();

    @GET
    public Response getAll() {
        try {
            return Response.ok(userDao.getAll()).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{username}")
    public Response get(@PathParam("username") String username) {
        try {
            return Response.ok(userDao.get(username)).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @PUT
    public Response set(User user) {
        try {
            userDao.set(user);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response create(User user) {
        try {
            userDao.create(user);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response delete(@PathParam("username") String username) {

        try {
            userDao.delete(username);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
