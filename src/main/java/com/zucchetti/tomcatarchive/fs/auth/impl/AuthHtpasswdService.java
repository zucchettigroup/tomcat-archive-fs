package com.zucchetti.tomcatarchive.fs.auth.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.mindrot.jbcrypt.BCrypt;

import com.zucchetti.tomcatarchive.fs.auth.IAuthResourceReader;
import com.zucchetti.tomcatarchive.fs.auth.IAuthService;

public class AuthHtpasswdService implements IAuthService {
	private final IAuthResourceReader resourceReader;

	public AuthHtpasswdService(IAuthResourceReader resourceReader) {
		this.resourceReader = resourceReader;
	}

	@Override
	public boolean verifyCredential(String username, String password) {

		// read htpasswd file in search of the username
		String storedPwd = this.resourceReader.read(username);
		if (storedPwd != null) 
		{
			// test Apache MD5 variant encrypted password
			if (storedPwd.startsWith("$apr1$") && storedPwd.equals(Md5Crypt.apr1Crypt(password, storedPwd))) 
			{
				return true;
			}
			// test unsalted SHA password
			if (storedPwd.startsWith("{SHA}") 
					&& storedPwd.substring("{SHA}".length()).equals(Base64.encodeBase64String(DigestUtils.sha1(password)))) 
			{
				return true;
			}
			// test bCrypt SHA-256 password
			if (storedPwd.startsWith("$2a$") && BCrypt.checkpw(password, storedPwd))
			{
				return true;
			}
		}
		return false;
	}
}
