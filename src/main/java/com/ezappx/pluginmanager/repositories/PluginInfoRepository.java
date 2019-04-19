package com.ezappx.pluginmanager.repositories;

import com.ezappx.pluginmanager.models.PluginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginInfoRepository extends JpaRepository<PluginInfo, String> {
    public PluginInfo findByPluginName(String pluginName);
}
