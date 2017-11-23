package com.charlesasilva61.gcsjavasignedurl.storage;

import java.io.InputStream;

/**
 * Class that contains information about security access.
 * 
 */
public final class StorageSignURLParamSecurity {

	protected static final String MSG_REQUIRED_FILE_OR_FILENAME = "The name of file or the file is required to read the security information. (PKeyFileName or PKeyFile).";
	protected static final String MSG_REQUIRED_GOOGLE_ACCESS_ID = "The id of account is required.";
	protected static final String MSG_REQUIRED_PASSWORD = "The password of key file is required.";
	protected static final String MSG_FILE_OR_FILENAME_TOGETHER = "The name of file and the file can't be fill together.";

	private String pKeyFileName;
	private InputStream pKeyFile;
	private String pKeyPassword;
	private String googleAccessId;

	public StorageSignURLParamSecurity(CloudStorageSignURLParamSecurityBuilder builder) {
		this.pKeyFileName = builder.pKeyFileName;
		this.pKeyFile = builder.pKeyFile;
		this.pKeyPassword = builder.pKeyPassword;
		this.googleAccessId = builder.googleAccessId;
	}

	public InputStream getPKeyFile() {
		return pKeyFile;
	}

	public String getPKeyFileName() {
		return pKeyFileName;
	}

	public String getPKeyPassword() {
		return pKeyPassword;
	}

	public char[] getPKeyPasswordAsCharArray() {
		return pKeyPassword.toCharArray();
	}

	public String getGoogleAccessId() {
		return googleAccessId;
	}

	public static class CloudStorageSignURLParamSecurityBuilder {
		private String pKeyFileName;
		private InputStream pKeyFile;
		private String pKeyPassword;
		private String googleAccessId;

		public CloudStorageSignURLParamSecurityBuilder(String pKeyPassword, String googleAccessId) {
			this.pKeyPassword = pKeyPassword;
			this.googleAccessId = googleAccessId;
		}

		public CloudStorageSignURLParamSecurityBuilder pKeyPassword(String pKeyPassword) {
			this.pKeyPassword = pKeyPassword;
			return this;
		}

		public CloudStorageSignURLParamSecurityBuilder googleAccessId(String googleAccessId) {
			this.googleAccessId = googleAccessId;
			return this;
		}

		public CloudStorageSignURLParamSecurityBuilder pKeyFileName(String pKeyFileName) {
			this.pKeyFileName = pKeyFileName;
			return this;
		}

		public CloudStorageSignURLParamSecurityBuilder pKeyFile(InputStream pKeyFile) {
			this.pKeyFile = pKeyFile;
			return this;
		}

		public StorageSignURLParamSecurity build() {
			return new StorageSignURLParamSecurity(this);
		}
	}

	protected void validate() {
		if (isNullOrEmpty(pKeyFileName) && isNull(pKeyFile)) {
			throw new IllegalArgumentException(MSG_REQUIRED_FILE_OR_FILENAME);
		}

		if (!isNullOrEmpty(pKeyFileName) && !isNull(pKeyFile)) {
			throw new IllegalArgumentException(MSG_FILE_OR_FILENAME_TOGETHER);
		}

		if (isNullOrEmpty(googleAccessId)) {
			throw new IllegalArgumentException(MSG_REQUIRED_GOOGLE_ACCESS_ID);
		}

		if (isNullOrEmpty(pKeyPassword)) {
			throw new IllegalArgumentException(MSG_REQUIRED_PASSWORD);
		}
	}

	private boolean isNull(Object object) {
		return object == null;
	}

	private boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

}
