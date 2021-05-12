package com.example.demo.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.example.demo.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseCipher {

	private Cipher cipher;
	private SecretKey key;
	private IvParameterSpec ips;

	public String encrypt(String plainText) {
		if (plainText == null || plainText.isBlank())
			return plainText;
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ips);
			return Base64.getEncoder().encodeToString(this.cipher.doFinal(plainText.getBytes()));
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public String decrypt(String cipherText) {
		if (cipherText == null || cipherText.isBlank())
			return cipherText;
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, this.key, this.ips);
			return new String(this.cipher.doFinal(Base64.getDecoder().decode(cipherText)));
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public User encrypt(User user) {
		user.setFirstName(this.encrypt(user.getFirstName()));
		user.setLastName(this.encrypt(user.getLastName()));
		return user;
	}

	public User decrypt(User user) {
		user.setFirstName(this.decrypt(user.getFirstName()));
		user.setLastName(this.decrypt(user.getLastName()));
		return user;
	}

}
