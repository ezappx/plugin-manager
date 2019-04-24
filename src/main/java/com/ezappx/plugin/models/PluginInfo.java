package com.ezappx.plugin.models;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
public class PluginInfo {
    private String name;

    private String version;

    @Enumerated(EnumType.STRING)
    private PluginType type;

    private String description;
}
