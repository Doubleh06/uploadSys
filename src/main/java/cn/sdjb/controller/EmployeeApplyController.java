package cn.sdjb.controller;

import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.dao.AccidentTypeDao;
import cn.sdjb.dao.EhsDao;
import cn.sdjb.dao.ImageDao;
import cn.sdjb.dto.EhsDto;
import cn.sdjb.entity.Ehs;
import cn.sdjb.entity.Image;
import cn.sdjb.service.DeptService;
import cn.sdjb.util.MyFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeApplyController extends BaseController {

    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private EhsDao ehsDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private DeptService deptService;
    @Autowired
    private Environment environment;


//    @RequestMapping(value = "remind")
    @RequestMapping(value = "sheet")
    public String remind(Model model){
        return "/employee/remind";
    }

//    @RequestMapping(value = "sheet")
    @RequestMapping(value = "remind")
    public String list(Model model){

        model.addAttribute("depts",deptService.getAddressArray("CZ") );
        model.addAttribute("accidentTypes",accidentTypeDao.selectAll());
        model.addAttribute("uuid", UUID.randomUUID());
        return "/employee/sheet";
    }

    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(@RequestBody EhsDto ehsDto)throws ParseException {
        //获取图片连接
        String uuid = ehsDto.getUuid();
        List<Image> imageList = imageDao.selectImgSourceName(uuid);
        String imgUrl = "";
        for (Image image : imageList){
            imgUrl += image.getImgName()+"~"+image.getImgSourceName()+"|";
        }
        Ehs ehs = new Ehs();
        BeanUtils.copyProperties(ehsDto, ehs);
        String date = ehsDto.getDate();
        String time = ehsDto.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        ehs.setAccidentTime(sdf.parse(date+" "+time));
        if(StringUtils.isEmpty(imgUrl)){
            ehs.setImgUrl(imgUrl);
        }else{
            ehs.setImgUrl(imgUrl.substring(0,imgUrl.length()-1));
        }
        ehs.setSubmitTime(new Date());
        ehs.setStatus(0);
        ehsDao.insert(ehs);
        return OK;
    }

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFile(MultipartFile[] files, @RequestParam String uuid) {
        List<String> subImgs = new ArrayList<>();
        for (MultipartFile file : files) {
            String imgPath  = MyFileUtil.saveFile(file);
            subImgs.add(imgPath);
        }
        //图片入库
        List<Image> imageList = new ArrayList<>();
        for (String subImg : subImgs){
            Image image = new Image();
            image.setUuid(uuid);
            image.setImgName(subImg.split("~")[0]);
            image.setImgSourceName(subImg.split("~")[1]);
            imageList.add(image);
        }
        imageDao.insertList(imageList);
        String subImgsString= StringUtils.join(subImgs.toArray(), ",");
        return new JSONResult(subImgsString);
    }

    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Result deleteFile(@RequestParam String imgSourceName, @RequestParam String uuid) {
        //获取img_name
        String imgName = imageDao.selectImgName(imgSourceName,uuid);
        //删除数据库
        imageDao.deleteFile(imgSourceName, uuid);
        //删除本地文件
        deleteLocalFile(imgName,imgSourceName);
        return OK;
    }
    @RequestMapping(value = "/address")
    @ResponseBody
    public Result getAddress(@RequestParam String address) {
        return deptService.getAddressResult(address);
    }

    public boolean deleteLocalFile(String imgName,String imgSourceName){
        String imgPath = environment.getProperty("static.img.path");
        String pathName = imgPath+imgName+"~"+imgSourceName;
        boolean flag = false;
        File file = new File(pathName);
        if (file.exists()&&file.isFile()){
            file.delete();
            flag = true;
        }
        return flag;
    }
}
