package com.ezappx.pluginmanager.repositories;

import com.ezappx.pluginmanager.models.PluginFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PluginFileRepository extends JpaRepository<PluginFile, String> {
    PluginFile findByMd5(String md5);
}
