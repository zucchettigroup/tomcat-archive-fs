package com.zucchetti.tomcatarchive.fs.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.zucchetti.tomcatarchive.fs.auth.AuthenticationException;
import com.zucchetti.tomcatarchive.fs.auth.IAuthResourceReader;
import com.zucchetti.tomcatarchive.fs.auth.IAuthService;
import com.zucchetti.tomcatarchive.fs.auth.impl.AuthHtpasswdService;

public class BasicAuthenticationTest 
{
	private static final String USERNAME = "massimo";

	@Test
	public void invalidPasswordTest() throws AuthenticationException
	{
		// Fixture
		IAuthResourceReader authResourceMock = Mockito.mock(IAuthResourceReader.class);
		IAuthService auth = new AuthHtpasswdService(authResourceMock);
		// Stubbing
		Mockito.when(authResourceMock.read(USERNAME)).thenReturn("$apr1$encodingacaso.");
		// Run Test
		boolean isValid = auth.verifyCredential(USERNAME, "miapassword");
		// Assertion
		Assertions.assertFalse(isValid);
	}

	@Test
	public void md5Test() throws AuthenticationException
	{
		// Fixture
		IAuthResourceReader authResourceMock = Mockito.mock(IAuthResourceReader.class);
		IAuthService auth = new AuthHtpasswdService(authResourceMock);
		// Stubbing
		Mockito.when(authResourceMock.read(USERNAME)).thenReturn("$apr1$PAlGdnWB$EhiPT6R3wXPoOUBuDmSHv.");
		// Run Test
		boolean isValid = auth.verifyCredential(USERNAME, "miapassword");
		// Assertion
		Assertions.assertTrue(isValid);
	}

	@Test
	public void bCryptTest()
	{
		// Fixture
		IAuthResourceReader authResourceMock = Mockito.mock(IAuthResourceReader.class);
		IAuthService auth = new AuthHtpasswdService(authResourceMock);
		// Stubbing
		Mockito.when(authResourceMock.read(USERNAME)).thenReturn("$2a$10$OKVeQESmCEtr697eOaoVReU2Xc3KjrH.ooc3zWWbAy/X8jm2HXdKO");
		// Run Test
		boolean isValid = auth.verifyCredential(USERNAME, "miapassword");
		// Assertion
		Assertions.assertTrue(isValid);
	}

	@Test
	public void shaTest()
	{
		// Fixture
		IAuthResourceReader authResourceMock = Mockito.mock(IAuthResourceReader.class);
		IAuthService auth = new AuthHtpasswdService(authResourceMock);
		// Stubbing
		Mockito.when(authResourceMock.read(USERNAME)).thenReturn("{SHA}NY/NNQ9Um4Oh3GbxQbPGEeygT8Q=");
		// Run Test
		boolean isValid = auth.verifyCredential(USERNAME, "miapassword");
		// Assertion
		Assertions.assertTrue(isValid);
	}
}
