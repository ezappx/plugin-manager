package com.ezappx.pluginmanager.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PluginInfo")
public class PluginInfo {
    @Id
    @GeneratedValue
    private Long id;

    private String pluginName;

    private String version;

    private String pluginType;

    private String fileId;
}
