package cn.sdjb.util;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFileUtil {

    public static String saveFile(MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                Environment environment = SpringContextUtil.getBean(Environment.class);
                String imgPath = environment.getProperty("static.img.path");
                String newPicName = "";
                if (file.getSize() != 0) {
                    String originalFileNameLeft = file.getOriginalFilename();
                    // 新的图片名称
//                    newPicName = UUID.randomUUID() + originalFileNameLeft.substring(originalFileNameLeft.lastIndexOf("."));
                    Date date = new Date();
                    newPicName = sdf.format(date) + "~" +originalFileNameLeft;//.substring(originalFileNameLeft.lastIndexOf("."));

                    // 新图片，写入磁盘
                    File f = new File(imgPath, newPicName);
                    if (!f.exists()) {
                        // 先创建文件所在的目录
                        f.getParentFile().mkdirs();
                    }
                    file.transferTo(f);
                }
                return   newPicName;
//                return  imgPath +newPicName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String getRealImgPath(String imgPath){
        Environment environment = SpringContextUtil.getBean(Environment.class);
        String realImgPath = environment.getProperty("local.path.server");
        return realImgPath+imgPath;
    }
}
