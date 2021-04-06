package com.example.demo.utils;

import java.io.File;

public class Constants {

	public static final String BACKEND = "https://localhost:8081";
	public static final String FRONTEND = "https://localhost:4201";
	public static final String IDENTITY_BACKEND = "https://localhost:8083";
	
	public static final String ADMIN_BACKEND = "https://localhost:8080";
	public static final String ADMIN_ALARMS = "admin-alarms";
	public static final String DOCTOR_ALARMS = "doctor-alarms";

	public static final String RESOURCES_FOLDER = "." + File.separator + "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator;
	public static final String CERTIFICATES_FOLDER = RESOURCES_FOLDER + "certificates" + File.separator;
	public static final String KEYSTORE_PATH = RESOURCES_FOLDER + "keystore.jks";
	
	public static final String CERTIFICATES_PATH = Constants.ADMIN_BACKEND + "/api/certificates/";
	public static final String REQUESTS_PATH = Constants.ADMIN_BACKEND + "/api/requests/";

	public static final String CERT_ATTRIBUTE = "javax.servlet.request.X509Certificate";

}
