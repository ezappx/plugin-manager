package com.ezappx.plugin.controllers;

import com.ezappx.plugin.models.Plugin;
import com.ezappx.plugin.models.PluginInfo;
import com.ezappx.plugin.services.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/plugin")
public class PluginController {
    @Autowired
    private PluginService pluginServices;

    /**
     * 上传插件
     *
     * @param file
     * @param info
     * @return
     */
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public PluginInfo uploadPlugin(@RequestPart("file") MultipartFile file,
                               @RequestPart("info") PluginInfo info) {
        Plugin plugin = null;
        try {
            plugin = pluginServices.storePlugin(file, info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plugin.getInfo();
    }

    /**
     * /插件名称@版本号/文件名
     */
    @GetMapping("/{nameWithVersion}/{fileName}")
    public ResponseEntity<Resource> loadPlugin(@PathVariable("nameWithVersion") String nameWithVersion,
                                               @PathVariable("fileName") String fileName) {
        try {
            Plugin plugin = pluginServices.loadPlugin(nameWithVersion, fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(plugin.getMediaType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + plugin.getFileName() + "\"")
                    .body(new ByteArrayResource(plugin.getData().getBytes()));
        } catch (FileNotFoundException e) {
            log.error(e.toString());
            return null;
        }
    }

    @GetMapping("/info/all")
    public List<PluginInfo> loadAllPluginInfo() {
        return pluginServices.loadAllPluginInfo();
    }
}
