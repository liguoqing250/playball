package org.jeecg.modules.appapi.utils;

import io.minio.errors.*;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UploadImageUtiles {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public static String uploadImages(MultipartFile file,String fileName) {
        // 生成新的文件名
        try {
            String savePath=System.getProperty("user.dir")+"\\playball-module-appapi\\src\\main\\resources\\static"+"\\AppImages";
            String newfileName=UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
            String realPath = savePath + "/" + newfileName;
            File dest = new File(realPath);
            // 判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            // 保存文件
            file.transferTo(dest);
            System.out.println("图片上传");
            System.out.println("http://localhost:8090/playball/AppImages/"+newfileName);
            return dest.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
