package com.ezappx.plugin.services;

import com.ezappx.plugin.models.Plugin;
import com.ezappx.plugin.models.PluginFile;
import com.ezappx.plugin.models.PluginInfo;
import com.ezappx.plugin.repositories.PluginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PluginService {

    @Autowired
    private PluginRepository pluginRepository;


    @CachePut(cacheNames = "pluginCache", key = "#info.name + '@' +#info.version")
    public Plugin storePlugin(MultipartFile data, PluginInfo info) throws IOException {

        // if file already in db
        String fileMd5 = DigestUtils.md5DigestAsHex(data.getBytes());
        Optional<Plugin> oldPlugin = pluginRepository.findByMd5(fileMd5);
        if (oldPlugin.isPresent()) {
            // update plugin info based on info
            oldPlugin.get().setInfo(info);
            return pluginRepository.save(oldPlugin.get());
        } else {
            // save new file to db
            PluginFile file = new PluginFile();
            file.setBytes(data.getBytes());

            Plugin plugin = new Plugin();
            plugin.setInfo(info);
            plugin.setData(file);
            plugin.setFileName(StringUtils.getFilename(data.getOriginalFilename()));
            plugin.setMediaType(data.getContentType());
            plugin.setMd5(fileMd5);

            return pluginRepository.save(plugin);
        }
    }

    @Cacheable(cacheNames = "pluginCache", key = "#nameWithVersion", unless = "#result==null")
    public Plugin loadPlugin(String nameWithVersion, String fileName) throws FileNotFoundException {
        String[] nameAndVer = nameWithVersion.split("@");

        if (nameAndVer.length != 2)
            throw new FileNotFoundException("File not found: " + nameWithVersion);

        Plugin plugin = pluginRepository.findByInfoNameAndInfoVersion(nameAndVer[0], nameAndVer[1])
                .orElseThrow(() -> new FileNotFoundException("File not found with fileName " + fileName));

        if (!plugin.getFileName().equals(fileName)) {
            log.debug("user load {} as {}", plugin.getFileName(), fileName);
            plugin.setFileName(fileName);
        }

        return plugin;
    }


    public List<PluginInfo> loadAllPluginInfo() {
        List<Plugin> allPlugin = pluginRepository.findAll();
        return allPlugin.stream().map(Plugin::getInfo).collect(Collectors.toList());
    }
}
