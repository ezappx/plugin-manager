package com.ezappx.plugin.configs;

import com.ezappx.plugin.models.Plugin;
import com.ezappx.plugin.models.PluginInfo;
import com.ezappx.plugin.models.PluginType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheConfigTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisWriteTests() {
        PluginInfo info = new PluginInfo();
        info.setName("test-plugin");
        info.setType(PluginType.CORDOVA);
        info.setDescription("desc");
        info.setVersion("1");

        redisTemplate.opsForValue().set("plugin", info);
        Object obj = redisTemplate.opsForValue().get("plugin");
        System.out.println(obj);


    }

    @Test
    public void redisReadTest() {
        Plugin o2 = (Plugin)redisTemplate.opsForValue().get("pluginCache::testplugin@1");
        System.out.println(o2);
    }
}