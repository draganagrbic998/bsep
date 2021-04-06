package com.example.demo.utils;

import java.io.File;

public class Constants {

	public static final String FRONTEND = "https://localhost:4200";
	public static final String CERTIFICATES_FOLDER = "certificates/";
	public static final String CERTIFICATE_SAVE_PATH = "/api/certificates";

	public static final String CERTIFICATE_ISSUED = "Certificate Issued - Bezbednost";
	public static final String ISSUED_TEMPLATE = "certificate-issued";

	public static final String CERTIFICATE_REVOKED = "Certificate Revoked - Bezbednost";
	public static final String REVOKED_TEMPLATE = "certificate-revoked";
	
	public static final String REVOKE_REQUEST_REASON = "Revocation requested by hospital admin.";
	
	public static final String CERT_ATTRIBUTE = "javax.servlet.request.X509Certificate";
	public static final String RESOURCE_FOLDER = "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar;

	public static final String TRUSTSTORE_PATH = "src/main/resources/certificates/root_super_super.jks";
}
