package com.example.demo.dto;

import com.example.demo.model.Alarm;

public class AlarmDTO {

	private long id;
	private Double minPulse;
	private Double maxPulse;
	private Double minPressure;
	private Double maxPressure;
	private Double minTemperature;
	private Double maxTemperature;
	private Double minOxygenLevel;
	private Double maxOxygenLevel;
	
	public AlarmDTO() {
		super();
	}

	public AlarmDTO(Alarm alarm) {
		super();
		this.id = alarm.getId();
		this.minPulse = alarm.getMinPulse();
		this.maxPulse = alarm.getMaxPulse();
		this.minPressure = alarm.getMinPressure();
		this.maxPressure = alarm.getMaxPressure();
		this.minTemperature = alarm.getMinTemperature();
		this.maxTemperature = alarm.getMaxTemperature();
		this.minOxygenLevel = alarm.getMinOxygenLevel();
		this.maxOxygenLevel = alarm.getMaxOxygenLevel();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getMinPulse() {
		return minPulse;
	}

	public void setMinPulse(Double minPulse) {
		this.minPulse = minPulse;
	}

	public Double getMaxPulse() {
		return maxPulse;
	}

	public void setMaxPulse(Double maxPulse) {
		this.maxPulse = maxPulse;
	}

	public Double getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(Double minPressure) {
		this.minPressure = minPressure;
	}

	public Double getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(Double maxPressure) {
		this.maxPressure = maxPressure;
	}

	public Double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public Double getMinOxygenLevel() {
		return minOxygenLevel;
	}

	public void setMinOxygenLevel(Double minOxygenLevel) {
		this.minOxygenLevel = minOxygenLevel;
	}

	public Double getMaxOxygenLevel() {
		return maxOxygenLevel;
	}

	public void setMaxOxygenLevel(Double maxOxygenLevel) {
		this.maxOxygenLevel = maxOxygenLevel;
	}
	
}
