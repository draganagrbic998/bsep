package com.example.demo.model;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Role(Role.Type.EVENT)
@Expires("10s")
public class Attack {

}
