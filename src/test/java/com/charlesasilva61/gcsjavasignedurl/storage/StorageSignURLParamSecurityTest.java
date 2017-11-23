package com.charlesasilva61.gcsjavasignedurl.storage;

import java.io.ByteArrayInputStream;
import java.security.SignatureException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.charlesasilva61.gcsjavasignedurl.storage.StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder;

public class StorageSignURLParamSecurityTest {

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Test
	public void ifPKeyFileNameIsNotPresentAndPKeyFileIsNotPresentThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParamSecurity.MSG_REQUIRED_FILE_OR_FILENAME);

		CloudStorageSignURLParamSecurityBuilder securityBuilder = getFilledParamSecurityBuilder();
		securityBuilder.pKeyFile(null).pKeyFileName(null);

		securityBuilder.build().validate();
	}

	@Test
	public void ifPKeyFileNameIsPresentAndPKeyFileIsPresentThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParamSecurity.MSG_FILE_OR_FILENAME_TOGETHER);

		CloudStorageSignURLParamSecurityBuilder securityBuilder = getFilledParamSecurityBuilder();
		securityBuilder.pKeyFile(new ByteArrayInputStream("The File".getBytes())).pKeyFileName("fileName");

		securityBuilder.build().validate();
	}

	@Test
	public void ifGoogleAccessIdIsNotPresentThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParamSecurity.MSG_REQUIRED_GOOGLE_ACCESS_ID);

		CloudStorageSignURLParamSecurityBuilder securityBuilder = getFilledParamSecurityBuilder();
		securityBuilder.googleAccessId("");

		securityBuilder.build().validate();
	}

	@Test
	public void ifPKeyPasswordIsNotPresentThrowException() throws SignatureException {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(StorageSignURLParamSecurity.MSG_REQUIRED_PASSWORD);

		CloudStorageSignURLParamSecurityBuilder securityBuilder = getFilledParamSecurityBuilder();
		securityBuilder.pKeyPassword("");

		securityBuilder.build().validate();
	}

	private CloudStorageSignURLParamSecurityBuilder getFilledParamSecurityBuilder() {
		CloudStorageSignURLParamSecurityBuilder securityBuilder = new StorageSignURLParamSecurity.CloudStorageSignURLParamSecurityBuilder(
				"password", "account@email.com");
		securityBuilder.pKeyFileName("pKeyFileName");
		return securityBuilder;
	}
}
