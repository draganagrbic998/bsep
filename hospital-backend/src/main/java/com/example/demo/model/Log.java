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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Role(Role.Type.EVENT)
@Expires("1m")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Date date;

	@NotNull
	private boolean normal;
	
	@NotNull
    @Enumerated(EnumType.STRING)
	private LogStatus status;
	
	@NotBlank
	private String description;

	@NotBlank
	private String userName;

	@NotBlank
	private String computerName;

	@NotBlank
	private String serviceName;

}
