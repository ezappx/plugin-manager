package com.ezappx.pluginmanager.services;

import com.ezappx.pluginmanager.models.PluginFile;
import com.ezappx.pluginmanager.models.PluginInfo;
import com.ezappx.pluginmanager.repositories.PluginFileRepository;
import com.ezappx.pluginmanager.repositories.PluginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PluginServices {

    @Autowired
    PluginFileRepository pluginFileRepository;

    @Autowired
    PluginInfoRepository pluginInfoRepository;


//    public PluginFile getPluginFile(String fileId) throws FileNotFoundException {
//        return pluginFileRepository.findById(fileId).orElseThrow(
//                () -> new FileNotFoundException("File not found with id " + fileId));
//    }

    public PluginFile storePluginFile(MultipartFile file) throws IOException {

        // if file already in db
        String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
        PluginFile fileInDb = pluginFileRepository.findByMd5(fileMd5);
        if (fileInDb != null)
            return fileInDb;
        else {
            // save new file to db
            PluginFile pluginFile = new PluginFile();
            pluginFile.setFileName(StringUtils.getFilename(file.getOriginalFilename()));
            pluginFile.setContent(file.getBytes());
            pluginFile.setMd5(fileMd5);
            return pluginFileRepository.save(pluginFile);
        }
    }

    public PluginInfo storePluginInfo(PluginInfo pluginInfo) {
        // update info in db
        PluginInfo oldInfo = pluginInfoRepository.findByPluginName(pluginInfo.getPluginName());
        if (oldInfo != null) {
            pluginInfo.setId(oldInfo.getId());
        }

        return pluginInfoRepository.save(pluginInfo);
    }
}
