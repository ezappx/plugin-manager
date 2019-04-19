package com.ezappx.pluginmanager.controllers;

import com.ezappx.pluginmanager.models.PluginInfo;
import com.ezappx.pluginmanager.models.PluginFile;
import com.ezappx.pluginmanager.services.PluginServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/v1/plugin")
public class PluginController {
    @Autowired
    private PluginServices pluginServices;

    @PostMapping("/upload/file")
    public String UploadFile(@RequestParam("file") MultipartFile file) {
        try {
            PluginFile pluginFile = pluginServices.storePluginFile(file);
            return pluginFile.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @PostMapping("/upload/info")
    public String uploadPlugInfo(@RequestBody PluginInfo pluginInfo) {
        return pluginServices.storePluginInfo(pluginInfo).getId().toString();
    }
}
