package com.ezappx.plugin.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PluginFile")
public class PluginFile {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "data")
    private Plugin plugin;

    private byte[] bytes;
}
