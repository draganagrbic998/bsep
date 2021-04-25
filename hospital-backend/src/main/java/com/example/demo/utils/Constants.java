package com.example.demo.utils;

import java.io.File;

public class Constants {

	public static final String FRONTEND = "https://localhost:4201";
	public static final String BACKEND = "https://localhost:8081";
	public static final String IDENTITY_BACKEND = "https://localhost:8083";
	public static final String ADMIN_BACKEND = "https://localhost:8080";

	public static final String RESOURCES_FOLDER = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
	public static final String CERTIFICATES_FOLDER = RESOURCES_FOLDER + "certificates" + File.separator;
	public static final String CONFIGURATION_FILE = RESOURCES_FOLDER + "configuration.json";

	public static final String ADMIN_ALARMS = "admin-alarms";
	public static final String DOCTOR_ALARMS = "doctor-alarms";
	public static final String COMMON_ALARMS = "common-alarms";

	public static final String CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";
	public static final String AUTHORIZATION_HEADER = "Authorization";

}
