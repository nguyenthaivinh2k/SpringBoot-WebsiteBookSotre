package com.example.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "configs")
public class Config {
    @Id
    @Column(name = "config_id")
    private Long id;
    private String name;
    private String value;

}
