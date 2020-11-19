package com.zucchetti.tomcatarchive.fs.conf;

import java.nio.file.Paths;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.EnvironmentConfiguration;

public class ConfigurationFactory 
{
	public static Config newConfiguration()
	{
		Configuration envConfig = new EnvironmentConfiguration();
		return Config.builder()
				.serviceHost(envConfig.getString("CONF_FS_HOST", "0.0.0.0"))
				.servicePort(envConfig.getInt("CONF_FS_PORT", 8080))
				.pwdFilePath(Paths.get(envConfig.getString("CONF_FS_PWD_PATH", "htpasswd")))
				.dirToBrowsePath(Paths.get(envConfig.getString("CONF_FS_PATH", "")))
				// @see io.undertow.server.handlers.resource.PathResource.serveImpl:
				// task = manager.getTransferMinSize() > Files.size(file) || range ? new ServerTask() : new TransferTask();
				.transfertMinSize(envConfig.getInt("CONF_FS_TRANSFER_MIN_SIZE", 1024))
				.build();
	}
}
