package com.example.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.example.demo.utils.CipherProperties;
import com.example.demo.utils.DatabaseCipher;
import com.example.demo.utils.PkiProperties;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class DatabaseCiperConfig {

	private final PkiProperties pkiProperties;
	private final CipherProperties cipherProperties;

	@Bean
	public DatabaseCipher getDatabaseCipher() {
		DatabaseCipher databaseCipher = null;
		SecretKey key = null;

		try {
			File file = new File(this.cipherProperties.getDbKeystorePath());
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			InputStream in = new FileInputStream(file);
			keyStore.load(in, this.pkiProperties.getKeystorePassword().toCharArray());
			key = (SecretKey) keyStore.getKey("databaseKey", this.pkiProperties.getKeystorePassword().toCharArray());
			in.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		if (key == null) {
			try {
				KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(256);
				key = keyGenerator.generateKey();

				File file = new File(this.cipherProperties.getDbKeystorePath());
				file.createNewFile();

				KeyStore ks = KeyStore.getInstance("JCEKS");
				ks.load(null, this.pkiProperties.getKeystorePassword().toCharArray());

				KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(
						this.pkiProperties.getKeystorePassword().toCharArray());

				KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);
				ks.setEntry("databaseKey", skEntry, protParam);

				ks.store(new FileOutputStream(file), this.pkiProperties.getKeystorePassword().toCharArray());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		IvParameterSpec ips = null;

		try {
			File file = ResourceUtils.getFile(this.cipherProperties.getIpsPath());
			InputStream in = new FileInputStream(file);
			ips = new IvParameterSpec(in.readAllBytes());
			in.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		if (ips == null) {
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			ips = new IvParameterSpec(iv);

			File file = new File(this.cipherProperties.getIpsPath());
			FileOutputStream out = null;

			try {
				file.createNewFile();
				out = new FileOutputStream(file);
				out.write(iv);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			databaseCipher = new DatabaseCipher(cipher, key, ips);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return databaseCipher;
	}

}
