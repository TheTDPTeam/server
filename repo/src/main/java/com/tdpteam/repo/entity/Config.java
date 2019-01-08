package com.tdpteam.repo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
@Getter
@Setter
public class Config {
    @Id
    @Column
    private int id;

    @Column
    private String key;

    @Column
    private String value;
}
