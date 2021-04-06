package com.example.demo.utils;

import java.io.File;

public class Constants {

	public static final String FRONTEND = "https://localhost:4200";
	public static final String RESOURCE_FOLDER = "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar;
	public static final String CERTIFICATES_FOLDER = "certificates" + File.separatorChar;
	
	public static final String ISSUED_TEMPLATE = "certificate-issued";
	public static final String REVOKED_TEMPLATE = "certificate-revoked";
		
	public static final String CERT_ATTRIBUTE = "javax.servlet.request.X509Certificate";
	public static final String KEYSTORE_PATH = "src/main/resources/certificates/root_super_super.jks";
	
}
