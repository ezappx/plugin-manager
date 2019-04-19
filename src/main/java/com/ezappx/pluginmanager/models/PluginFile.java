package com.ezappx.pluginmanager.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PluginFile")
public class PluginFile {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    private byte[] content;

    private String md5;
}
