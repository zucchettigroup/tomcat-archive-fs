package com.zucchetti.tomcatarchive.fs.auth;

public interface IAuthService 
{
	boolean verifyCredential(String username, String password);
}
