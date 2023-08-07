package com.example.library.service;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.Config;
import com.example.library.repository.ConfigRepository;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {
    @Autowired
    ConfigRepository repository;
    @Autowired
    ImageUpload imageUpload;


    public List<Config> findALl() {
        return repository.findAll();
    }

    public void update(ConfigDTO configDTO, MultipartFile multipartFile) {
        Config config0 = repository.getById(1L);
        if(multipartFile.isEmpty()){
            config0.setValue(config0.getValue());
        }else {
            imageUpload.uploadImageLogo(multipartFile);
            config0.setValue(multipartFile.getOriginalFilename());
        }
        Config config1 = repository.getById(2L);
        config1.setValue(configDTO.getPhoneNumber());
        Config config2 = repository.getById(3L);
        config2.setValue(configDTO.getEmail());
        Config config3 = repository.getById(4L);
        config3.setValue(configDTO.getAddress());
        Config config4 = repository.getById(5L);
        config4.setValue(configDTO.getTimeForWork());
        Config config5 = repository.getById(6L);
        config5.setValue(configDTO.getSlide1());
        Config config6 = repository.getById(7L);
        config6.setValue(configDTO.getSlide2());
        List<Config> configList = new ArrayList<>();
        configList.add(config0);
        configList.add(config1);
        configList.add(config2);
        configList.add(config3);
        configList.add(config4);
        configList.add(config4);
        configList.add(config5);
        repository.saveAll(configList);
    }
    public String getValueById(Long id){
        return repository.getById(id).getValue();
    }
    public ConfigDTO getConfigPresent(){
        String logo = getValueById(1L);
        String phoneNumber = getValueById(2L);
        String email = getValueById(3L);
        String address = getValueById(4L);
        String timeForWork = getValueById(5L);
        String slide1 = getValueById(6L);
        String slide2 = getValueById(7L);
        ConfigDTO configDTO = new ConfigDTO(logo,phoneNumber,email,address,timeForWork,slide1,slide2);
        return configDTO;
    }
}
