package com.ezappx.plugin.services;

import com.ezappx.plugin.models.Plugin;
import com.ezappx.plugin.models.PluginFile;
import com.ezappx.plugin.models.PluginInfo;
import com.ezappx.plugin.models.PluginType;
import com.ezappx.plugin.repositories.PluginRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PluginServiceTest {

    @Autowired
    private PluginRepository repository;

    @Test
    public void insertPlugin() {
        PluginFile file = new PluginFile();
        byte[] data = new byte[1024];
        new Random().nextBytes(data);
        file.setBytes(data);

        PluginInfo info = new PluginInfo();
        info.setDescription("test desc");
        info.setType(PluginType.CORDOVA);
        info.setVersion("1");
        info.setName("test plugin");

        Plugin plugin = new Plugin();
        plugin.setData(file);
        plugin.setInfo(info);
        plugin.setMediaType("js");
        plugin.setFileName("test.js");
        plugin.setMd5("123");

        repository.save(plugin);
    }

}