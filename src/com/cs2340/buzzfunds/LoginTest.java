package com.cs2340.buzzfunds;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginTest {

	@Test
	public void testLogin() {
		Authenticator auth = new Authenticator(DefaultConnection.BUZZFUNDS);
		auth.setUsername("testUser1234");
		auth.setPassword("password");
		assertTrue(auth.httpGetLogin("1"));
	}
	
	@Test
	public void testGetAccounts() {
		Authenticator auth = new Authenticator(DefaultConnection.BUZZFUNDS);
		auth.setUsername("testUser1234");
		auth.setPassword("password");
		assertNotNull(auth.httpGetSyncAccount());
	}
	
	@Test
	public void testTransaction() {
		Authenticator auth = new Authenticator(DefaultConnection.BUZZFUNDS);
		auth.setUsername("testUser1234");
		auth.setPassword("password");
		double previousBalance = auth.httpGetSyncAccount()[0].getBalance();
		auth.httpGetTransaction(new Transaction(auth.httpGetSyncAccount()[0], 100.00, "deposit"), "2014-03-30");
		auth.httpGetTransaction(new Transaction(auth.httpGetSyncAccount()[0], 100.00, "withdrawal"), "2014-03-30");
		assertEquals(previousBalance, auth.httpGetSyncAccount()[0].getBalance(), 0);
	}

}
