package com.lusiwei.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lusiwei
 * @Date: 2018/11/27 11:17
 * @Description:
 */
public interface UploadService {
    String upload(MultipartFile file, String remote);
}
