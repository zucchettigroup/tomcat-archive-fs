package com.zucchetti.tomcatarchive.fs.auth;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.idm.PasswordCredential;

public class MapIdentityManager implements IdentityManager  
{
    private final IAuthService authService;

    public MapIdentityManager(IAuthService authService) 
    {
        this.authService = authService;
    }

    @Override
    public Account verify(Account account) 
    {
        return account;
    }

    @Override
    public Account verify(String username, Credential credential)
    {
        Account account = newAccount(username);
        if (verifyCredential(account, credential)) 
        {
            return account;
        }
        return null;
    }

    @Override
    public Account verify(Credential credential) 
    {
        return null;
    }

    private boolean verifyCredential(Account account, Credential credential) 
    {
        if (credential instanceof PasswordCredential) 
        {
            char[] password = ((PasswordCredential) credential).getPassword();
            String username = account.getPrincipal().getName();
            return this.authService.verifyCredential(username, new String(password));
        }
        return false;
    }

    private static Account newAccount(final String username) 
    {
        return new Account() 
        {
			private static final long serialVersionUID = 1L;
			
			private final Principal principal = new Principal() 
			{
                @Override
                public String getName() 
                {
                    return username;
                }
            };

            @Override
            public Principal getPrincipal() 
            {
                return principal;
            }

            @Override
            public Set<String> getRoles() 
            {
                return Collections.emptySet();
            }

        };
    }
}
