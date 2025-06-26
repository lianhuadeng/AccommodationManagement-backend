package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    Map upload(MultipartFile file) throws IOException;

    List<User> parseUserExcel(MultipartFile file, String userType) throws IOException;;
}
