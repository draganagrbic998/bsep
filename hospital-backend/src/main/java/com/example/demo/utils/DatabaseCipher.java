package com.example.demo.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.Message;
import com.example.demo.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseCipher {

	private Cipher cipher;
	private SecretKey key;
	private IvParameterSpec ips;

	public String encrypt(String plainText) {		
		if (plainText.isBlank()) return plainText;
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ips);
			return Base64.getEncoder().encodeToString(this.cipher.doFinal(plainText.getBytes()));
		} 
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public String decrypt(String cipherText) {
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, this.key, this.ips);
			return new String(this.cipher.doFinal(Base64.getDecoder().decode(cipherText)));
		} 
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	public Patient encrypt(Patient patient) {
		patient.setInsuredNumber(this.encrypt(patient.getInsuredNumber()));
		patient.setFirstName(this.encrypt(patient.getFirstName()));
		patient.setLastName(this.encrypt(patient.getLastName()));
		patient.setAddress(this.encrypt(patient.getAddress()));
		patient.setCity(this.encrypt(patient.getCity()));
		return patient;
	}

	public Patient decrypt(Patient patient) {
		patient.setInsuredNumber(this.decrypt(patient.getInsuredNumber()));
		patient.setFirstName(this.decrypt(patient.getFirstName()));
		patient.setLastName(this.decrypt(patient.getLastName()));
		patient.setAddress(this.decrypt(patient.getAddress()));
		patient.setCity(this.decrypt(patient.getCity()));
		return patient;
	}

	public Message decrypt(Message message) {
		Patient patient = new Patient();
		patient.setInsuredNumber(this.decrypt(message.getPatient().getInsuredNumber()));
		patient.setFirstName(this.decrypt(message.getPatient().getFirstName()));
		patient.setLastName(this.decrypt(message.getPatient().getLastName()));
		message.setPatient(patient);
		return message;
	}

	public AlarmTriggering decrypt(AlarmTriggering alarmTriggering) {
		Patient patient = new Patient();
		patient.setInsuredNumber(this.decrypt(alarmTriggering.getPatient().getInsuredNumber()));
		patient.setFirstName(this.decrypt(alarmTriggering.getPatient().getFirstName()));
		patient.setLastName(this.decrypt(alarmTriggering.getPatient().getLastName()));
		alarmTriggering.setPatient(patient);
		return alarmTriggering;
	}
	
}
