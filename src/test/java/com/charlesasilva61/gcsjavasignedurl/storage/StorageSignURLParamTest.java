package com.charlesasilva61.gcsjavasignedurl.storage;

import java.security.SignatureException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.charlesasilva61.gcsjavasignedurl.storage.StorageSignURLParam.CloudStorageSignURLParamBuilder;
import com.charlesasilva61.gcsjavasignedurl.storage.StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder;

public class StorageSignURLParamTest {

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Test
	public void ifBucketIsNullThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_BUCKET_FILE);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.bucket(null);

		paramBuilder.build().validate();
	}

	@Test
	public void ifFileNameIsNullThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_BUCKET_FILE);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.fileName(null);

		paramBuilder.build().validate();
	}

	@Test
	public void ifBucketIsEmptyThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_BUCKET_FILE);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.bucket("");

		paramBuilder.build().validate();
	}

	@Test
	public void ifFileNameEmptyThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_BUCKET_FILE);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.fileName("");

		paramBuilder.build().validate();
	}

	@Test
	public void ifExpirationEqualsZeroThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_EXPIRATION);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.expirationSecond(0l);

		paramBuilder.build().validate();
	}

	@Test
	public void ifExpirationLessThanZeroThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_EXPIRATION);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.expirationSecond(-1l);

		paramBuilder.build().validate();
	}

	@Test
	public void ifHttpMethodIsNullThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_HTTP_METHOD);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.httpMethod(null);

		paramBuilder.build().validate();
	}

	@Test
	public void ifHttpMethodIsEmptyThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_HTTP_METHOD);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.httpMethod("");

		paramBuilder.build().validate();
	}

	@Test
	public void ifSecurityIsNullThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParam.MSG_REQUIRED_SECURITY);

		CloudStorageSignURLParamBuilder paramBuilder = getFilledParamBuilder();
		paramBuilder.security(null);

		paramBuilder.build().validate();
	}

	private CloudStorageSignURLParamBuilder getFilledParamBuilder() {
		CloudStorageSignURLParamSecurityBuilder securityBuilder = new StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder(
				"password", "account@email.com");
		securityBuilder.pKeyFileName("pKeyFileName");

		CloudStorageSignURLParamBuilder cloudStorageSignURLParamBuilder = new StorageSignURLParam.CloudStorageSignURLParamBuilder(
				securityBuilder.build());
		cloudStorageSignURLParamBuilder.bucket("my-bucket").fileName("my-file").contentType("content\type")
				.expirationSecond(1000l).httpMethod("GET");
		return cloudStorageSignURLParamBuilder;
	}
}
