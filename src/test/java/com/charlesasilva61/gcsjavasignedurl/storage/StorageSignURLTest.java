package com.charlesasilva61.gcsjavasignedurl.storage;

import java.io.IOException;
import java.security.SignatureException;
import java.util.logging.Logger;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.charlesasilva61.gcsjavasignedurl.storage.StorageSignURLParam.CloudStorageSignURLParamBuilder;
import com.charlesasilva61.gcsjavasignedurl.storage.StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder;

public class StorageSignURLTest {

	private static final String VALID_BUCKET = "my-bucket";

	private static final String VALID_GOOGLE_ACCOUNT_ID = "my-account";

	private static final String VALID_PASSOWRD = "validPassowrd";

	private final static Logger LOGGER = Logger.getLogger(StorageSignURLTest.class.getName());
	private static final int STATUS_CODE_SUCCESS = 200;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Test
	public void uploadIfAllIsOkReturnSuccessCode() throws SignatureException, ClientProtocolException, IOException {
		StorageSignURL cssURL = new StorageSignURL();

		CloudStorageSignURLParamSecurityBuilder securityBuild = new StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder(
				VALID_PASSOWRD, VALID_GOOGLE_ACCOUNT_ID);

		securityBuild.pKeyFileName("my-pk.p12");

		CloudStorageSignURLParamBuilder paramBuilder = new StorageSignURLParam.CloudStorageSignURLParamBuilder(
				securityBuild.build());

		paramBuilder.bucket(VALID_BUCKET).contentType("text/plain").expirationSecond(1000l).fileName("my-file.txt")
				.httpMethod("PUT");

		StorageSignURLParam paramSign = paramBuilder.build();
		String signedURL = cssURL.getSignedURL(paramSign);

		LOGGER.info(String.format("Signed URL => %s", signedURL));

		StatusLine statusLine = Request.Put(signedURL).addHeader("Content-Type", paramSign.getContentType())
				.bodyByteArray("My content!".getBytes()).execute().returnResponse().getStatusLine();

		LOGGER.info(String.format("Response Message => %s", statusLine.getReasonPhrase()));

		Assert.assertThat(statusLine.getStatusCode(), Matchers.equalTo(STATUS_CODE_SUCCESS));
	}

	@Test
	public void downloadIfAllIsOkReturnSuccessCode() throws SignatureException, ClientProtocolException, IOException {
		StorageSignURL cssURL = new StorageSignURL();

		CloudStorageSignURLParamSecurityBuilder securityBuild = new StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder(
				"notasecret", VALID_GOOGLE_ACCOUNT_ID);

		securityBuild.pKeyFileName("my-pk.p12");

		CloudStorageSignURLParamBuilder paramBuilder = new StorageSignURLParam.CloudStorageSignURLParamBuilder(
				securityBuild.build());

		paramBuilder.bucket(VALID_BUCKET).contentType("text/plain").expirationSecond(1000l).fileName("my-file.txt")
				.httpMethod("GET");

		StorageSignURLParam paramSign = paramBuilder.build();
		String signedURL = cssURL.getSignedURL(paramSign);

		LOGGER.info(String.format("Signed URL => %s", signedURL));

		StatusLine statusLine = Request.Get(signedURL).addHeader("Content-Type", paramSign.getContentType()).execute()
				.returnResponse().getStatusLine();

		LOGGER.info(String.format("Response Message => %s", statusLine.getReasonPhrase()));

		Assert.assertThat(statusLine.getStatusCode(), Matchers.equalTo(STATUS_CODE_SUCCESS));
	}

	@Test
	public void operationWithInvalidFileThowException()
			throws SignatureException, ClientProtocolException, IOException {

		expectedException.expect(SignatureException.class);

		StorageSignURL cssURL = new StorageSignURL();

		CloudStorageSignURLParamSecurityBuilder securityBuild = new StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder(
				"invalidPassword", VALID_GOOGLE_ACCOUNT_ID);

		securityBuild.pKeyFileName("my-pk.p12");

		CloudStorageSignURLParamBuilder paramBuilder = new StorageSignURLParam.CloudStorageSignURLParamBuilder(
				securityBuild.build());

		paramBuilder.bucket(VALID_BUCKET).contentType("text/plain").expirationSecond(1000l).fileName("my-file.txt")
				.httpMethod("GET");

		StorageSignURLParam paramSign = paramBuilder.build();
		String signedURL = cssURL.getSignedURL(paramSign);

		LOGGER.info(String.format("Signed URL => %s", signedURL));

		StatusLine statusLine = Request.Get(signedURL).addHeader("Content-Type", paramSign.getContentType()).execute()
				.returnResponse().getStatusLine();

		LOGGER.info(String.format("Response Message => %s", statusLine.getReasonPhrase()));

		Assert.assertThat(statusLine.getStatusCode(), Matchers.equalTo(STATUS_CODE_SUCCESS));
	}
}
