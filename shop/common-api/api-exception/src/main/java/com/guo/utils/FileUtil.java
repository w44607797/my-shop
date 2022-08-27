package com.guo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;
import java.util.UUID;

/**
 * @author guokaifeng
 * @createDate: 2022/4/25
 **/

@Slf4j
public class FileUtil {
    public static String getFileExtension(MultipartFile File){
        String originalFileName = File.getOriginalFilename();
        String result = null;
        try {
            result = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase(Locale.ROOT);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取文件后缀名出错");
        }
        return result;
    }
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString()
                .replace("-","");
    }
}
