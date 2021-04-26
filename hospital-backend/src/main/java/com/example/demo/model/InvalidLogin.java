package com.example.demo.model;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Role(Role.Type.EVENT)
@Expires("24h")
public class InvalidLogin implements HasIpAddress{

	private String ipAddress;

	@Override
	public String ipAddress() {
		return this.ipAddress;
	}
	
}
