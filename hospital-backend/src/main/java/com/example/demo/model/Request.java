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
@Expires("1s")
public class Request {

	private boolean login;
	
}
