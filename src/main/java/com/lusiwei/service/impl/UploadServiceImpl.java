package com.lusiwei.service.impl;

import com.lusiwei.service.UploadService;
import com.lusiwei.util.PropertiesUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload(MultipartFile file, String remote) {
        // file.getInputStream()
        return null;
    }

    private String getFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String s = UUID.randomUUID().toString();
        return s + suffix;
    }

    private boolean uploadFile(List<File> files, String remote) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(PropertiesUtil.getProperty("ftp.server.ip"));
            System.out.println(ftpClient.login(PropertiesUtil.getProperty("ftp.user"), PropertiesUtil.getProperty("ftp.pass")));
            //主动模式：客户端开启1024以上端口号 服务端就是20或者21    被动模式 服务端开启端口
            //开启被动模式
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("aaa");
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.storeFile(new String("IO超级总结.bmp".getBytes(), "iso-8859-1"), new FileInputStream(new File("C:\\Users\\95157\\Pictures\\jpg.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(PropertiesUtil.getProperty("ftp.server.ip"));
            System.out.println(ftpClient.login(PropertiesUtil.getProperty("ftp.user"), PropertiesUtil.getProperty("ftp.pass")));
            //主动模式：客户端开启1024以上端口号 服务端就是20或者21    被动模式 服务端开启端口
            //开启被动模式
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("upload");
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setBufferSize(1024);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean b = ftpClient.storeFile(new String("hello.jpg".getBytes("UTF-8"), "iso-8859-1"), new FileInputStream(new File("C:\\Users\\95157\\Pictures\\jpg.jpg")));
            System.out.println(ftpClient.getReplyString());
            System.out.println(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
