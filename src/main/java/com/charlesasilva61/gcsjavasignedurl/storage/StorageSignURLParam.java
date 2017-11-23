package com.charlesasilva61.gcsjavasignedurl.storage;

public final class StorageSignURLParam {

	protected static final String MSG_REQUIRED_BUCKET_FILE = "The name of the bucket and file is required.";
	protected static final String MSG_REQUIRED_EXPIRATION = "The time of expiration must be greater than 0.";
	protected static final String MSG_REQUIRED_HTTP_METHOD = "The HTTP Method is required.";
	protected static final String MSG_REQUIRED_SECURITY = "The security param is required.";

	private String httpMethod;
	private String contentType;
	private long expirationSecond;
	private String bucket;
	private String fileName;
	private StorageSignURLParamSecurity security;

	private StorageSignURLParam(CloudStorageSignURLParamBuilder builder) {
		this.httpMethod = builder.httpMethod;
		this.contentType = builder.contentType;
		this.expirationSecond = builder.expirationSecond;
		this.bucket = builder.bucket;
		this.fileName = builder.fileName;
		this.security = builder.security;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getContentType() {
		return contentType;
	}

	public long getExpirationSecond() {
		return expirationSecond;
	}

	public String getBucket() {
		return bucket;
	}

	public String getFileName() {
		return fileName;
	}

	public StorageSignURLParamSecurity getSecurity() {
		return security;
	}

	public static class CloudStorageSignURLParamBuilder {
		private String httpMethod;
		private String contentType;
		private long expirationSecond;
		private String bucket;
		private String fileName;
		private StorageSignURLParamSecurity security;

		public CloudStorageSignURLParamBuilder(StorageSignURLParamSecurity security) {
			this.security = security;
		}

		public CloudStorageSignURLParamBuilder httpMethod(String httpMethod) {
			this.httpMethod = httpMethod;
			return this;
		}

		public CloudStorageSignURLParamBuilder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public CloudStorageSignURLParamBuilder expirationSecond(Long expirationSecond) {
			this.expirationSecond = expirationSecond;
			return this;
		}

		public CloudStorageSignURLParamBuilder bucket(String bucket) {
			this.bucket = bucket;
			return this;
		}

		public CloudStorageSignURLParamBuilder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public CloudStorageSignURLParamBuilder security(StorageSignURLParamSecurity security) {
			this.security = security;
			return this;
		}

		public StorageSignURLParam build() {
			return new StorageSignURLParam(this);
		}

	}

	protected void validate() {
		if (security == null) {
			throw new IllegalArgumentException(MSG_REQUIRED_SECURITY);
		}

		if (isNullOrEmpty(bucket) || isNullOrEmpty(fileName)) {
			throw new IllegalArgumentException(MSG_REQUIRED_BUCKET_FILE);
		}

		if (expirationSecond <= 0) {
			throw new IllegalArgumentException(MSG_REQUIRED_EXPIRATION);
		}

		if (isNullOrEmpty(httpMethod)) {
			throw new IllegalArgumentException(MSG_REQUIRED_HTTP_METHOD);
		}

	}

	private boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
}
