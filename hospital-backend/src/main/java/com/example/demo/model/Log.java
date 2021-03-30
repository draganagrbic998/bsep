package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "log_table")
@Role(Role.Type.EVENT)
@Expires("1m")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "date")
	private Date date;

	@NotNull
	@Column(name = "normal")
	private boolean normal;
	
	@NotNull
	@Column(name = "status")
	private String status;
	
	@NotBlank
	@Column(name = "description")
	private String description;

	@NotBlank
	@Column(name = "userName")
	private String userName;

	@NotBlank
	@Column(name = "computerName")
	private String computerName;

	@NotBlank
	@Column(name = "serviceName")
	private String serviceName;

}
