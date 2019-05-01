package com.bank;

import com.bank.account.AccountController;
import com.bank.infrastructure.db.DbUtils;
import com.bank.user.UserController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.h2.tools.RunScript;

import java.io.FileReader;

public class Application {

	public static void main(String[] args) throws Exception {

		if (args.length != 0) {
			RunScript.execute(DbUtils.getConnection(), new FileReader(args[0]));
			startService();
		} else {
			System.out.println("Cannot able to start the api \n Please enter the full path of your database schema like data.sql as program argument");
		}
	}

	private static void startService() throws Exception {

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/api");

		Server jettyServer = new Server(8080);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
									   AccountController.class.getCanonicalName()  + "," +
									   UserController.class.getCanonicalName());
		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}

}
