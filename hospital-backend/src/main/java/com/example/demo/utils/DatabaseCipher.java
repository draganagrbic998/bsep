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
			this.cipher.init(Cipher.ENCRYPT_MODE, key, ips);
			cipherText = this.cipher.doFinal(plainText.getBytes());
		} 
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public Patient encrypt(Patient patient) {
		patient.setAddress(this.encrypt(patient.getAddress()));
		patient.setCity(this.encrypt(patient.getCity()));
		patient.setFirstName(this.encrypt(patient.getFirstName()));
		patient.setLastName(this.encrypt(patient.getLastName()));
		patient.setInsuredNumber(this.encrypt(patient.getInsuredNumber()));
		return patient;
	}

	public String decrypt(String cipherText) {
		byte[] plainText = null;
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, key, ips);
			plainText = this.cipher.doFinal(Base64.getDecoder().decode(cipherText));
		} 
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return new String(plainText);
	}

	public Patient decrypt(Patient patient) {
		patient.setAddress(this.decrypt(patient.getAddress()));
		patient.setCity(this.decrypt(patient.getCity()));
		patient.setFirstName(this.decrypt(patient.getFirstName()));
		patient.setLastName(this.decrypt(patient.getLastName()));
		patient.setInsuredNumber(this.decrypt(patient.getInsuredNumber()));
		return patient;
	}
	
}
