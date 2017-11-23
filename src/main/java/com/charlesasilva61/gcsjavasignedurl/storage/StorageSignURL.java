package com.charlesasilva61.gcsjavasignedurl.storage;

import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

public class StorageSignURL {

	private static final int MILISECONDS = 1000;

	private static final String CHARSET_NAME = "UTF-8";

	protected static final String MSG_REQUIRED_PARAM = "Param is required.";

	/**
	 * Get the url already signed.
	 * 
	 * @param param
	 *            the parameters
	 * @return the url
	 * @throws SignatureException
	 *             if any error to generate URL.
	 */
	public String getSignedURL(StorageSignURLParam param) throws SignatureException {
		if (isNull(param)) {
			throw new IllegalArgumentException(MSG_REQUIRED_PARAM);
		}

		param.validate();
		param.getSecurity().validate();

		long expirationTime = createExpirationTime(param);

		String stringToSign = String.format("%s%n%n%s%n%s%n/%s/%s", param.getHttpMethod(), param.getContentType(),
				expirationTime, param.getBucket(), param.getFileName());

		try {
			String signedString = signString(stringToSign, param.getSecurity());

			return String.format("https://storage.googleapis.com/%s/%s?GoogleAccessId=%s&Expires=%s&Signature=%s",
					param.getBucket(), param.getFileName(), param.getSecurity().getGoogleAccessId(), expirationTime,
					URLEncoder.encode(signedString, CHARSET_NAME));
		} catch (Exception e) {
			throw new SignatureException("Fail to generate signed URL.", e);
		}
	}

	protected long createExpirationTime(StorageSignURLParam param) {
		long now = System.currentTimeMillis();
		return (now + param.getExpirationSecond() * MILISECONDS) / MILISECONDS;
	}

	public String signString(String stringToSign, StorageSignURLParamSecurity cloudStorageSignSecurity)
			throws Exception {
		PrivateKey key = loadKeyFromPkcs12(cloudStorageSignSecurity);

		Signature signer = Signature.getInstance("SHA256withRSA");
		signer.initSign(key);
		signer.update(stringToSign.getBytes(CHARSET_NAME));
		return new String(Base64.encodeBase64(signer.sign(), false), CHARSET_NAME);
	}

	private PrivateKey loadKeyFromPkcs12(StorageSignURLParamSecurity security) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12");

		if (security.getPKeyFile() != null) {
			ks.load(security.getPKeyFile(), security.getPKeyPasswordAsCharArray());
		} else {
			ks.load(getFile(security.getPKeyFileName()), security.getPKeyPasswordAsCharArray());
		}

		return ((PrivateKey) ks.getKey("privatekey", security.getPKeyPasswordAsCharArray()));
	}

	private InputStream getFile(String fileName) {
		InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (isNull(file)) {
			throw new IllegalArgumentException("File Key not found, verify your fileName.");
		}
		return file;
	}

	private boolean isNull(Object object) {
		return object == null;
	}
}