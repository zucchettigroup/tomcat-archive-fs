package com.zucchetti.tomcatarchive.fs;

import static io.undertow.Handlers.resource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import com.zucchetti.tomcatarchive.fs.auth.MapIdentityManager;
import com.zucchetti.tomcatarchive.fs.auth.impl.AuthHtpasswdResourceReader;
import com.zucchetti.tomcatarchive.fs.auth.impl.AuthHtpasswdService;
import com.zucchetti.tomcatarchive.fs.conf.Config;
import com.zucchetti.tomcatarchive.fs.conf.ConfigurationFactory;

import io.undertow.Undertow;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.handlers.SecurityInitialHandler;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

public class FileServer 
{
	private static final String REALM_NAME = "FSRealm";

	public static void main(String...args) throws UnknownHostException
	{
		Config config = ConfigurationFactory.newConfiguration();
		final IdentityManager identityManager = 
				new MapIdentityManager(new AuthHtpasswdService(new AuthHtpasswdResourceReader(config.getPwdFilePath())));

		ResourceHandler resource = resource(new PathResourceManager(config.getDirToBrowsePath(), config.getTransfertMinSize()));
		resource = resource.setDirectoryListingEnabled(true);
		
		Undertow server = Undertow.builder()
				.addHttpListener(config.getServicePort(), config.getServiceHost())
				.setHandler(addSecurity(resource, identityManager, null))
				.build();
		server.start();
		
		System.out.println("POD IP -> " + InetAddress.getLocalHost().getHostAddress());
		System.out.println(config);
	}
	
	private static HttpHandler addSecurity(HttpHandler toWrap, IdentityManager identityManager, Config config) 
	{
		HttpHandler handler = toWrap;
		if (config.requireAuthentication()) 
		{
			handler = new AuthenticationCallHandler(handler);
			handler = new AuthenticationConstraintHandler(handler);
			BasicAuthenticationMechanism authenticationMechanism = new BasicAuthenticationMechanism(REALM_NAME);
			List<AuthenticationMechanism> mechanisms = Collections
					.<AuthenticationMechanism>singletonList(authenticationMechanism);
			handler = new AuthenticationMechanismsHandler(handler, mechanisms);
			handler = new SecurityInitialHandler(AuthenticationMode.PRO_ACTIVE, identityManager, handler);
		}
		return handler;
	}
}
