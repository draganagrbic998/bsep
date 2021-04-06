package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Extensions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean basicConstraints; // can be null as well

    @ElementCollection
    private List<String> keyPurposeIds;

    private int keyUsage;
}
