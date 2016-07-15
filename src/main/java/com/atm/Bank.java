package com.atm;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/*
 * Rest Service which implements deposit,withdraw and balance.
 */
@Path("/BankApp")
public class Bank {
	private String accountNum = "1234";
	private String password = "12";
	static double balance = 0;
	int accountError = 1;
	int pwdError = 2;
	int acctpwdError = 3;
	int success = 0;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyAccount(@MatrixParam("accountNumber") String accountNumber,
			@MatrixParam("pwd") String pwd) {
		// System.out.println("accountNum "+accountNum+"  password "+pwd);
		String value = "success";
		int status = 200;

		// TODO Auto-generated method stub
		if (accountNumber == null || !accountNumber.equals(accountNum) || accountNumber.isEmpty()) {
			if (!password.equals(pwd)) {
				value = "Invalid account and password";
				status = 401;
			} else {
				value = "Invalid account";
				status = 401;
			}
		}
		if (pwd == null || !password.equals(pwd) || pwd.isEmpty()) {
			if (!accountNumber.equals(accountNum)) {
				value = "Invalid account and password";
				status = 401;
			} else {
				value = "Invalid password";
				status = 401;
			}
		}

		return Response.status(status).entity("{login:" + value + "}").build();

	}

	@GET
	@Path("deposit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deposit(@MatrixParam("accNum") String accNum,
			@MatrixParam("pwd") String pwd, @MatrixParam("amount") double amount) {
		// TODO Auto-generated method stub
		Response res = verifyAccount(accNum, pwd);
		if (res.getStatus() == 200) {
			balance += amount;
			return Response
					.status(200)
					.entity("{" + "deposit : success" + ",balance:" + balance
							+ "}").build();
		} else {
			return res;
		}
	}

	@GET
	@Path("withdraw")
	@Produces(MediaType.APPLICATION_JSON)
	public Response withdraw(@MatrixParam("accNum") String accNum,
			@MatrixParam("pwd") String pwd, @MatrixParam("amount") double amount) {
		// TODO Auto-generated method stub
		Response res = verifyAccount(accNum, pwd);
		if (res.getStatus() == 200) {
			balance -= amount;
			return Response
					.status(200)
					.entity("{" + "withdraw : success" + ",balance:" + balance
							+ "}").build();
		} else {
			return res;
		}
	}

	@GET
	@Path("balance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response balance(@MatrixParam("accNum") String accNum,
			@MatrixParam("pwd") String pwd) {
		// TODO Auto-generated method stub
		Response res = verifyAccount(accNum, pwd);
		if (res.getStatus() == 200) {
			return Response.status(200)
					.entity("{" + "balance:" + balance + "}").build();
		} else {
			return res;
		}
	}
}
