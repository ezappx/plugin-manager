package com.ezappx.plugin.repositories;

import com.ezappx.plugin.models.Plugin;
import com.ezappx.plugin.models.PluginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, PluginInfo> {
    Optional<Plugin> findByMd5(String md5);

    Optional<Plugin> findByInfoNameAndInfoVersion(String name, String version);
}
