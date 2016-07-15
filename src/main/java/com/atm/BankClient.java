package com.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class BankClient {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {

		HttpClient client = null;
		String Url = "http://localhost:8080/Bank/rest/BankApp";

		String accountNumber;
		String password, line;
		int choice = 0, amount = 0;
		HttpGet request;
		HttpResponse response;
		BufferedReader br1;
		InputStream in;
		try {
			BankClient bnkClient = new BankClient();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("enter account number");
			try {
				accountNumber = br.readLine();
				System.out.println("enter password");
				password = br.readLine();
				String Urlverify = Url + ";accountNumber=" + accountNumber
						+ ";pwd=" + password;
				request = new HttpGet(Urlverify);
				client = new DefaultHttpClient();
				response = client.execute(request);
				in = response.getEntity().getContent();
				br1 = new BufferedReader(new InputStreamReader(in));
				while ((line = br1.readLine()) != null) {
					System.out.println(line);
				}
				if (response.getStatusLine().getStatusCode() == 200) {
					do {
						System.out.println("Menu ");
						System.out
								.println("1.deposit  n  d   deposit d dollars into account  n");
						System.out
								.println("2.withdraw n  d		withdraw d dollars from account n");
						System.out
								.println("3.balance  n		get the balance of account n");
						System.out.println("4.exit			program exit");
						System.out.println("enter ur option as a number ");
						choice = Integer.parseInt(br.readLine());

						switch (choice) {
						case 1:
							System.out.println("enter amount");
							client = new DefaultHttpClient();
							amount = Integer.parseInt(br.readLine());
							if (bnkClient.isValidAmount(amount)) {

								String Urldeposit = Url + "/deposit;"
										+ "accNum=" + accountNumber + ";pwd="
										+ password + ";amount=" + amount;
								request = new HttpGet(Urldeposit);
								response = client.execute(request);
								in = response.getEntity().getContent();
								br1 = new BufferedReader(new InputStreamReader(
										in));
								while ((line = br1.readLine()) != null) {
									System.out.println(line);
								}
							} else {
								System.out.println("invalid amount");
							}
							break;
						case 2:
							client = new DefaultHttpClient();
							System.out.println("enter amount");
							amount = Integer.parseInt(br.readLine());
							if (bnkClient.isValidAmount(amount)) {
								String Urlwithdraw = Url + "/withdraw;"
										+ "accNum=" + accountNumber + ";pwd="
										+ password + ";amount=" + amount;
								request = new HttpGet(Urlwithdraw);
								response = client.execute(request);
								in = response.getEntity().getContent();
								br1 = new BufferedReader(new InputStreamReader(
										in));
								while ((line = br1.readLine()) != null) {
									System.out.println(line);
								}
							} else {
								System.out.println("invalid amount");
							}
							break;
						case 3:
							client = new DefaultHttpClient();
							String Urlbalance = Url + "/balance;" + "accNum="
									+ accountNumber + ";pwd=" + password;
							request = new HttpGet(Urlbalance);
							response = client.execute(request);
							in = response.getEntity().getContent();
							br1 = new BufferedReader(new InputStreamReader(in));
							while ((line = br1.readLine()) != null) {
								System.out.println(line);
							}

							break;
						case 4:

							return;
						}

					} while (true);
				}

			} catch (IOException ie) {
				System.out.println(ie.getMessage());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Validates the amount entered by the user
	 * 
	 * @param amount
	 * @return
	 */
	boolean isValidAmount(int amount) {
		if (amount <= 0) {
			System.out.println("entered invalid amount");
			return false;
		}
		return true;
	}

}
