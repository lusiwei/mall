package com.lusiwei.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ftp工具类
 */
public class FtpUtil {
    private static String ftp_server;
    private static String ftp_username;
    private static String ftp_password;
    private static int ftp_port;
    private static String ftp_server_prefix;

    static {
        ftp_server = PropertiesUtil.getProperty("ftp.server.ip");
        ftp_username = PropertiesUtil.getProperty("ftp.user");
        ftp_password = PropertiesUtil.getProperty("ftp.pass");
        ftp_port = Integer.parseInt(PropertiesUtil.getProperty("ftp.port"));
        ftp_server_prefix = PropertiesUtil.getProperty("ftp.server.http.prefix");
    }

    /**
     */
    /**
     * 上传成功返回一个map，map中装所上传图片的uri和url
     *
     * @param multipartFile 文件
     * @param dirName       上传的目录名
     * @return map
     */
    public static Map<String, String> uploadFile(MultipartFile multipartFile, String dirName) throws IOException {
        Map<String, String> map = new HashMap<>();
        FTPClient ftpClient = new FTPClient();
        if (dirName == null) {
            dirName = "";
        }
        //获取文件名
        String fileName = getFileName(multipartFile);
        if (fileName != null) {
            map.put("uri", fileName);
            map.put("url", ftp_server_prefix+dirName +"/"+ fileName);
        }
            //连接
            ftpClient.connect(ftp_server, ftp_port);
            //登录
            ftpClient.login(ftp_username, ftp_password);
            //设置文件类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("upload/"+dirName);
            ftpClient.storeFile(fileName, multipartFile.getInputStream());
        return map;
    }

    private static String getFileName(MultipartFile multipartFile) {
        //获取文件后缀名
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String s = UUID.randomUUID().toString();
        return s + suffix;
    }


}