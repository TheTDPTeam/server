package com.tdpteam.service.impl;

import com.tdpteam.service.helper.Constants;
import com.tdpteam.repo.entity.Config;
import com.tdpteam.repo.repository.ConfigRepository;
import com.tdpteam.service.interf.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository configRepository;

    @Autowired
    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public boolean isAdminConfigured() {
        Config config = configRepository.findByKey("Admin");
        if (config == null){
            return false;
        }
        return config.getValue().equals(Constants.AdminIsConfigured);
    }

    @Override
    public void setAdminConfigured(boolean value) {
        Config config = configRepository.findByKey("Admin");
        config.setValue(value ? Constants.AdminIsConfigured : Constants.AdminIsNotConfigured);
        configRepository.save(config);
    }
}
