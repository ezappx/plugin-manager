package com.ezappx.plugin.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PluginInfo")
public class Plugin {
    @Id
    @GeneratedValue
    private Long id;

    private String md5;

    private String fileName;

    private String mediaType;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PluginFile data;

    private PluginInfo info;
}
