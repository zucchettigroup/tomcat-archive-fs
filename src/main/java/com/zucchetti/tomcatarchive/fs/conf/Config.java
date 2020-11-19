package com.zucchetti.tomcatarchive.fs.conf;

import java.nio.file.Path;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Config 
{
	private final String serviceHost;
	private final int servicePort;
	private final Path pwdFilePath;
	private final Path dirToBrowsePath;
	private final int transfertMinSize;

	@Generated("SparkTools")
	private Config(Builder builder) {
		this.serviceHost = builder.serviceHost;
		this.servicePort = builder.servicePort;
		this.pwdFilePath = builder.pwdFilePath;
		this.dirToBrowsePath = builder.dirToBrowsePath;
		this.transfertMinSize = builder.transfertMinSize;
	}

	public int getTransfertMinSize() {
		return transfertMinSize;
	}
	public String getServiceHost() {
		return serviceHost;
	}
	public int getServicePort() {
		return servicePort;
	}
	public Path getPwdFilePath() {
		return pwdFilePath.toAbsolutePath();
	}
	public Path getDirToBrowsePath() {
		return dirToBrowsePath.toAbsolutePath();
	}
	/**
	 * Creates builder to build {@link Config}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static IServiceHostStage builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public interface IServiceHostStage {
		public IServicePortStage serviceHost(String serviceHost);
	}
	@Generated("SparkTools")
	public interface IServicePortStage {
		public IPwdFilePathStage servicePort(int servicePort);
	}
	@Generated("SparkTools")
	public interface IPwdFilePathStage {
		public IDirToBrowsePathStage pwdFilePath(Path pwdFilePath);
	}
	@Generated("SparkTools")
	public interface IDirToBrowsePathStage {
		public ITransfertMinSizeStage dirToBrowsePath(Path dirToBrowsePath);
	}
	@Generated("SparkTools")
	public interface ITransfertMinSizeStage {
		public IBuildStage transfertMinSize(int transfertMinSize);
	}
	@Generated("SparkTools")
	public interface IBuildStage {
		public Config build();
	}
	/**
	 * Builder to build {@link Config}.
	 */
	@Generated("SparkTools")
	public static final class Builder implements IServiceHostStage, IServicePortStage, IPwdFilePathStage,
	IDirToBrowsePathStage, ITransfertMinSizeStage, IBuildStage {
		private String serviceHost;
		private int servicePort;
		private Path pwdFilePath;
		private Path dirToBrowsePath;
		private int transfertMinSize;

		private Builder() {
		}

		@Override
		public IServicePortStage serviceHost(String serviceHost) {
			this.serviceHost = serviceHost;
			return this;
		}

		@Override
		public IPwdFilePathStage servicePort(int servicePort) {
			this.servicePort = servicePort;
			return this;
		}

		@Override
		public IDirToBrowsePathStage pwdFilePath(Path pwdFilePath) {
			this.pwdFilePath = pwdFilePath;
			return this;
		}

		@Override
		public ITransfertMinSizeStage dirToBrowsePath(Path dirToBrowsePath) {
			this.dirToBrowsePath = dirToBrowsePath;
			return this;
		}

		@Override
		public IBuildStage transfertMinSize(int transfertMinSize) {
			this.transfertMinSize = transfertMinSize;
			return this;
		}

		@Override
		public Config build() {
			return new Config(this);
		}
	}
	
	@Override
	public String toString() 
	{
		ToStringBuilder toString = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		toString.append("serviceHost", serviceHost);
		toString.append("servicePort", servicePort);
		toString.append("pwdFilePath", getPwdFilePath());
		toString.append("dirToBrowsePath", getDirToBrowsePath());
		toString.append("transfertMinSize", transfertMinSize);
		return toString.toString();
	}	
}