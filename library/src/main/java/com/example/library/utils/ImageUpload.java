package com.example.library.utils;

import com.example.library.ClassOfConst.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    public void uploadImage(MultipartFile imageProduct) {
        try {
            Files.copy(imageProduct.getInputStream(), Paths.get(Constant.UPLOAD_FOLDER + File.separator,
                    imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(imageProduct.getInputStream(), Paths.get(Constant.UPLOAD_FOLDER_2 + File.separator,
                    imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExist(MultipartFile imageProduct) {
        boolean isExist = false;
        File file = new File(Constant.UPLOAD_FOLDER + "/" + imageProduct.getOriginalFilename());
        if (file.exists()) { // tính cả khi null
            isExist = true;
        }
        if (imageProduct.isEmpty()) return false;
//        System.out.println(isExist);
        return isExist;
    }
    public void uploadImageLogo(MultipartFile imageProduct) {
        try {
            Files.copy(imageProduct.getInputStream(), Paths.get(Constant.UPLOAD_FOLDER_IMAGE_LOGO_ADMIN + File.separator,
                    imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(imageProduct.getInputStream(), Paths.get(Constant.UPLOAD_FOLDER_IMAGE_LOGO_CUSTOMER + File.separator,
                    imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
