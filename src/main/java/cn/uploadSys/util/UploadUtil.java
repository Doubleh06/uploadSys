package cn.uploadSys.util;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class UploadUtil {

    public static String saveFile(MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                Environment environment = SpringContextUtil.getBean(Environment.class);
                String imgPath =  environment.getProperty("static.file.path");
                String newFileName = "";
                if (file.getSize() != 0) {
                    String originalFileNameLeft = file.getOriginalFilename();
                    // 新的图片名称
                    newFileName = originalFileNameLeft.substring(0,originalFileNameLeft.lastIndexOf(".")) + UUID.randomUUID()
                            + originalFileNameLeft.substring(originalFileNameLeft.lastIndexOf("."));
                    // 新图片，写入磁盘
                    File f = new File(imgPath, newFileName);
                    if (!f.exists()) {
                        // 先创建文件所在的目录
                        f.getParentFile().mkdirs();
                    }
                    file.transferTo(f);
                }
                return  imgPath +newFileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public static  boolean deleteFile(String fileName){
        boolean flag = false;
//        Environment environment = SpringContextUtil.getBean(Environment.class);
//        String filePath =  environment.getProperty("static.file.path");//"c:\\test\\" ;
        File file = new File(fileName);
        if (!file.exists()) {
            return flag;
        }
        if (file.delete()){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    public static String getRealImgPath(String imgPath){
        Environment environment = SpringContextUtil.getBean(Environment.class);
        String realImgPath = environment.getProperty("local.path.server");
        return realImgPath+imgPath;
    }
}
