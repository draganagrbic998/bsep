package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Role(Role.Type.EVENT)
@Expires("1m")
public class Log implements HasIpAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Date date;

	@NotNull
    @Enumerated(EnumType.STRING)
	private LogMode mode;
	
	@NotNull
    @Enumerated(EnumType.STRING)
	private LogStatus status;
	
	@NotBlank
	private String ipAddress;

	@NotBlank
	private String description;

	@Override
	public String ipAddress() {
		return this.ipAddress;
	}

}
