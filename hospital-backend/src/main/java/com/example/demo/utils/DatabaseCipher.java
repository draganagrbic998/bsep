package com.example.demo.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.example.demo.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseCipher {

	private Cipher cipher;
	private SecretKey key;
	private IvParameterSpec ips;

	public String encrypt(String plainText) {
		byte[] cipherText = null;
		
		if (plainText.isBlank())
			return plainText;
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, ips);
			cipherText = cipher.doFinal(plainText.getBytes());
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public Patient encrypt(Patient p) {
		p.setAddress(this.encrypt(p.getAddress()));
		p.setCity(this.encrypt(p.getCity()));
		p.setFirstName(this.encrypt(p.getFirstName()));
		p.setLastName(this.encrypt(p.getLastName()));
		p.setInsuredNumber(this.encrypt(p.getInsuredNumber()));

		return p;
	}

	public String decrypt(String cipherText) {
		byte[] plainText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, ips);
			plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(plainText);
	}

	public Patient decrypt(Patient p) {
		p.setAddress(this.decrypt(p.getAddress()));
		p.setCity(this.decrypt(p.getCity()));
		p.setFirstName(this.decrypt(p.getFirstName()));
		p.setLastName(this.decrypt(p.getLastName()));
		p.setInsuredNumber(this.decrypt(p.getInsuredNumber()));

		return p;
	}
}
