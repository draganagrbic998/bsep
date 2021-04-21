package com.example.demo.utils;

import java.io.File;

public class Constants {

	public static final String FRONTEND = "https://localhost:4200";
	
	public static final String RESOURCES_FOLDER = "src" + File.separatorChar + "main" + File.separatorChar + "resources" + File.separatorChar;
	public static final String CERTIFICATES_FOLDER = RESOURCES_FOLDER + "certificates" + File.separatorChar;
	
	public static final String ISSUED_TEMPLATE = "certificate-issued";
	public static final String REVOKED_TEMPLATE = "certificate-revoked";
		
	public static final String KEYSTORE_PATH = CERTIFICATES_FOLDER + "root_super_super.jks";
	public static final String CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
}
